package com.shree.communityconnect.service;

import com.shree.communityconnect.bean.ActivityBean;
import com.shree.communityconnect.bean.UserBean;
import com.shree.communityconnect.entity.ActivityEntity;
import com.shree.communityconnect.entity.ParticipantEntity;
import com.shree.communityconnect.entity.UserEntity;

import java.util.List;

public interface EmailService {

    void sendEmail(String to, String subject, String text);

    void sendWelcomeEmail(UserBean userBean);

    void sendActivityRegistrationEmail(ActivityEntity activityEntity, UserEntity userEntity);

    void sendActivityUnregistrationEmail(ActivityEntity activityEntity, UserEntity userEntity);

    void sendBulkActivityDeletionEmail(ActivityBean activityBean, List<UserBean> participantBeanList);
}
