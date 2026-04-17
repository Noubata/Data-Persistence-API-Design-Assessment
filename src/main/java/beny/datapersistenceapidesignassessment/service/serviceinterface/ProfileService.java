package beny.datapersistenceapidesignassessment.service.serviceinterface;

import beny.datapersistenceapidesignassessment.dtos.request.GetProfilesRequest;
import beny.datapersistenceapidesignassessment.dtos.response.ProfileResponse;
import beny.datapersistenceapidesignassessment.dtos.response.ServiceResponse;
import beny.datapersistenceapidesignassessment.dtos.response.Summary;

import java.util.List;

public interface ProfileService {
    ServiceResponse<ProfileResponse> createProfile(GetProfilesRequest getProfilesRequest);

    ProfileResponse getProfileById(String id);

    List<Summary> getProfiles(String gender, String ageGroup, String countryId);
    void deleteProfile(String id);
}
