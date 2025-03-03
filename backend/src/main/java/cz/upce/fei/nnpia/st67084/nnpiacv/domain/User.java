package cz.upce.fei.nnpia.st67084.nnpiacv.domain;


import cz.upce.fei.nnpia.st67084.nnpiacv.dto.UserResponseDTO;
import jakarta.persistence.*;

import lombok.*;


@Entity
@Table(name = "app_user")
@RequiredArgsConstructor
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String password;
    @Column(unique = true)
    @NonNull
    private String email;

    public UserResponseDTO toDTO() {
        return UserResponseDTO.builder()
                .id(getId())
                .email(getEmail())
                .password(getPassword())
                .build();
    }

}
