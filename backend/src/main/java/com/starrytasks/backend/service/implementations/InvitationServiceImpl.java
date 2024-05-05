package com.starrytasks.backend.service.implementations;

import com.starrytasks.backend.api.external.InvitationDTO;
import com.starrytasks.backend.api.external.UserDTO;
import com.starrytasks.backend.api.internal.Invitation;
import com.starrytasks.backend.api.internal.User;
import com.starrytasks.backend.mapper.InvitationMapper;
import com.starrytasks.backend.repository.InvitationRepository;
import com.starrytasks.backend.repository.UserRepository;
import com.starrytasks.backend.service.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvitationServiceImpl implements InvitationService {

    private final InvitationMapper invitationMapper;
    private final InvitationRepository invitationRepository;
    private final UserRepository userRepository;


    @Override
    public InvitationDTO get() {
        return null;
    }

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
                .setGeneratedByUser(new UserDTO().
                        setId(user.getId())
                        .setEmail(user.getEmail()))
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

                return true;
            }
        }
        return false;
    }


}
