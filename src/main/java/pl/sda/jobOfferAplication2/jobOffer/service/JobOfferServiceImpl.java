package pl.sda.jobOfferAplication2.jobOffer.service;

import org.springframework.stereotype.Service;
import pl.sda.jobOfferAplication2.jobOffer.entity.JobOfferEntity;
import pl.sda.jobOfferAplication2.jobOffer.repository.JobOfferRepository;
import pl.sda.jobOfferAplication2.user.exception.UserException;
import pl.sda.jobOfferAplication2.user.repository.UserRepository;
import pl.sda.jobOfferAplication2.user.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
public class JobOfferServiceImpl implements JobOfferService{

    private final JobOfferRepository jobOfferRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public JobOfferServiceImpl(JobOfferRepository jobOfferRepository, UserRepository userRepository, UserService userService) {
        this.jobOfferRepository = jobOfferRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @Override
    public void addOfferToUser(Long userId, Long jobOfferId) throws UserException {
        JobOfferEntity jobOfferEntity = jobOfferRepository.findById(jobOfferId).get();
        userRepository.findById(userId).get().addJobOffer(jobOfferEntity);
        List<JobOfferEntity> list;
        list = userRepository.findById(userId).get().jobOffers;
        for(int i=0;i<list.size();i++){
            System.out.println(list.get(i));
        }
    }
}
