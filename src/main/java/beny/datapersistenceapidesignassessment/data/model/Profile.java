package beny.datapersistenceapidesignassessment.data.model;

import com.fasterxml.uuid.Generators;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Data
@Entity
public class Profile {
        @Id
        private String id;
        private String name;
        private String gender;
        private Double genderProbability;
        private Integer sampleSize;
        private Integer age;
        private String ageGroup;
        private String countryId;
        private Double countryProbability;
        @CreationTimestamp
        private String createdAt;
        @PrePersist
        public void prePersist(){
            this.id = Generators.timeBasedGenerator().generate().toString();
        }

}
