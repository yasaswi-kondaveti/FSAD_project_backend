package workshopHub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import workshopHub.entity.User;
import workshopHub.repository.UserRepository;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository repo;

    @PutMapping("/{id}")
    public User updateProfile(@PathVariable Long id, @RequestBody User newUserData) {
        User existingUser = repo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        
        // Update primitive fields
        if(newUserData.getName() != null && !newUserData.getName().isEmpty()) {
            existingUser.setName(newUserData.getName());
        }
        if(newUserData.getEmail() != null && !newUserData.getEmail().isEmpty()) {
            existingUser.setEmail(newUserData.getEmail());
        }
        if(newUserData.getPassword() != null && !newUserData.getPassword().isEmpty()) {
            existingUser.setPassword(newUserData.getPassword());
        }
        if(newUserData.getAvatar() != null && !newUserData.getAvatar().isEmpty()) {
            existingUser.setAvatar(newUserData.getAvatar());
        }
        if(newUserData.getAvatarGradient() != null && !newUserData.getAvatarGradient().isEmpty()) {
            existingUser.setAvatarGradient(newUserData.getAvatarGradient());
        }

        return repo.save(existingUser);
    }
}
