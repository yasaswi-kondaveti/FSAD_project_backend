package workshopHub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import workshopHub.entity.User;
import workshopHub.entity.Workshop;
import workshopHub.repository.UserRepository;
import workshopHub.repository.WorkshopRepository;
import workshopHub.service.FileStorageService;
import workshopHub.service.CertificateService;
import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/workshops")
@CrossOrigin(origins = "*")
public class WorkshopController {

    @Autowired
    private WorkshopRepository workshopRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private FileStorageService fileService;

    @Autowired
    private CertificateService certService;

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
        
        if (newWorkshopData.getStatus() != null) {
            existing.setStatus(newWorkshopData.getStatus());
        }
        
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

    // Get all users registered for this workshop
    @GetMapping("/{id}/roster")
    public List<User> getWorkshopRoster(@PathVariable Long id) {
        return userRepo.findByRegisteredWorkshopsId(id);
    }

    @GetMapping("/{id}/certificate")
    public ResponseEntity<byte[]> downloadCertificate(@PathVariable Long id, @RequestParam Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Workshop workshop = workshopRepo.findById(id).orElseThrow(() -> new RuntimeException("Workshop not found"));
        
        try {
            byte[] pdfBytes = certService.generateCertificate(user.getName(), workshop.getTitle());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            String filename = "Certificate_" + (workshop.getTitle() == null ? "Workshop" : workshop.getTitle().replaceAll(" ", "_")) + ".pdf";
            headers.setContentDispositionFormData("attachment", filename);
            return ResponseEntity.ok().headers(headers).body(pdfBytes);
        } catch (Throwable e) {
            java.io.StringWriter sw = new java.io.StringWriter();
            e.printStackTrace(new java.io.PrintWriter(sw));
            return ResponseEntity.status(500).body(sw.toString().getBytes());
        }
    }

    @PostMapping("/{id}/materials")
    public Workshop uploadMaterial(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        Workshop w = workshopRepo.findById(id).orElseThrow(() -> new RuntimeException("Workshop not found"));
        String uniqueName = fileService.storeFile(file);
        
        List<String> materials = w.getMaterials();
        if (materials == null) materials = new ArrayList<>();
        
        if (!materials.contains(uniqueName)) {
            materials.add(uniqueName);
            w.setMaterials(materials);
            workshopRepo.save(w);
        }
        return w;
    }

    @DeleteMapping("/{id}/materials/{fileName:.+}")
    public Workshop removeMaterial(@PathVariable Long id, @PathVariable String fileName) {
        Workshop w = workshopRepo.findById(id).orElseThrow(() -> new RuntimeException("Workshop not found"));
        List<String> materials = w.getMaterials();
        if (materials != null && materials.contains(fileName)) {
            materials.remove(fileName);
            w.setMaterials(materials);
            workshopRepo.save(w);
        }
        return w;
    }
}