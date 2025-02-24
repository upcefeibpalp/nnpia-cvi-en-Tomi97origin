package cz.upce.fei.nnpia.st67084.nnpiacv.controllers;

import cz.upce.fei.nnpia.st67084.nnpiacv.domain.User;
import cz.upce.fei.nnpia.st67084.nnpiacv.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/{id}")
    public User findUser(@PathVariable Long id) {
        User user = userService.findUser(id);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return user;
    }

    @GetMapping("/users")
    public Collection<User> findUsers(@RequestParam(name = "email", required = false) String email) {
        return userService.findUsers(email);
    }
}
