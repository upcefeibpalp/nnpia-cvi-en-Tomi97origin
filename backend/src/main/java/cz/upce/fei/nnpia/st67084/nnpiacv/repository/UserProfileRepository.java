package cz.upce.fei.nnpia.st67084.nnpiacv.repository;

import cz.upce.fei.nnpia.st67084.nnpiacv.domain.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    UserProfile findByUser_Id(Long id);
}
