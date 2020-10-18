package pl.sda.jobOfferAplication2.jobOffer.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Getter;
import pl.sda.jobOfferAplication2.jobOffer.model.JobOfferCategory;
import pl.sda.jobOfferAplication2.jobOffer.model.JobOfferOutput;
import pl.sda.jobOfferAplication2.user.entity.UserEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "JOB_OFFERS")
public class JobOfferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private JobOfferCategory category;
    private String startDate;
    private String endDate;
    @ManyToOne()
    @JoinColumn(name = "userEntity_id")
    private UserEntity userEntity;

    public JobOfferEntity(String name, JobOfferCategory category, String startDate, String endDate) {
        this.name = name;
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public JobOfferOutput toOutput() {
        return new JobOfferOutput(name, category.toString(), startDate, endDate, userEntity.toOutput());
    }

}
