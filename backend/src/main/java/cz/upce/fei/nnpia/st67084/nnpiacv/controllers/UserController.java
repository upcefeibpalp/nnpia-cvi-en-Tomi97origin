package cz.upce.fei.nnpia.st67084.nnpiacv.controllers;

import cz.upce.fei.nnpia.st67084.nnpiacv.domain.User;
import cz.upce.fei.nnpia.st67084.nnpiacv.dto.UserRequestDTO;
import cz.upce.fei.nnpia.st67084.nnpiacv.dto.UserResponseDTO;
import cz.upce.fei.nnpia.st67084.nnpiacv.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findUser(@PathVariable Long id) {
        User user = userService.findUser(id);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(user.toDTO());
    }

    @GetMapping("")
    public ResponseEntity<Collection<UserResponseDTO>> findUsers(@RequestParam(name = "email", required = false) String email) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(userService.findUsers(email).stream().map(
                        User::toDTO).toList()
                );
    }

    @PostMapping("")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserRequestDTO userRequest) {
        log.info("Request for creating user {}", userRequest);
        User createdUser = userService.createUser(userRequest.toUser());

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdUser.toDTO());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.info("Request for deleting user with id: {}", id);
        if (userService.deleteUser(id)) {
            return ResponseEntity.noContent().build(); // 204 No Content, pokud je smazání úspěšné
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"); // 404 Not Found, pokud uživatel neexistuje
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @RequestBody UserRequestDTO userRequestDTO) {
        log.info("Request for updating user with id: {}, data: {}", id, userRequestDTO);
        User updatedUser = userService.updateUser(id,userRequestDTO.toUser());
        if (updatedUser != null) {
            return ResponseEntity.ok() // 200 OK
                    .body(updatedUser.toDTO());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"); // 404 Not Found, pokud uživatel neexistuje
        }
    }
}
