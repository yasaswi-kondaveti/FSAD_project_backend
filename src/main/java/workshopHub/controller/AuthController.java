package workshopHub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import workshopHub.entity.User;
import workshopHub.repository.UserRepository;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository repo;

    @PostMapping("/signup")
    public User signup(@RequestBody User user) {
        if (repo.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("Email already in use");
        }
        
        if (user.getRole() == null) user.setRole("user");
        if (user.getAvatar() == null && user.getName() != null && !user.getName().isEmpty()) {
            user.setAvatar(user.getName().substring(0, 1).toUpperCase());
        }
        if (user.getAvatarGradient() == null) {
            user.setAvatarGradient("linear-gradient(135deg, #818CF8, #06B6D4)");
        }
        if (user.getJoinedDate() == null) {
            user.setJoinedDate("February 2026");
        }

        return repo.save(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody User user) {
        User existingUser = repo.findByEmail(user.getEmail());

        if (existingUser != null &&
            existingUser.getPassword().equals(user.getPassword())) {
            return existingUser;
        }

        throw new RuntimeException("Invalid credentials");
    }
}