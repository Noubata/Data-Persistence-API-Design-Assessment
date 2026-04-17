package beny.datapersistenceapidesignassessment.service.serviceImplementation;

import beny.datapersistenceapidesignassessment.data.model.Country;
import beny.datapersistenceapidesignassessment.data.model.Profile;
import beny.datapersistenceapidesignassessment.data.repository.ProfileRepository;
import beny.datapersistenceapidesignassessment.dtos.request.GetProfilesRequest;
import beny.datapersistenceapidesignassessment.dtos.response.*;
import beny.datapersistenceapidesignassessment.exceptions.ProfileExistException;
import beny.datapersistenceapidesignassessment.service.serviceinterface.AgeApi;
import beny.datapersistenceapidesignassessment.service.serviceinterface.ProfileService;
import beny.datapersistenceapidesignassessment.utils.Mapper;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class ProfileServiceImple implements ProfileService {

    private AgeApi ageApi;
    private GeneralizeApi generalizeApi;
    private NationalizeApi nationalizeApi;
    private ProfileRepository profileRepository;

    public ProfileServiceImple(AgeApi ageApi, GeneralizeApi generalizeApi, NationalizeApi nationalizeApi, ProfileRepository profileRepository) {
        this.ageApi = ageApi;
        this.generalizeApi = generalizeApi;
        this.nationalizeApi = nationalizeApi;
        this.profileRepository = profileRepository;
    }

    @Override
    public ServiceResponse<ProfileResponse> createProfile(GetProfilesRequest getProfilesRequest) {
        Optional<Profile> profileOptional = profileRepository.findByName(getProfilesRequest.getName());
       if(profileOptional.isPresent()){
           ProfileResponse profileResponse= Mapper.mapProfileToProfileResponse(profileOptional.get());
           return Mapper.mapProfileResponseToServiceResponse(profileResponse,true);
       }
        CompletableFuture<AgeApiResponse> ageApiResponseCompletableFuture = CompletableFuture.supplyAsync(()->ageApi.getAge(getProfilesRequest.getName()));
        CompletableFuture<GenderizeApiResponse> genderizeApiResponseCompletableFuture = CompletableFuture.supplyAsync(()->generalizeApi.getGender(getProfilesRequest.getName()));
        CompletableFuture<NationalityApiResponse> nationalizeApiCompletableFuture = CompletableFuture.supplyAsync(()->nationalizeApi.getNationality(getProfilesRequest.getName()));
        CompletableFuture.allOf(ageApiResponseCompletableFuture, genderizeApiResponseCompletableFuture, nationalizeApiCompletableFuture).join();
       Profile profile= Mapper.mapApiResponsesToProfile(ageApiResponseCompletableFuture,genderizeApiResponseCompletableFuture,nationalizeApiCompletableFuture);
       profile.setCountryId(determineCountry(nationalizeApiCompletableFuture.join().getCountry()).getCountryId());
       profile.setCountryProbability(determineCountry(nationalizeApiCompletableFuture.join().getCountry()).getProbability());
       profile.setAgeGroup(DetermineAgeGroup(ageApiResponseCompletableFuture.join().getAge()));
       profile.setAge(ageApiResponseCompletableFuture.join().getAge());
        profileRepository.save(profile);
        ProfileResponse response= Mapper.mapProfileToProfileResponse(profile);
        return Mapper.mapProfileResponseToServiceResponse(response,false);
    }

    @Override
    public ProfileResponse getProfileById(String id) {
         Optional<Profile> profileOptional = profileRepository.findById(id);
         if(profileOptional.isPresent()){
             return Mapper.mapProfileToProfileResponse(profileOptional.get());
         }
         throw new ProfileExistException("profile does not exist");
    }

    @Override
    public List<Summary> getProfiles(String gender, String ageGroup, String countryId) {
        Specification<Profile> spec = Specification.where((root, query, criteriaBuilder) -> null);

        if (gender != null) {
            spec = spec.and((root, query, cb) ->
                cb.equal(cb.lower(root.get("gender")), gender.toLowerCase()));
        }
        if (countryId != null) {
            spec = spec.and((root, query, cb) ->
                cb.equal(cb.lower(root.get("countryId")), countryId.toLowerCase()));
        }
        if (ageGroup != null) {
            spec = spec.and((root, query, cb) ->
                cb.equal(cb.lower(root.get("ageGroup")), ageGroup.toLowerCase()));
        }
        return profileRepository.findAll(spec)
                .stream()
                .map(Mapper::mapProfileToSummary)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProfile(String id) {
            Optional<Profile> profileOptional = profileRepository.findById(id);
            if(profileOptional.isPresent()){
                profileRepository.delete(profileOptional.get());
            } else {
                throw new ProfileExistException("profile does not exist");
            }
    }

    private String DetermineAgeGroup(int age) {
        String ageGroup = "";
        if(age >=0 && age <= 12){
            ageGroup= "child";
        } else if (age >=13 && age <= 19 ){
            ageGroup= "teenager";
        } else if (age >=20 && age <= 59) {
            ageGroup= "adult";
        } else if (age>=60) {
            ageGroup= "senior";
        }
    return ageGroup;
    }

    private Country determineCountry(List<Country> countries) {
       Double highestProbability = 0.0;
       Country countryWithHighestProbability=null;
        for (Country country : countries ){
            if (country.getProbability() > highestProbability){
                countryWithHighestProbability = country;
                highestProbability = country.getProbability();
            }
        }
        assert countryWithHighestProbability != null;
        return countryWithHighestProbability;
    }
}
