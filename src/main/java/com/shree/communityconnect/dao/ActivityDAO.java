package com.shree.communityconnect.dao;

import com.shree.communityconnect.entity.ActivityEntity;

import java.util.List;

public interface ActivityDAO {

    void create(ActivityEntity activityEntity);

    void update(ActivityEntity activityEntity);

    void delete(ActivityEntity activityEntity);

    ActivityEntity findById(String id);

    List<ActivityEntity> fetchAllActivity();
}
