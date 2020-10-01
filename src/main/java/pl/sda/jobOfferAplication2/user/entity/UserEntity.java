package pl.sda.jobOfferAplication2.user.entity;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pl.sda.jobOfferAplication2.jobOffer.entity.JobOfferEntity;
import pl.sda.jobOfferAplication2.user.model.UserOutput;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name = "USERS")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String login;
    private LocalDate creationDate;
    private String password;
    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<JobOfferEntity> jobOffers;

    public UserEntity(String name, String login, LocalDate creationDate, String password) {
        this.name = name;
        this.login = login;
        this.creationDate = creationDate;
        this.password = password;
    }

    public UserOutput toOutput() {
        return new UserOutput(id, name, login, creationDate);
    }

}
