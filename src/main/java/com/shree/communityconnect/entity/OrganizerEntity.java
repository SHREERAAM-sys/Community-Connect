package com.shree.communityconnect.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ORGANIZERS")
public class OrganizerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID", nullable = false)
    private UserEntity userEntity;

    @OneToMany(mappedBy = "organizerEntity", cascade = CascadeType.ALL)
    @Column(name = "ACTIVITY_ID")
    private List<ActivityEntity> activityEntityList = new ArrayList<>();

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

    public List<ActivityEntity> getActivityEntityList() {
        return activityEntityList;
    }

    public void setActivityEntityList(List<ActivityEntity> activityEntityList) {
        this.activityEntityList = activityEntityList;
    }
}
