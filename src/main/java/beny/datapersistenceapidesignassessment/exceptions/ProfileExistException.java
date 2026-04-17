package beny.datapersistenceapidesignassessment.exceptions;

public class ProfileExistException extends ProfileAggregatorException{
    public ProfileExistException(String message) {
        super(message);
    }
}
