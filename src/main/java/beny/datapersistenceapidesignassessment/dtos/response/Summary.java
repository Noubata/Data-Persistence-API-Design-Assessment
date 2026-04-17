package beny.datapersistenceapidesignassessment.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Summary {
    private  String id;
    private String name;
    private String gender;
    private Integer age;
    @JsonProperty("age_group")
    private String ageGroup;
    @JsonProperty("country_id")
    private String countryId;
}
