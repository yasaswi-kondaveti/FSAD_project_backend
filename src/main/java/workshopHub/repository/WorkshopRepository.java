package workshopHub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import workshopHub.entity.Workshop;

public interface WorkshopRepository extends JpaRepository<Workshop, Long> {
}