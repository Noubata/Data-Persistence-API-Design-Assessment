package beny.datapersistenceapidesignassessment.exceptions;

public class ApiResponseException extends ProfileAggregatorException {
    public ApiResponseException(String apiReturnedError) {
        super(apiReturnedError);
    }
}
