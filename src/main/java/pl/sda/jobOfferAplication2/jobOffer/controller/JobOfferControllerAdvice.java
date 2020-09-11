package pl.sda.jobOfferAplication2.jobOffer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.sda.jobOfferAplication2.jobOffer.exception.JobOfferException;

public class JobOfferControllerAdvice {
    @ExceptionHandler(JobOfferException.class)
    public ResponseEntity<String> handUserException(JobOfferException e){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }
}
