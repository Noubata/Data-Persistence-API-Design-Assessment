package beny.datapersistenceapidesignassessment.exceptions;

public class ApiRequestLimitError extends ApiResponseException {
    public ApiRequestLimitError(String apiReturnedRequestLimitError) {
        super(apiReturnedRequestLimitError);
    }
}
