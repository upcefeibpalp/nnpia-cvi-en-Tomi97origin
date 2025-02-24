package cz.upce.fei.nnpia.st67084.nnpiacv.repository;

import cz.upce.fei.nnpia.st67084.nnpiacv.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
