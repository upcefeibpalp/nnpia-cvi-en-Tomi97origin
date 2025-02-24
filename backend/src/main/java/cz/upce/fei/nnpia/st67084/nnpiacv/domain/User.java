package cz.upce.fei.nnpia.st67084.nnpiacv.domain;

import jakarta.persistence.*;

import lombok.*;


@Entity
@Table(name = "app_user")
@AllArgsConstructor
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String password;
    @Column(unique = true)
    private String email;

}
