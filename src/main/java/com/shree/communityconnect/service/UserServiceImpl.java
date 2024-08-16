package com.shree.communityconnect.service;

import com.shree.communityconnect.bean.LoginBean;
import com.shree.communityconnect.bean.UserBean;
import com.shree.communityconnect.constants.Role;
import com.shree.communityconnect.dao.OrganizerDAO;
import com.shree.communityconnect.dao.ParticipantDAO;
import com.shree.communityconnect.dao.UserDAO;
import com.shree.communityconnect.entity.OrganizerEntity;
import com.shree.communityconnect.entity.ParticipantEntity;
import com.shree.communityconnect.entity.UserEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private OrganizerDAO organizerDAO;

    @Autowired
    private ParticipantDAO participantDAO;


    @Override
    public void registerUser(UserBean userBean) {

        String encodedPassword = passwordEncoder.encode(userBean.getPassword());
        userBean.setPassword(encodedPassword);
        userBean.setAccountCreated(new Timestamp(System.currentTimeMillis()));

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userBean, userEntity);
        userDAO.createUser(userEntity);

        //check if it is a organizer or participant and save accordingly

        if(userBean.getRole().equals(Role.ROLE_PARTICIPANT)) {
            ParticipantEntity participantEntity = new ParticipantEntity();
            participantEntity.setUserEntity(userEntity);
            participantDAO.createParticipant(participantEntity);
        }
        else{
            OrganizerEntity organizerEntity = new OrganizerEntity();
            organizerEntity.setUserEntity(userEntity);
            organizerDAO.createOrganizer(organizerEntity);
        }
    }

    @Override
    public UserBean verifyUser(LoginBean loginBean) {
        UserEntity userEntity = userDAO.findUserByEmail(loginBean.getEmail());

        if(userEntity != null && passwordEncoder.matches(loginBean.getPassword(), userEntity.getPassword())){
            UserBean bean = new UserBean();
            BeanUtils.copyProperties(userEntity, bean);
            return bean;
        }

        return null;
    }

    @Override
    public UserBean findUserByEmail(String email) {
        UserEntity userEntity = userDAO.findUserByEmail(email);
        if(userEntity != null) {
            UserBean userBean = new UserBean();
            BeanUtils.copyProperties(userEntity, userBean);
            return userBean;
        }

        return null;
    }
}
