package com.shree.communityconnect.dao;

import com.shree.communityconnect.entity.OrganizerEntity;
import com.shree.communityconnect.entity.UserEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class OrganizerDAOWrapper implements OrganizerDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void createOrganizer(OrganizerEntity organizerEntity) {
        entityManager.persist(organizerEntity);
    }

    @Override
    @Transactional
    public void update(OrganizerEntity organizerEntity) {
        entityManager.merge(organizerEntity);
    }

    @Override
    public OrganizerEntity findOrganizerByUser(UserEntity userEntity) {
        return entityManager.createQuery("SELECT o FROM OrganizerEntity o WHERE o.userEntity = :user", OrganizerEntity.class)
                .setParameter("user", userEntity)
                .getSingleResult();
    }
}
