package com.starrytasks.backend.service;

import com.starrytasks.backend.api.external.InvitationDTO;

import java.util.List;

public interface InvitationService {
    InvitationDTO get();
    InvitationDTO generateNewInvitationForUser(Long userId);
    InvitationDTO getByInvitationCode(String invitationCode);
    boolean acceptInvitation(String invitationCode, Long id);

}
