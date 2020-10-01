package pl.sda.jobOfferAplication2.jobOffer.service;


import pl.sda.jobOfferAplication2.jobOffer.exception.JobOfferException;
import pl.sda.jobOfferAplication2.jobOffer.model.JobOfferInput;
import pl.sda.jobOfferAplication2.jobOffer.model.JobOfferOutput;
import pl.sda.jobOfferAplication2.user.exception.UserException;

import java.util.List;

public interface JobOfferService {
    List<JobOfferOutput> getAllJobOffer();
    JobOfferOutput getJobOfferById(Long id) throws JobOfferException;
    void createJobOffer(JobOfferInput jobOfferInput);
    void deleteJobOfferById(Long id) throws JobOfferException;
    void addUserToJobOffer(Long userId, Long jobOfferId);
    void deleteUserFromJobOffer(Long jobOfferId);
}
