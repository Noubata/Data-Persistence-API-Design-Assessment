package beny.datapersistenceapidesignassessment.service.serviceinterface;

import beny.datapersistenceapidesignassessment.dtos.response.GenderizeApiResponse;

public interface GenderApi {
    GenderizeApiResponse getGender(String name);
}
