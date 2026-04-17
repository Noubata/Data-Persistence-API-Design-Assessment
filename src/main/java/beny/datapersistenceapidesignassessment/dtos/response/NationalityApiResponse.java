package beny.datapersistenceapidesignassessment.dtos.response;

import beny.datapersistenceapidesignassessment.data.model.Country;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class NationalityApiResponse {
        private Integer count;
        private String name;
        private ArrayList<Country> country;

        public NationalityApiResponse() {
            this.country = new ArrayList<>();
        }


}
