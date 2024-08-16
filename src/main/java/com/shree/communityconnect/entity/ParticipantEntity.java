package com.shree.communityconnect.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity()
@Table(name = "PARTICIPANTS")
public class ParticipantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private String id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserEntity userEntity;

    @ManyToMany(mappedBy = "participantEntityList", cascade = CascadeType.ALL)
    private List<ActivityEntity> registeredActivity = new ArrayList<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public List<ActivityEntity> getRegisteredActivity() {
        return registeredActivity;
    }

    public void setRegisteredActivity(List<ActivityEntity> registeredActivity) {
        this.registeredActivity = registeredActivity;
    }
}
