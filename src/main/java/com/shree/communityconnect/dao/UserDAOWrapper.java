package com.shree.communityconnect.dao;

import com.shree.communityconnect.bean.UserBean;
import com.shree.communityconnect.entity.UserEntity;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOWrapper implements UserDAO {


    private static final Logger logger = LoggerFactory.getLogger(UserDAOWrapper.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void createUser(UserEntity user) {
        entityManager.persist(user);
        logger.atInfo().setMessage("User with email "+ user.getEmail()+ " created successfully").log();
    }

    @Override
    public UserEntity findUserByEmail(String email) {

        try {
            TypedQuery<UserEntity> query = entityManager.createQuery("FROM UserEntity WHERE email=:email", UserEntity.class);
            query.setParameter("email", email);
            logger.atInfo().setMessage("User found with email "+ email+ " successfully").log();
            return query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public void updateUser(UserEntity user) {
        entityManager.merge(user);
        logger.atInfo().setMessage("User with email "+ user.getEmail()+ " updated successfully").log();
    }
}
