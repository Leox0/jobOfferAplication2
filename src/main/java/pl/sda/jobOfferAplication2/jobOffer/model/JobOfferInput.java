package pl.sda.jobOfferAplication2.jobOffer.model;

import lombok.Getter;
import lombok.ToString;
import pl.sda.jobOfferAplication2.user.model.UserOutput;

@Getter
@ToString
public class JobOfferInput {

    private JobOfferCategory category;
    private String startDate;
    private String endDate;
    private UserOutput user;

    public JobOfferInput(JobOfferCategory category, String startDate, String endDate, UserOutput user) {
        this.category = category;
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
    }
}
