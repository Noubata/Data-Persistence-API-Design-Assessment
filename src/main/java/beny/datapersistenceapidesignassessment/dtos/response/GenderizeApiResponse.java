package beny.datapersistenceapidesignassessment.dtos.response;

import lombok.Data;

@Data
public class GenderizeApiResponse {
        private Integer count;
        private String name;
        private String gender;
        private Double probability;

}
