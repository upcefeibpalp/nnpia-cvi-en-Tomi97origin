package cz.upce.fei.nnpia.st67084.nnpiacv.services;

import cz.upce.fei.nnpia.st67084.nnpiacv.domain.User;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;


@Service
public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final HashMap<Long, User> users = new HashMap<>();

    @PostConstruct
    public void init() {
        Arrays.asList(
                new User(1L, "JohnDoe",
                        "john@doe.com"),
                new User(2L,"password",
                        "xxx@x.com" )
        ).forEach(user -> {
            users.put(user.getId(), user);
        });
    }

    public User findUser(Long id) {
        User user = users.get(id);

        if (user != null) {
            log.atDebug().log("User found with id {}: {}", id, user);
            return user;
        } else {
            log.atDebug().log("User not found with id: {}", id);
            return null;
        }
    }

    public Collection<User> findUsers() {
        return users.values();
    }
}
