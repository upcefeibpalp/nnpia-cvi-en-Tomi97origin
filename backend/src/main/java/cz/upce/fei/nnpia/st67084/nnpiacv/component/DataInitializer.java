package cz.upce.fei.nnpia.st67084.nnpiacv.component;


import cz.upce.fei.nnpia.st67084.nnpiacv.domain.User;
import cz.upce.fei.nnpia.st67084.nnpiacv.domain.UserProfile;
import cz.upce.fei.nnpia.st67084.nnpiacv.repository.UserProfileRepository;
import cz.upce.fei.nnpia.st67084.nnpiacv.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;

    @Override
    public void run(String... args) {
        Optional<User> adminUser = userRepository.findByEmail("admin@example.com");

        if (adminUser.isEmpty()) {
            adminUser = Optional.of(new User("adminPassword", "admin@example.com"));
            userRepository.save(adminUser.get());
            log.info("Admin user created: {}", adminUser.get().getEmail());
        } else {
            log.info("Admin user already exists.");
        }

        // Check if admin user profile already exists (optional)
        UserProfile adminProfile = userProfileRepository.findByUser_Id(adminUser.get().getId());

        if (adminProfile == null) {
            UserProfile userProfile = new UserProfile(null, "https://example.com/admin-profile.jpg", adminUser.get()); // Create UserProfile and link to adminUser
            userProfileRepository.save(userProfile);
            log.info("Admin user profile created for user: {}", adminUser.get().getEmail());
        } else {
            log.info("Admin user profile already exists for user: {}", adminUser.get().getEmail());
        }
    }
}