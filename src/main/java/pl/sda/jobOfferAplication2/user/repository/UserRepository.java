package pl.sda.jobOfferAplication2.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.sda.jobOfferAplication2.user.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsUserEntityByLogin(String login);
    boolean existsUserEntityById(Long id);
}
