package com.starrytasks.backend.controller;
import com.starrytasks.backend.api.external.InvitationDTO;
import com.starrytasks.backend.api.external.UserDTO;
import com.starrytasks.backend.service.InvitationService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/invitations")
public class InvitationController {
    private final InvitationService invitationService;

    @GetMapping("/{invitationCode}")
    public InvitationDTO getInvitation(@PathVariable String invitationCode) {

        UserDTO generatedByUser = new UserDTO();
        generatedByUser.setId(2L);
        generatedByUser.setEmail("example@example.com");

        InvitationDTO invitation = new InvitationDTO();
        invitation.setInvitationCode(invitationCode);
        invitation.setGeneratedByUser(generatedByUser);
        invitation.setExpirationDate(LocalDate.now().plusDays(30));
        invitation.setActive(true);
        return invitation;
    }

    @GetMapping
    public ResponseEntity<List<InvitationDTO>> getAllInvitations() {
        List<InvitationDTO> allInvitations = invitationService.findAll();
        return ResponseEntity.ok(allInvitations);
    }

    @PostMapping("/generate-for-user")
    public ResponseEntity<InvitationDTO> generateInvitationForUser(@RequestBody UserDTO userDTO) {
        InvitationDTO newInvitation = invitationService.generateNewInvitationForUser(userDTO.getId());
        if (newInvitation != null) {
            return new ResponseEntity<>(newInvitation, HttpStatus.CREATED);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }



    @PostMapping("/{invitationCode}/accept")
    public ResponseEntity<?> acceptInvitation(@PathVariable String invitationCode) {
        boolean success = invitationService.acceptInvitation(invitationCode);
        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invitation not found or already accepted/declined.");
        }
    }

    @PostMapping("/{invitationCode}/decline")
    public ResponseEntity<?> declineInvitation(@PathVariable String invitationCode) {
        boolean success = invitationService.declineInvitation(invitationCode);
        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invitation not found or already accepted/declined.");
        }
    }

    @PatchMapping("/{invitationCode}/deactivate")
    public ResponseEntity<?> deactivateInvitation(@PathVariable String invitationCode) {
        boolean success = invitationService.deactivateInvitation(invitationCode);
        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invitation not found or already deactivated.");
        }
    }



}
