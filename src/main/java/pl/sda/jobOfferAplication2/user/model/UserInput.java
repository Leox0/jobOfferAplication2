package pl.sda.jobOfferAplication2.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class UserInput {
    private String name;
    private String login;
    @JsonIgnore
    private LocalDate creationDate = LocalDate.now();
    private String password;

    public UserInput(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }
}
