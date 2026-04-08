package workshopHub.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String role;
    private String avatar;
    private String avatarGradient;
    private String joinedDate;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_registrations",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "workshop_id")
    )
    private Set<Workshop> registeredWorkshops = new HashSet<>();
}
