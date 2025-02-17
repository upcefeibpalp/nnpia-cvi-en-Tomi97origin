package cz.upce.fei.nnpia.st67084.nnpiacv.domain;

import lombok.*;

@AllArgsConstructor
@Data
public class User {
    private Long id;
    private String password;
    private String email;
}
