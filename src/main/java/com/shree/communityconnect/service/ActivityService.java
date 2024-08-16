package com.shree.communityconnect.service;

import com.shree.communityconnect.bean.ActivityBean;
import com.shree.communityconnect.bean.UserBean;

import java.util.List;

public interface ActivityService {

    void create(UserBean userBean, ActivityBean activityBean);

    List<ActivityBean> fetchAllPostedActivityForOrganizer(UserBean userBean);

    ActivityBean getActivityById(String activityId);

    void updateActivity(ActivityBean activityBean);

    List<UserBean> getParticipantList(String activityId);

    boolean deleteActivity(String activityId);

    List<ActivityBean> getAllActivity();

    void registerParticipantForActivity(UserBean userBean, String activityId);

    List<ActivityBean> getParticipantRegisteredActivity(UserBean userBean);

    void unregisterActivityForParticipant(UserBean userBean, String activityId);
}
