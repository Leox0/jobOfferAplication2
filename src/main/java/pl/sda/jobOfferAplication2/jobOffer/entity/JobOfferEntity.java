package pl.sda.jobOfferAplication2.jobOffer.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Getter;
import pl.sda.jobOfferAplication2.jobOffer.model.JobOfferCategory;
import pl.sda.jobOfferAplication2.user.entity.UserEntity;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "JOB_OFFERS")
public class JobOfferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private JobOfferCategory category;
    private String startDate;
    private String endDate;

    public JobOfferEntity(Long id, JobOfferCategory category, String startDate, String endDate) {
        this.id = id;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
