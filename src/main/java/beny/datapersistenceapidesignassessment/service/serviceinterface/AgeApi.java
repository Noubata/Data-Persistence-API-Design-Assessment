package beny.datapersistenceapidesignassessment.service.serviceinterface;

import beny.datapersistenceapidesignassessment.dtos.response.AgeApiResponse;

public interface AgeApi {
    AgeApiResponse getAge(String name);
}
