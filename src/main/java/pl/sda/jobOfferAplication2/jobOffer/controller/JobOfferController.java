package pl.sda.jobOfferAplication2.jobOffer.controller;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.sda.jobOfferAplication2.jobOffer.entity.JobOfferEntity;
import pl.sda.jobOfferAplication2.jobOffer.exception.JobOfferException;
import pl.sda.jobOfferAplication2.jobOffer.model.JobOfferCategory;
import pl.sda.jobOfferAplication2.jobOffer.model.JobOfferInput;
import pl.sda.jobOfferAplication2.jobOffer.model.JobOfferOutput;
import pl.sda.jobOfferAplication2.jobOffer.repository.JobOfferRepository;
import pl.sda.jobOfferAplication2.jobOffer.service.JobOfferService;
import pl.sda.jobOfferAplication2.user.exception.UserException;
import pl.sda.jobOfferAplication2.user.model.UserOutput;
import pl.sda.jobOfferAplication2.user.service.UserService;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(JobOfferController.JOB_OFFER_MAPPING)
public class JobOfferController {
    public static final String JOB_OFFER_MAPPING = "/jobOffer";

    private final JobOfferService jobOfferService;
    private final JobOfferRepository jobOfferRepository;
    private final UserService userService;

    public JobOfferController(JobOfferService jobOfferService, JobOfferRepository jobOfferRepository, UserService userService) {
        this.jobOfferService = jobOfferService;
        this.jobOfferRepository = jobOfferRepository;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<JobOfferOutput>> getAllJobOffer(){
        List<JobOfferOutput> allJobOffer = jobOfferService.getAllJobOffer();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(allJobOffer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobOfferOutput> getJobOfferById(@PathVariable(value = "id") Long id) throws JobOfferException {
        JobOfferOutput jobOfferById = jobOfferService.getJobOfferById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobOfferById);
    }

    @PostMapping
    public ResponseEntity<Void> postUser(@RequestBody JobOfferInput jobOfferInput) {
        jobOfferService.createJobOffer(jobOfferInput);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobOfferById(@PathVariable(value = "id") Long id) throws JobOfferException {
        jobOfferService.deleteJobOfferById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @GetMapping("/addUser/{id}")
    public ResponseEntity<Void> addUserToJobOffer(@PathVariable(value = "id") Long jobOfferId, @RequestParam Long userId)  {
        jobOfferService.addUserToJobOffer(userId,jobOfferId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @GetMapping("/deleteUser/{id}")
    public ResponseEntity<Void> deleteUserFromJobOffer(@PathVariable(value = "id") Long jobOfferId) {
        jobOfferService.deleteUserFromJobOffer(jobOfferId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .build();
    }

    @GetMapping("/getUser")
    public ResponseEntity<UserOutput> getUserForJb() throws UserException {
        JobOfferEntity jobOfferEntity = jobOfferRepository.findById(1L).get();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(jobOfferEntity.getUserEntity().toOutput());
    }

}
