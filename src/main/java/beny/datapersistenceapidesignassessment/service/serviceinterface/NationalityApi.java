package beny.datapersistenceapidesignassessment.service.serviceinterface;

import beny.datapersistenceapidesignassessment.dtos.response.NationalityApiResponse;

public interface NationalityApi {
NationalityApiResponse getNationality(String name);
}
