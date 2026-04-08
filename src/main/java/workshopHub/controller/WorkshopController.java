package workshopHub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import workshopHub.entity.User;
import workshopHub.entity.Workshop;
import workshopHub.repository.UserRepository;
import workshopHub.repository.WorkshopRepository;
import java.util.List;

@RestController
@RequestMapping("/api/workshops")
@CrossOrigin(origins = "*")
public class WorkshopController {

    @Autowired
    private WorkshopRepository workshopRepo;

    @Autowired
    private UserRepository userRepo;

    @GetMapping
    public List<Workshop> getAll() {
        return workshopRepo.findAll();
    }

    @PostMapping
    public Workshop create(@RequestBody Workshop workshop) {
        return workshopRepo.save(workshop);
    }

    @PutMapping("/{id}")
    public Workshop update(@PathVariable Long id, @RequestBody Workshop newWorkshopData) {
        Workshop existing = workshopRepo.findById(id).orElseThrow(() -> new RuntimeException("Workshop not found"));
        // Safely update properties, avoiding ID or foreign overrides
        existing.setTitle(newWorkshopData.getTitle());
        existing.setInstructor(newWorkshopData.getInstructor());
        existing.setDate(newWorkshopData.getDate());
        existing.setTime(newWorkshopData.getTime());
        existing.setDuration(newWorkshopData.getDuration());
        existing.setCategory(newWorkshopData.getCategory());
        existing.setLevel(newWorkshopData.getLevel());
        existing.setCapacity(newWorkshopData.getCapacity());
        existing.setDescription(newWorkshopData.getDescription());
        
        // Persist color/thumbnail if provided or fallback
        if(newWorkshopData.getThumbnail() != null) existing.setThumbnail(newWorkshopData.getThumbnail());
        if(newWorkshopData.getColor() != null) existing.setColor(newWorkshopData.getColor());
        
        return workshopRepo.save(existing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        workshopRepo.deleteById(id);
    }

    // Register a user for a workshop
    @PostMapping("/{id}/register")
    public User register(@PathVariable Long id, @RequestParam Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Workshop workshop = workshopRepo.findById(id).orElseThrow(() -> new RuntimeException("Workshop not found"));
        
        if (!user.getRegisteredWorkshops().contains(workshop)) {
            user.getRegisteredWorkshops().add(workshop);
            workshop.setRegistered(workshop.getRegistered() == null ? 1 : workshop.getRegistered() + 1);
            workshopRepo.save(workshop);
        }
        
        return userRepo.save(user);
    }

    // Unregister a user from a workshop
    @DeleteMapping("/{id}/register")
    public User unregister(@PathVariable Long id, @RequestParam Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Workshop workshop = workshopRepo.findById(id).orElseThrow(() -> new RuntimeException("Workshop not found"));
        
        if (user.getRegisteredWorkshops().contains(workshop)) {
            user.getRegisteredWorkshops().remove(workshop);
            if(workshop.getRegistered() != null && workshop.getRegistered() > 0) {
                workshop.setRegistered(workshop.getRegistered() - 1);
            }
            workshopRepo.save(workshop);
        }
        
        return userRepo.save(user);
    }
}