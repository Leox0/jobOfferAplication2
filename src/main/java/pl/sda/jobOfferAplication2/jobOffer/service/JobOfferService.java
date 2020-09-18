package pl.sda.jobOfferAplication2.jobOffer.service;


import pl.sda.jobOfferAplication2.user.exception.UserException;

public interface JobOfferService {
    void addOfferToUser(Long userId, Long jobOfferId) throws UserException;
}
