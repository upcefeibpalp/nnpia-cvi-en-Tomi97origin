package cz.upce.fei.nnpia.st67084.nnpiacv.domain;


import cz.upce.fei.nnpia.st67084.nnpiacv.dto.UserResponseDTO;
import jakarta.persistence.*;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "app_user")
@RequiredArgsConstructor
@Data
@NoArgsConstructor
public class User  implements UserDetails {
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
