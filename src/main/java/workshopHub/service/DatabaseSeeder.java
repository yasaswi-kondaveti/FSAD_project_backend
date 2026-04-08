package workshopHub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import workshopHub.entity.User;
import workshopHub.entity.Workshop;
import workshopHub.repository.UserRepository;
import workshopHub.repository.WorkshopRepository;

import java.util.Arrays;

@Service
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private WorkshopRepository workshopRepo;

    @Override
    public void run(String... args) throws Exception {
        // Only seed if database is empty
        if (workshopRepo.count() == 0) {
            // Seed Workshops
            Workshop w1 = new Workshop();
            w1.setTitle("Advanced React Patterns");
            w1.setInstructor("Sarah Chen");
            w1.setCategory("Development");
            w1.setDate("Feb 24, 2026");
            w1.setTime("10:00 AM EST");
            w1.setDuration("3h");
            w1.setCapacity(30);
            w1.setRegistered(22);
            w1.setStatus("upcoming");
            w1.setLevel("Advanced");
            w1.setThumbnail("⚛️");
            w1.setColor("#3B82F6");
            w1.setDescription("Deep-dive into advanced React patterns including compound components, render props, and custom hooks. Walk away with practical patterns you can apply to real projects immediately.");
            w1.setMaterials(Arrays.asList("slides.pdf", "starter-code.zip"));
            w1.setTags(Arrays.asList("React", "JavaScript", "Hooks"));
            
            Workshop w2 = new Workshop();
            w2.setTitle("UI/UX Design Fundamentals");
            w2.setInstructor("Marcus Rivera");
            w2.setCategory("Design");
            w2.setDate("Feb 26, 2026");
            w2.setTime("2:00 PM EST");
            w2.setDuration("2h");
            w2.setCapacity(25);
            w2.setRegistered(25);
            w2.setStatus("full");
            w2.setLevel("Beginner");
            w2.setThumbnail("🎨");
            w2.setColor("#EC4899");
            w2.setDescription("Learn core design principles: typography, color theory, layout, and user-centered thinking. Perfect for developers and aspiring designers who want to build beautiful, usable interfaces.");
            w2.setMaterials(Arrays.asList("design-kit.fig"));
            w2.setTags(Arrays.asList("Figma", "Design", "UX"));

            Workshop w3 = new Workshop();
            w3.setTitle("Machine Learning Basics");
            w3.setInstructor("Dr. Priya Nair");
            w3.setCategory("Data Science");
            w3.setDate("Mar 3, 2026");
            w3.setTime("11:00 AM EST");
            w3.setDuration("4h");
            w3.setCapacity(20);
            w3.setRegistered(8);
            w3.setStatus("upcoming");
            w3.setLevel("Intermediate");
            w3.setThumbnail("🤖");
            w3.setColor("#10B981");
            w3.setDescription("Introduction to ML concepts, supervised learning, and building your first predictive model with Python and scikit-learn. No prior ML experience needed — just Python basics.");
            w3.setMaterials(Arrays.asList("notebook.ipynb"));
            w3.setTags(Arrays.asList("Python", "ML", "Data"));
            
            Workshop w4 = new Workshop();
            w4.setTitle("Product Management 101");
            w4.setInstructor("James Okafor");
            w4.setCategory("Business");
            w4.setDate("Mar 7, 2026");
            w4.setTime("3:00 PM EST");
            w4.setDuration("2.5h");
            w4.setCapacity(35);
            w4.setRegistered(19);
            w4.setStatus("upcoming");
            w4.setLevel("Beginner");
            w4.setThumbnail("📊");
            w4.setColor("#F59E0B");
            w4.setDescription("From ideation to launch — learn how PMs prioritize, plan, and ship products that users love. Covers frameworks like RICE scoring, roadmap building, and stakeholder alignment.");
            w4.setMaterials(Arrays.asList("pm-framework.pdf", "roadmap-template.xlsx"));
            w4.setTags(Arrays.asList("Product", "Strategy", "Agile"));

            Workshop w5 = new Workshop();
            w5.setTitle("Cloud Architecture with AWS");
            w5.setInstructor("Lei Zhang");
            w5.setCategory("Development");
            w5.setDate("Feb 20, 2026");
            w5.setTime("9:00 AM EST");
            w5.setDuration("3h");
            w5.setCapacity(20);
            w5.setRegistered(20);
            w5.setStatus("live");
            w5.setLevel("Advanced");
            w5.setThumbnail("☁️");
            w5.setColor("#8B5CF6");
            w5.setDescription("Design scalable, fault-tolerant cloud systems using AWS core services and best practices. Covers VPC, EC2, RDS, Lambda, and infrastructure-as-code with Terraform.");
            w5.setMaterials(Arrays.asList("aws-architecture.pdf"));
            w5.setTags(Arrays.asList("AWS", "Cloud", "DevOps"));

            Workshop w6 = new Workshop();
            w6.setTitle("Brand Storytelling");
            w6.setInstructor("Amara Diallo");
            w6.setCategory("Marketing");
            w6.setDate("Feb 16, 2026");
            w6.setTime("1:00 PM EST");
            w6.setDuration("2h");
            w6.setCapacity(40);
            w6.setRegistered(40);
            w6.setStatus("completed");
            w6.setLevel("Beginner");
            w6.setThumbnail("✍️");
            w6.setColor("#EF4444");
            w6.setDescription("Craft compelling brand narratives that connect emotionally with your audience and drive action. Includes exercises in voice, tone, and content strategy.");
            w6.setMaterials(Arrays.asList("storytelling-guide.pdf", "recording.mp4"));
            w6.setTags(Arrays.asList("Marketing", "Content", "Brand"));

            workshopRepo.saveAll(Arrays.asList(w1, w2, w3, w4, w5, w6));
            
            // Re-fetch to link to users
            w1 = workshopRepo.findById(1L).orElse(w1);
            w3 = workshopRepo.findById(3L).orElse(w3);
            w6 = workshopRepo.findById(6L).orElse(w6);
        }

        if (userRepo.count() == 0) {
            // Seed Demo Accounts
            User u1 = new User();
            u1.setName("Jamie Lee");
            u1.setEmail("user@workshophub.com");
            u1.setPassword("password123");
            u1.setRole("user");
            u1.setAvatar("J");
            u1.setAvatarGradient("linear-gradient(135deg, #818CF8, #06B6D4)");
            u1.setJoinedDate("January 2026");

            User u2 = new User();
            u2.setName("Alex Morgan");
            u2.setEmail("admin@workshophub.com");
            u2.setPassword("admin123");
            u2.setRole("admin");
            u2.setAvatar("A");
            u2.setAvatarGradient("linear-gradient(135deg, #F59E0B, #EF4444)");
            u2.setJoinedDate("December 2025");

            userRepo.saveAll(Arrays.asList(u1, u2));
            
            // Link u1 to some workshops (ids 1, 3, 6 as in INITIAL_REGISTRATIONS frontend)
            u1.getRegisteredWorkshops().add(workshopRepo.findById(1L).orElse(null));
            u1.getRegisteredWorkshops().add(workshopRepo.findById(3L).orElse(null));
            u1.getRegisteredWorkshops().add(workshopRepo.findById(6L).orElse(null));
            userRepo.save(u1);
        }
    }
}
