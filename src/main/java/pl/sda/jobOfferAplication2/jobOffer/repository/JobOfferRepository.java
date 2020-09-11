package pl.sda.jobOfferAplication2.jobOffer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.jobOfferAplication2.jobOffer.entity.JobOfferEntity;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOfferEntity, Long> {

}
