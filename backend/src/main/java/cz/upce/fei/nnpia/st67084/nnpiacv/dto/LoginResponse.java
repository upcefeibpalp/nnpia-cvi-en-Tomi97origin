package cz.upce.fei.nnpia.st67084.nnpiacv.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginResponse {

    private String token;

    private long expiresIn;

}