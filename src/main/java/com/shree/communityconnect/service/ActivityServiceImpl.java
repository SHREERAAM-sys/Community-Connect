package com.shree.communityconnect.service;

import com.shree.communityconnect.bean.ActivityBean;
import com.shree.communityconnect.bean.UserBean;
import com.shree.communityconnect.dao.ActivityDAO;
import com.shree.communityconnect.dao.OrganizerDAO;
import com.shree.communityconnect.dao.ParticipantDAO;
import com.shree.communityconnect.dao.UserDAO;
import com.shree.communityconnect.entity.ActivityEntity;
import com.shree.communityconnect.entity.OrganizerEntity;
import com.shree.communityconnect.entity.ParticipantEntity;
import com.shree.communityconnect.entity.UserEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {


    @Autowired
    private UserDAO userDAO;

    @Autowired
    private OrganizerDAO organizerDAO;

    @Autowired
    private ParticipantDAO participantDAO;

    @Autowired
    private ActivityDAO activityDAO;

    @Autowired
    private EmailService emailService;


    @Override
    public void create(UserBean userBean, ActivityBean activityBean) {

        UserEntity userEntity = userDAO.findUserByEmail(userBean.getEmail());
        OrganizerEntity organizerEntity = organizerDAO.findOrganizerByUser(userEntity);

        activityBean.setDatePosted(LocalDateTime.now());

        ActivityEntity activityEntity = new ActivityEntity();
        BeanUtils.copyProperties(activityBean, activityEntity); //first time so no participant list available

        activityEntity.setOrganizerEntity(organizerEntity);

        //create activity
        activityDAO.create(activityEntity);

    }

    @Override
    public List<ActivityBean> fetchAllPostedActivityForOrganizer(UserBean userBean) {

        UserEntity userEntity = userDAO.findUserByEmail(userBean.getEmail());
        OrganizerEntity organizerEntity = organizerDAO.findOrganizerByUser(userEntity);

        List<ActivityEntity> activityEntityList = organizerEntity.getActivityEntityList();
        activityEntityList.sort(Comparator.comparing(ActivityEntity::getDatePosted).reversed());

        List<ActivityBean> activityBeanList = new ArrayList<>(activityEntityList.size());

        for(ActivityEntity activityEntity: activityEntityList) {

            ActivityBean activityBean = this.convertToBean(activityEntity);
            activityBeanList.add(activityBean);
        }

        return activityBeanList;
    }

    @Override
    public ActivityBean getActivityById(String activityId) {

        ActivityEntity activityEntity = activityDAO.findById(activityId);
        ActivityBean activityBean = this.convertToBean(activityEntity);

        return activityBean;
    }

    @Override
    public void updateActivity(ActivityBean activityBean) {

        ActivityEntity activityEntity = activityDAO.findById(activityBean.getId());
        if (activityEntity != null) {
            // Update fields from ActivityBean to ActivityEntity
            activityEntity.setName(activityBean.getName());
            activityEntity.setDescription(activityBean.getDescription());
            activityEntity.setCategory(activityBean.getCategory());
            activityEntity.setType(activityBean.getType());
            activityEntity.setTotalCapacity(activityBean.getTotalCapacity());
            activityEntity.setActivityDate(activityBean.getActivityDate());


            activityDAO.update(activityEntity);
        }
    }

    @Override
    public List<UserBean> getParticipantList(String activityId) {

        ActivityEntity activityEntity = activityDAO.findById(activityId);

        List<ParticipantEntity> participantEntityList = activityEntity.getParticipantEntityList();
        List<UserBean> participantBeanList = new ArrayList<>(participantEntityList.size());

        for(ParticipantEntity participantEntity: participantEntityList) {

            UserEntity userEntity = participantEntity.getUserEntity();
            UserBean userBean = new UserBean();
            BeanUtils.copyProperties(userEntity, userBean);
            participantBeanList.add(userBean);
        }
        return participantBeanList;
    }

    @Override
    public boolean deleteActivity(String activityId) {

        ActivityEntity activityEntity = activityDAO.findById(activityId);
        if(activityEntity!= null) {
            ActivityBean activityBean = convertToBean(activityEntity);
            List<UserBean> participantBeanList = this.getParticipantUserBeansList(activityEntity.getParticipantEntityList());
            activityDAO.delete(activityEntity);
            emailService.sendBulkActivityDeletionEmail(activityBean, participantBeanList);
            return true;
        }
        return false;
    }

    @Override
    public List<ActivityBean> getAllActivity() {

        List<ActivityEntity> activityEntityList = activityDAO.fetchAllActivity();
        List<ActivityBean> activityBeanList = this.getActivityBeanList(activityEntityList);

        return activityBeanList;
    }

    @Override
    public void registerParticipantForActivity(UserBean userBean, String activityId) {

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userBean, userEntity);

        ParticipantEntity participantEntity = participantDAO.findParticipantByUser(userEntity);
        ActivityEntity activityEntity = activityDAO.findById(activityId);

        activityEntity.getParticipantEntityList().add(participantEntity);
        activityDAO.update(activityEntity);

        emailService.sendActivityRegistrationEmail(activityEntity, participantEntity.getUserEntity());
    }

    @Override
    public List<ActivityBean> getParticipantRegisteredActivity(UserBean userBean) {

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userBean, userEntity);

        ParticipantEntity participantEntity = participantDAO.findParticipantByUser(userEntity);

        List<ActivityEntity> activityEntityList = participantEntity.getRegisteredActivity();
        List<ActivityBean> activityBeanList = this.getActivityBeanList(activityEntityList);

        return activityBeanList;


    }

    @Override
    public void unregisterActivityForParticipant(UserBean userBean, String activityId) {

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userBean, userEntity);

        // Find the participant entity by user ID
        ParticipantEntity participantEntity = participantDAO.findParticipantByUser(userEntity);

        if (participantEntity != null) {
            // Find the activity entity by ID
            ActivityEntity activityEntity = activityDAO.findById(activityId);

            if (activityEntity != null) {
                // Remove the activity from the participant's registered activities
                participantEntity.getRegisteredActivity().remove(activityEntity);
                participantDAO.update(participantEntity);

                // Remove the participant from the activity's participants
                activityEntity.getParticipantEntityList().remove(participantEntity);
                activityDAO.update(activityEntity);

                emailService.sendActivityUnregistrationEmail(activityEntity, participantEntity.getUserEntity());
            }
        }
    }

    private List<ActivityBean> getActivityBeanList(List<ActivityEntity> activityEntityList) {

        List<ActivityBean> activityBeanList = new ArrayList<>(activityEntityList.size());

        for(ActivityEntity activityEntity : activityEntityList) {

            ActivityBean activityBean = this.convertToBean(activityEntity);
            activityBeanList.add(activityBean);
        }

        return activityBeanList;
    }

    private List<UserBean> getParticipantUserBeansList(List<ParticipantEntity> participantEntityList) {

        List<UserBean> userBeansList = new ArrayList<>(participantEntityList.size());

        for(ParticipantEntity participantEntity: participantEntityList) {
            UserBean userBean = new UserBean();
            BeanUtils.copyProperties(participantEntity.getUserEntity(), userBean);

            userBeansList.add(userBean);
        }

        return userBeansList;
    }


    private ActivityBean convertToBean(ActivityEntity entity) {
        ActivityBean activityBean = new ActivityBean();
        BeanUtils.copyProperties(entity, activityBean);

        // Handle organizerEntity to organizerId conversion
        if (entity.getOrganizerEntity() != null) {
            activityBean.setOrganizerId(entity.getOrganizerEntity().getId());
        }

        // Handle participantEntityList to participantList conversion
        if (entity.getParticipantEntityList() != null) {
            List<String> participants = new ArrayList<>();
            for (ParticipantEntity participantEntity : entity.getParticipantEntityList()) {
                participants.add(participantEntity.getUserEntity().getId());
            }
            activityBean.setParticipantUserIdList(participants);
        }

        return activityBean;
    }

}
