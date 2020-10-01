package pl.sda.jobOfferAplication2.jobOffer.service;

import org.springframework.stereotype.Service;
import pl.sda.jobOfferAplication2.jobOffer.entity.JobOfferEntity;
import pl.sda.jobOfferAplication2.jobOffer.exception.JobOfferException;
import pl.sda.jobOfferAplication2.jobOffer.model.JobOfferInput;
import pl.sda.jobOfferAplication2.jobOffer.model.JobOfferOutput;
import pl.sda.jobOfferAplication2.jobOffer.repository.JobOfferRepository;
import pl.sda.jobOfferAplication2.user.entity.UserEntity;
import pl.sda.jobOfferAplication2.user.exception.UserException;
import pl.sda.jobOfferAplication2.user.repository.UserRepository;
import pl.sda.jobOfferAplication2.user.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobOfferServiceImpl implements JobOfferService {

    public static final String NO_JOB_OFFER_FOUND_FOR_GIVEN_ID = "No job offer found for given id";
    private final JobOfferRepository jobOfferRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public JobOfferServiceImpl(JobOfferRepository jobOfferRepository, UserRepository userRepository, UserService userService) {
        this.jobOfferRepository = jobOfferRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }


    @Override
    public List<JobOfferOutput> getAllJobOffer() {
        return jobOfferRepository.findAll()
                .stream()
                .map(JobOfferEntity::toOutput)
                .collect(Collectors.toList());
    }

    @Override
    public JobOfferOutput getJobOfferById(Long id) throws JobOfferException {
        Optional<JobOfferEntity> optionalJobOfferEntity = getJobOfferEntity(id);
        return optionalJobOfferEntity.get().toOutput();
    }

    private Optional<JobOfferEntity> getJobOfferEntity(Long id) throws JobOfferException {
        Optional<JobOfferEntity> optionalJobOfferEntity = jobOfferRepository.findById(id);
        if (!optionalJobOfferEntity.isPresent()) {
            throw new JobOfferException(NO_JOB_OFFER_FOUND_FOR_GIVEN_ID);
        }
        return optionalJobOfferEntity;
    }

    @Override
    public void createJobOffer(JobOfferInput jobOfferInput) {
        JobOfferEntity jobOfferEntity = new JobOfferEntity(
                jobOfferInput.getName(),
                jobOfferInput.getCategory(),
                jobOfferInput.getStartDate(),
                jobOfferInput.getEndDate());
        jobOfferRepository.save(jobOfferEntity);
    }

    @Override
    public void deleteJobOfferById(Long id) throws JobOfferException {
        getJobOfferEntity(id);
        jobOfferRepository.deleteById(id);
    }

    @Override
    public void addUserToJobOffer(Long userId, Long jobOfferId) {
        UserEntity userEntity = userRepository.findById(userId).get();
        JobOfferEntity jobOfferEntity = jobOfferRepository.findById(jobOfferId).get();
        jobOfferEntity.setUserEntity(userEntity);
        jobOfferRepository.save(jobOfferEntity);
    }

    @Override
    public void deleteUserFromJobOffer(Long jobOfferId) {
        JobOfferEntity jobOfferEntity = jobOfferRepository.findById(jobOfferId).get();
        jobOfferEntity.setUserEntity(null);
        jobOfferRepository.save(jobOfferEntity);
    }
}
