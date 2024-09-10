package com.shree.communityconnect.bean;

import com.shree.communityconnect.constants.ActivityCategory;
import com.shree.communityconnect.constants.ActivityType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ActivityBean {

    private String id;

    @NotBlank(message = "Activity Name should not be blank")
    private String name;

    @NotBlank(message = "Description should not be blank")
    private String description;

    @NotNull(message = "please select a activity category")
    private ActivityCategory category;

    @NotNull(message = "please select a activity type")
    private ActivityType type;

    @NotNull(message = "Please provide the total capacity for the activity")
    private Integer totalCapacity;
    private String organizerId; // Use ID or other identifier for the organizer
    private List<String> participantUserIdList = new ArrayList<>();

    @NotNull(message = "Please select an activity date")
    private LocalDateTime activityDate;

    private LocalDateTime datePosted;

    public ActivityBean() {
    }

    public ActivityBean(String id, String name, String description, ActivityCategory category, ActivityType type, Integer totalCapacity, String organizerId, List<String> participantIds, LocalDateTime activityDate, LocalDateTime datePosted) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.type = type;
        this.totalCapacity = totalCapacity;
        this.organizerId = organizerId;
        this.participantUserIdList = participantIds;
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

    public String getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(String organizerId) {
        this.organizerId = organizerId;
    }

    public List<String> getParticipantUserIdList() {
        return participantUserIdList;
    }

    public void setParticipantUserIdList(List<String> participantUserIdList) {
        this.participantUserIdList = participantUserIdList;
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
        return "ActivityBean{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category=" + category +
                ", type=" + type +
                ", totalCapacity=" + totalCapacity +
                ", organizerId='" + organizerId + '\'' +
                ", participantList=" + participantUserIdList +
                ", activityDate=" + activityDate +
                ", datePosted=" + datePosted +
                '}';
    }
}
