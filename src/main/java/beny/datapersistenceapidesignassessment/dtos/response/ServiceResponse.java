package beny.datapersistenceapidesignassessment.dtos.response;

import lombok.Data;

@Data
public class ServiceResponse<T> {
    private T data;
    private boolean status;
   public ServiceResponse(T data, boolean status){
       this.data=data;
       this.status=status;
   }
}
