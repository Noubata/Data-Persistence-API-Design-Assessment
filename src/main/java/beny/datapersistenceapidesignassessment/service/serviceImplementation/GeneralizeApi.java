package beny.datapersistenceapidesignassessment.service.serviceImplementation;

import beny.datapersistenceapidesignassessment.dtos.response.GenderizeApiResponse;
import beny.datapersistenceapidesignassessment.exceptions.ApiRequestLimitError;
import beny.datapersistenceapidesignassessment.exceptions.ApiResponseException;
import beny.datapersistenceapidesignassessment.exceptions.GenderNullException;
import beny.datapersistenceapidesignassessment.service.serviceinterface.GenderApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@Service
public class GeneralizeApi implements GenderApi {

    @Autowired
    private ObjectMapper objectMapper;

    private HttpClient client = HttpClient.newHttpClient();

    @Override
    public GenderizeApiResponse getGender(String name) {
        String url = "https://api.genderize.io?name=" + URLEncoder.encode(name, StandardCharsets.UTF_8);
        return fetchGenderizeApiResponse(url);

    }

    private GenderizeApiResponse fetchGenderizeApiResponse(String url) {
        try  {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(url))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return getGenderizeApiResponse(response);
        } catch (IOException | InterruptedException e) {
            throw new ApiResponseException("api return error");
        }
    }

    private GenderizeApiResponse getGenderizeApiResponse(HttpResponse<String> response) {
        if(response.statusCode() == 200) {
            GenderizeApiResponse genderizeApiResponse = objectMapper.readValue(response.body(), GenderizeApiResponse.class);
            if (genderizeApiResponse.getGender()==null||genderizeApiResponse.getCount()==0){
                throw new GenderNullException("Genderize returned an invalid response ");
            }
            return genderizeApiResponse;
        }else {
            throw new ApiRequestLimitError("api returned limit error");
        }
    }
}
