package pl.sda.jobOfferAplication2.jobOffer.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.sda.jobOfferAplication2.user.model.UserOutput;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class JobOfferOutput {

    private String name;
    private String category;
    private String startDate;
    private String endDate;
    private UserOutput user;
}
