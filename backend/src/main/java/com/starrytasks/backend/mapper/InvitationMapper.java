package com.starrytasks.backend.mapper;

import com.starrytasks.backend.api.external.InvitationDTO;
import com.starrytasks.backend.api.internal.Invitation;
import org.springframework.stereotype.Component;

@Component
public class InvitationMapper {

    private final UserMapper userMapper = new UserMapper();

    public InvitationDTO map(Invitation invitation) {
        return new InvitationDTO()
                .setInvitationCode(invitation.getInvitationCode())
                .setGeneratedByUser(userMapper.map(invitation.getGeneratedByUser()))
                .setExpirationDate(invitation.getExpirationDate())
                .setActive(invitation.isActive());
    }


    public Invitation map(InvitationDTO invitationDTO) {
        return new Invitation()
                .setInvitationCode(invitationDTO.getInvitationCode())
                .setGeneratedByUser(userMapper.map(invitationDTO.getGeneratedByUser()))
                .setExpirationDate(invitationDTO.getExpirationDate())
                .setActive(invitationDTO.isActive());
    }
}
