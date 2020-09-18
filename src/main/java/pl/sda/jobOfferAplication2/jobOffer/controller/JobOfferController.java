package pl.sda.jobOfferAplication2.jobOffer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.sda.jobOfferAplication2.jobOffer.entity.JobOfferEntity;
import pl.sda.jobOfferAplication2.jobOffer.model.JobOfferCategory;
import pl.sda.jobOfferAplication2.jobOffer.repository.JobOfferRepository;
import pl.sda.jobOfferAplication2.jobOffer.service.JobOfferService;
import pl.sda.jobOfferAplication2.user.exception.UserException;

@RestController
@CrossOrigin
@RequestMapping(JobOfferController.JOB_OFFER_MAPPING)
public class JobOfferController {
    public static final String JOB_OFFER_MAPPING = "/jobOffer";

    private final JobOfferService jobOfferService;
    private final JobOfferRepository jobOfferRepository;

    public JobOfferController(JobOfferService jobOfferService, JobOfferRepository jobOfferRepository) {
        this.jobOfferService = jobOfferService;
        this.jobOfferRepository = jobOfferRepository;
    }

    @GetMapping
    public ResponseEntity<Void> createJobOfferEntityTest() throws UserException {
        JobOfferEntity jobOfferEntity = new JobOfferEntity(1L,
                JobOfferCategory.OFFICE,
                "01-01-2020",
                "02-02-2020");
        jobOfferRepository.save(jobOfferEntity);

        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @GetMapping("/1")
    public ResponseEntity<Void> test() throws UserException {
        jobOfferService.addOfferToUser(1L,1L);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

}
