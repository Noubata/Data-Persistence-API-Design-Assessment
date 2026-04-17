package beny.datapersistenceapidesignassessment.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
@Data
public class Country {
        @JsonProperty("country_id")
        private String countryId;
        private Double probability;
}
