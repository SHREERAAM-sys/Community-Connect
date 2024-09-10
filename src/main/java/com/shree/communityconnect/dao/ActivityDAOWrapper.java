package com.shree.communityconnect.dao;

import com.shree.communityconnect.entity.ActivityEntity;
import com.shree.communityconnect.entity.ParticipantEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;


@Repository
public class ActivityDAOWrapper implements ActivityDAO {


    @PersistenceContext
    private EntityManager entityManager;
    @Override
    @Transactional
    public void create(ActivityEntity activityEntity) {
        entityManager.persist(activityEntity);
    }

    @Override
    @Transactional
    public void update(ActivityEntity activityEntity) {
        entityManager.merge(activityEntity);
    }

    @Override
    @Transactional
    public void delete(ActivityEntity activityEntity) {
        entityManager.remove(activityEntity);
    }

    @Override
    public ActivityEntity findById(String activityId) {
        return entityManager.createQuery("SELECT a FROM ActivityEntity  a WHERE a.id = :id", ActivityEntity.class)
                .setParameter("id", activityId)
                .getSingleResult();
    }

    @Override
    public List<ActivityEntity> fetchAllActivity() {
        String hql = "SELECT a FROM ActivityEntity a";
        return entityManager.createQuery(hql, ActivityEntity.class).getResultList();
    }
}
