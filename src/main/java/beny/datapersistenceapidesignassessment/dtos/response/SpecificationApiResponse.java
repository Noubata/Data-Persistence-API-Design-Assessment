package beny.datapersistenceapidesignassessment.dtos.response;

import lombok.Data;

import java.util.List;

@Data
public class SpecificationApiResponse  {
    private String status;
    private int count;
    private List<Summary> data;

    public SpecificationApiResponse(int count, List<Summary> data) {
        this.count=count;
        this.data=data;
        this.status= "success";
    }

}
