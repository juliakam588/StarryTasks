package com.starrytasks.backend.service;

import com.starrytasks.backend.api.external.InvitationDTO;
import com.starrytasks.backend.api.internal.User;

import java.util.List;

public interface InvitationService {
    InvitationDTO generateNewInvitationForUser(Long userId);
    InvitationDTO getByInvitationCode(String invitationCode);
    boolean acceptInvitation(String invitationCode, Long id);

    boolean hasUserValidInvitation(User user);

    InvitationDTO getInvitationByUser(User user);
}
