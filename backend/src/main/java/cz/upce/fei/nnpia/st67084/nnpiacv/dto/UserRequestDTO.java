package cz.upce.fei.nnpia.st67084.nnpiacv.dto;

import cz.upce.fei.nnpia.st67084.nnpiacv.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDTO {
    private String email;
    private String password;

    public User toUser(){
        return new User(email, password);
    }
}
