package beny.datapersistenceapidesignassessment.dtos.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
@Data
public class ProfileResponse {
    private String id;
    private String name;
    private String gender;
    @JsonProperty("gender_probability")
    private Double genderProbability;
    @JsonProperty("sample_size")
    private Integer sampleSize;
    private Integer age;
    @JsonProperty("age_group")
    private String ageGroup;
    @JsonProperty("country_id")
    private String countryId;
    @JsonProperty("country_probability")
    private Double countryProbability;
    @CreationTimestamp
    @JsonProperty("created_at")
    private String createdAt;
}
