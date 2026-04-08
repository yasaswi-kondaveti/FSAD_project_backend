package workshopHub.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Entity
@Data
public class Workshop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String instructor;
    private String category;
    private String date;
    private String time;
    private String duration;
    private Integer capacity;
    
    @Column(name = "registered_count")
    private Integer registered;
    
    private String status;
    private String level;
    private String thumbnail;
    private String color;
    
    @Column(length = 2000)
    private String description;

    @ElementCollection
    private List<String> materials;

    @ElementCollection
    private List<String> tags;
}