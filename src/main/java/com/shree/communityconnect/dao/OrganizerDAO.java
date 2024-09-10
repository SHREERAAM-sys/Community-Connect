package com.shree.communityconnect.dao;

import com.shree.communityconnect.entity.OrganizerEntity;
import com.shree.communityconnect.entity.UserEntity;

public interface OrganizerDAO {

    void createOrganizer(OrganizerEntity organizerEntity);

    void update(OrganizerEntity organizerEntity);

    OrganizerEntity findOrganizerByUser(UserEntity userEntity);
}
