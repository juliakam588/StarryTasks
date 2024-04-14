package com.starrytasks.backend.service.implementations;

import com.starrytasks.backend.api.external.InvitationDTO;
import com.starrytasks.backend.api.internal.Invitation;
import com.starrytasks.backend.api.internal.User;
import com.starrytasks.backend.mapper.InvitationMapper;
import com.starrytasks.backend.repository.InvitationRepository;
import com.starrytasks.backend.repository.UserRepository;
import com.starrytasks.backend.service.InvitationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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
        User user = userRepository.findById(Math.toIntExact(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Invitation newInvitation = new Invitation();
        newInvitation.setInvitationCode(UUID.randomUUID().toString());
        newInvitation.setExpirationDate(LocalDate.now().plusWeeks(2));
        newInvitation.setActive(true);
        newInvitation.setGeneratedByUser(user);

        Invitation savedInvitation = invitationRepository.save(newInvitation);

        return invitationMapper.map(savedInvitation);
    }


    @Override
    public boolean acceptInvitation(String invitationCode) {
        return false;
    }

    @Override
    public boolean declineInvitation(String invitationCode) {
        return false;
    }

    @Override
    public boolean deactivateInvitation(String invitationCode) {
        return false;
    }

    @Override
    public List<InvitationDTO> findAll() {
        return List.of();
    }
}
