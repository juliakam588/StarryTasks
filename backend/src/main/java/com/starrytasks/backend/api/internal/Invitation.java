package com.starrytasks.backend.api.internal;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "invitations")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Invitation {
    @Id
    @Column(nullable = false, unique = true)
    private String invitationCode;

    @ManyToOne
    @JoinColumn(name = "generatedByUserId", nullable = false)
    private User generatedByUser;

    @Column(nullable = false)
    private LocalDate expirationDate;

    @Column(nullable = false)
    private boolean isActive;

    @Transient
    public boolean isExpired() {
        return expirationDate.isBefore(LocalDate.now());
    }


}
