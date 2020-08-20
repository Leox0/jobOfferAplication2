package pl.sda.jobOfferAplication2.user.model;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class UserInput {
    private String name;
    private String login;
    private LocalDate creationDate;
    private String password;

    public UserInput() {
        creationDate = LocalDate.now();
    }
}
