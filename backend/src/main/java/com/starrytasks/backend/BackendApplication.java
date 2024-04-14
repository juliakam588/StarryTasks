package com.starrytasks.backend;

import com.starrytasks.backend.api.internal.Invitation;
import com.starrytasks.backend.api.internal.Role;
import com.starrytasks.backend.api.internal.User;
import com.starrytasks.backend.api.internal.UserProfile;
import com.starrytasks.backend.repository.InvitationRepository;
import com.starrytasks.backend.repository.RoleRepository;
import com.starrytasks.backend.repository.UserRepository;
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
                                  final RoleRepository roleRepository) {
        return args -> {
            Role roleChild = new Role(1L,"Child");
            Role roleParent = new Role(2L, "Parent");

            roleRepository.save(roleChild);
            roleRepository.save(roleParent);
            Role userRole = roleRepository.findRoleByName("Parent").orElseThrow(() -> new RuntimeException("Role not found"));

            User user = new User();
            user.setEmail("parent@example.com");
            user.setPassword("password123");
            user.setRole(userRole);

            UserProfile userProfile = new UserProfile();
            userProfile.setUser(user);
            user.setUserProfile(userProfile);

            user = userRepository.save(user);


            Invitation invitation = new Invitation();
            invitation.setInvitationCode("CODE");
            invitation.setGeneratedByUser(user);
            invitation.setExpirationDate(LocalDate.now().plusDays(30));
            invitation.setActive(true);

            invitationRepository.save(invitation);
        };

    }
}
