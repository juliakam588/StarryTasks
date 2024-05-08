package com.starrytasks.backend.api.internal;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Entity
@Table(name="user_profiles")
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserProfile {
    @Id
    private Long id;
    private String name;
    @Column(length = 500)
    private String profilePicturePath;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }


}
