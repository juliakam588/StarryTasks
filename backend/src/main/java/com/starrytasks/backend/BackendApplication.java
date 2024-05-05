package com.starrytasks.backend;

import com.starrytasks.backend.api.internal.*;
import com.starrytasks.backend.repository.InvitationRepository;
import com.starrytasks.backend.repository.RoleRepository;
import com.starrytasks.backend.repository.UserRepository;
import com.starrytasks.backend.repository.UserStarsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(final UserRepository userRepository,
                                  final InvitationRepository invitationRepository,
                                  final RoleRepository roleRepository,
                                  final UserStarsRepository userStarsRepository) {
        return args -> {
            Role roleChild = new Role(1L,"Child");
            Role roleParent = new Role(2L, "Parent");

            roleRepository.save(roleChild);
            roleRepository.save(roleParent);
            Role userRole = roleRepository.findRoleByName("Parent").orElseThrow(() -> new RuntimeException("Role not found"));
            Role userRoleCh = roleRepository.findRoleByName("Child").orElseThrow(() -> new RuntimeException("Role not found"));

            //creating parent
            User user = new User();
            user.setEmail("parent@example.com");
            user.setPassword("password123");
            user.setRole(userRole);

            UserProfile userProfile = new UserProfile();
            userProfile.setUser(user);
            userProfile.setName("Anne");
            user.setUserProfile(userProfile);

            user = userRepository.save(user);

            //creating child
            User user1 = new User();
            user1.setParent(user);
            user1.setEmail("sam@example.com");
            user1.setPassword("haslo");
            user1.setRole(userRoleCh);

            UserProfile userProfile1 = new UserProfile();
            userProfile1.setUser(user1);
            userProfile1.setName("Sam");
            userProfile1.setProfilePicturePath("/lol");
            user1.setUserProfile(userProfile1);

            UserStars userStars = new UserStars()
                    .setUser(user1)
                    .setTotalStars(60)
                    .setStarsSpent(40);

            userStarsRepository.save(userStars);
            user1 = userRepository.save(user1);


            Invitation invitation = new Invitation();
            invitation.setInvitationCode("CODE");
            invitation.setGeneratedByUser(user);
            invitation.setExpirationDate(LocalDate.now().plusDays(30));
            invitation.setActive(true);

            invitationRepository.save(invitation);
        };

    }
}
