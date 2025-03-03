package cz.upce.fei.nnpia.st67084.nnpiacv.services;

import cz.upce.fei.nnpia.st67084.nnpiacv.domain.User;
import cz.upce.fei.nnpia.st67084.nnpiacv.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Service
@Slf4j
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            log.atDebug().log("User found with id {}: {}", id, user);
            return user;
        } else {
            log.atDebug().log("User not found with id: {}", id);
            return null;
        }
    }

    public User findUserByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            log.atDebug().log("User found with email {}: {}", email, user);
            return user.orElse(null);
        } else {
            log.atDebug().log("User not found with email: {}", email);
            return null;
        }
    }

    public Collection<User> findUsers(String email) {
        if (email != null && !email.isEmpty()) {
            User userByEmail = findUserByEmail(email);
            if (userByEmail != null) {
                return List.of(userByEmail); // Return a list containing the user if found
            } else {
                return List.of(); // Return an empty list if user with email not found
            }
        } else {
            return userRepository.findAll(); // Return all users if email is not provided
        }

    }

    public User createUser(User user) {
        log.atDebug().log("Creating user: {}", user);
        return userRepository.save(user);
    }

    public boolean deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            log.atDebug().log("User not found with id: {}", id);
            return false; // Nebo můžeme vyhodit ResponseStatusException přímo zde
        }
        userRepository.deleteById(id);
        log.atDebug().log("User with id: {} deleted", id);
        return true;
    }

    public User updateUser(Long id, User updatedUser) {
        Optional<User> existingUserOptional = userRepository.findById(id);
        if (existingUserOptional.isPresent()) {
            User existingUser = existingUserOptional.get();
            log.atDebug().log("Updating user with id {}: {}", id, updatedUser);
            // Aktualizujeme pole existujícího uživatele s novými hodnotami
            existingUser.setEmail(updatedUser.getEmail());
            existingUser.setPassword(updatedUser.getPassword()); // Měli byste zvážit hashování hesla před uložením
            return userRepository.save(existingUser); // Uložíme aktualizovaného uživatele
        } else {
            log.atDebug().log("User not found with id: {}", id);
            return null; // Nebo můžeme vyhodit ResponseStatusException přímo zde
        }
    }
}
