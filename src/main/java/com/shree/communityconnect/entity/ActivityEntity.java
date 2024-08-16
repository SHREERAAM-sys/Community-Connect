package com.shree.communityconnect.entity;

import com.shree.communityconnect.constants.ActivityCategory;
import com.shree.communityconnect.constants.ActivityType;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ACTIVITY")
public class ActivityEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    private String id;

    @Column(name = "ACTIVITY_NAME", nullable = false)
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CATEGORY", nullable = false)
    @Enumerated(EnumType.STRING)
    private ActivityCategory category;

    @Column(name = "TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private ActivityType type;

    @Column(name = "TOTAL_CAPACITY")
    private Integer totalCapacity;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "ORGANIZER_ID", nullable = false)
    private OrganizerEntity organizerEntity;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "PARTICIPANT_ACTIVITY",
            joinColumns = @JoinColumn(name = "PARTICIPANT_ID"),
            inverseJoinColumns = @JoinColumn(name = "ACTIVITY_ID"))
    private List<ParticipantEntity> participantEntityList = new ArrayList<>();

    @Column(name = "DATE", nullable = false)
    private LocalDateTime activityDate;

    @Column(name = "POSTED_DATE", nullable = false)
    private LocalDateTime datePosted;



    public ActivityEntity() {
    }

    public ActivityEntity(String id, String name, String description, ActivityCategory category, ActivityType type, int totalCapacity, OrganizerEntity organizerEntity, List<ParticipantEntity> participantEntityList, LocalDateTime activityDate, LocalDateTime datePosted) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.type = type;
        this.totalCapacity = totalCapacity;
        this.organizerEntity = organizerEntity;
        this.participantEntityList = participantEntityList;
        this.activityDate = activityDate;
        this.datePosted = datePosted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ActivityCategory getCategory() {
        return category;
    }

    public void setCategory(ActivityCategory category) {
        this.category = category;
    }

    public ActivityType getType() {
        return type;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public Integer getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(Integer totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public OrganizerEntity getOrganizerEntity() {
        return organizerEntity;
    }

    public void setOrganizerEntity(OrganizerEntity organizerEntity) {
        this.organizerEntity = organizerEntity;
    }

    public List<ParticipantEntity> getParticipantEntityList() {
        return participantEntityList;
    }

    public void setParticipantEntityList(List<ParticipantEntity> participantEntityList) {
        this.participantEntityList = participantEntityList;
    }

    public LocalDateTime getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(LocalDateTime activityDate) {
        this.activityDate = activityDate;
    }

    public LocalDateTime getDatePosted() {
        return datePosted;
    }

    public void setDatePosted(LocalDateTime datePosted) {
        this.datePosted = datePosted;
    }

    @Override
    public String toString() {
        return "ActivityEntity{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", type=" + type +
                ", totalCapacity=" + totalCapacity +
                ", organizerEntity=" + organizerEntity +
                ", participantEntityList=" + participantEntityList +
                ", activityDate=" + activityDate +
                ", datePosted=" + datePosted +
                '}';
    }
}
