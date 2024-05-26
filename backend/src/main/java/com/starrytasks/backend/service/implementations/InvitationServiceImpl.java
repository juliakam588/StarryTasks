package com.starrytasks.backend.service.implementations;

import com.starrytasks.backend.api.external.InvitationDTO;
import com.starrytasks.backend.api.external.UserDTO;
import com.starrytasks.backend.api.internal.Invitation;
import com.starrytasks.backend.api.internal.User;
import com.starrytasks.backend.api.internal.UserStars;
import com.starrytasks.backend.mapper.InvitationMapper;
import com.starrytasks.backend.mapper.UserMapper;
import com.starrytasks.backend.repository.InvitationRepository;
import com.starrytasks.backend.repository.UserRepository;
import com.starrytasks.backend.repository.UserStarsRepository;
import com.starrytasks.backend.service.InvitationService;
import lombok.RequiredArgsConstructor;
import org.hibernate.NonUniqueResultException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final InvitationMapper invitationMapper;
    private final InvitationRepository invitationRepository;
    private final UserRepository userRepository;
    private final UserStarsRepository userStarsRepository;
    private final UserMapper userMapper;


    @Override
    public InvitationDTO getByInvitationCode(String invitationCode) {
        return invitationRepository.findInvitationByInvitationCode(invitationCode)
                .map(invitationMapper::map)
                .orElseThrow(() -> new RuntimeException("Invitation not found"));
    }

    @Override
    public InvitationDTO generateNewInvitationForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String invitationCode = generateRandomCode(8);
        LocalDate expirationDate = LocalDate.now().plusDays(30);

        Invitation invitation = new Invitation();
        invitation.setInvitationCode(invitationCode)
                .setGeneratedByUser(user)
                .setExpirationDate(expirationDate)
                .setActive(true);

        invitationRepository.save(invitation);

        return new InvitationDTO()
                .setInvitationCode(invitationCode)
                .setGeneratedByUser(userMapper.map(user))
                .setExpirationDate(expirationDate)
                .setActive(true);
    }

    private String generateRandomCode(int length) {
        String allowedChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        return random.ints(length, 0, allowedChars.length())
                .mapToObj(allowedChars::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());
    }


    @Override
    public boolean acceptInvitation(String invitationCode, Long childUserId) {
        Optional<Invitation> invitationOpt = invitationRepository.findInvitationByInvitationCode(invitationCode);

        if (invitationOpt.isPresent()) {
            Invitation invitation = invitationOpt.get();

            if (!invitation.isExpired() || invitation.isActive()) {
                User child = userRepository.findById(childUserId)
                        .orElseThrow(() -> new RuntimeException("User not found"));

                child.setParent(userRepository.findById(invitation.getGeneratedByUser().getId())
                        .orElseThrow(() -> new RuntimeException("Parent not found")));


                userRepository.save(child);

                invitation.setActive(false);
                invitationRepository.save(invitation);

                UserStars userStars = new UserStars();
                userStars.setTotalStars(0);
                userStars.setStarsSpent(0);
                userStars.setUser(child);
                userStarsRepository.save(userStars);

                return true;
            }
        }
        return false;
    }

    @Override
    public boolean hasUserValidInvitation(User user) {
        List<Invitation> invitations = invitationRepository.findInvitationsByGeneratedByUser(user);
        if (invitations.isEmpty()) {
            return false;
        } else if (invitations.size() == 1) {
            return invitations.get(0).isActive();
        } else {
            invitationRepository.deleteAll(invitations);
            return false;
        }
    }

    @Override
    public InvitationDTO getInvitationByUser(User user) {
        List<Invitation> invitations = invitationRepository.findInvitationsByGeneratedByUser(user);
        if (invitations.isEmpty()) {
            throw new RuntimeException("Invitation not found");
        } else if (invitations.size() == 1) {
            return invitationMapper.map(invitations.get(0));
        } else {
            invitationRepository.deleteAll(invitations);
            throw new RuntimeException("Multiple invitations found and deleted");
        }
    }

}
