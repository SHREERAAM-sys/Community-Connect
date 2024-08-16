package com.shree.communityconnect.dao;

import com.shree.communityconnect.entity.OrganizerEntity;
import com.shree.communityconnect.entity.ParticipantEntity;
import com.shree.communityconnect.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class ParticipantDAOWrapper implements ParticipantDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void createParticipant(ParticipantEntity participantEntity) {

        entityManager.persist(participantEntity);
    }

    @Override
    public ParticipantEntity findParticipantByUser(UserEntity userEntity) {
        return entityManager.createQuery("SELECT p FROM ParticipantEntity  p WHERE p.userEntity = :user", ParticipantEntity.class)
                .setParameter("user", userEntity)
                .getSingleResult();
    }

    @Override
    @Transactional
    public void update(ParticipantEntity participantEntity) {
        entityManager.merge(participantEntity);
    }
}
