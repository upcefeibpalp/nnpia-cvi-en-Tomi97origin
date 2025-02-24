package cz.upce.fei.nnpia.st67084.nnpiacv.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_profile")
@Data
@AllArgsConstructor
@NoArgsConstructor // Required for JPA entities
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String profilePictureUrl; // Example attribute not related to the relationship

    @OneToOne(fetch = FetchType.LAZY) // Define OneToOne relationship with User
    @JoinColumn(name = "user_id", referencedColumnName = "id") // Specify foreign key column
    private User user;
}