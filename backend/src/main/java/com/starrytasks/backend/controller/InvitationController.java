package com.starrytasks.backend.controller;

import com.starrytasks.backend.api.external.InvitationDTO;
import com.starrytasks.backend.api.external.UserDTO;
import com.starrytasks.backend.api.internal.User;
import com.starrytasks.backend.service.InvitationService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/invitations")
public class InvitationController {
    private final InvitationService invitationService;

    @GetMapping("/{invitationCode}")
    public ResponseEntity<InvitationDTO> getInvitation(@PathVariable String invitationCode) {
        InvitationDTO invitation = invitationService.getByInvitationCode(invitationCode);
        if (invitation != null && invitation.isActive() && !invitation.getExpirationDate().isBefore(LocalDate.now())) {
            return ResponseEntity.ok(invitation);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @PostMapping("/generate-for-user")
    public ResponseEntity<InvitationDTO> generateInvitationForUser(@AuthenticationPrincipal User currentUser) {
        if (currentUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        InvitationDTO newInvitation = invitationService.generateNewInvitationForUser(currentUser.getId());
        if (newInvitation != null) {
            return new ResponseEntity<>(newInvitation, HttpStatus.CREATED);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PostMapping("/{invitationCode}/accept")
    public ResponseEntity<?> acceptInvitation(@PathVariable String invitationCode, @AuthenticationPrincipal User currentUser) {
        boolean success = invitationService.acceptInvitation(invitationCode, currentUser.getId());
        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invitation not found or already accepted.");
        }
    }

}

