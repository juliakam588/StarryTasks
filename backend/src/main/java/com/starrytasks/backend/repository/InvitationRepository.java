package com.starrytasks.backend.repository;

import com.starrytasks.backend.api.internal.Invitation;
import com.starrytasks.backend.api.internal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Integer> {
    Optional<Invitation> findInvitationByInvitationCode(String invitationCode);
    Optional<Invitation> findInvitationByGeneratedByUser(User user);
    List<Invitation> findInvitationsByGeneratedByUser(User user);

}
