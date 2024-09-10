package com.shree.communityconnect.dao;

import com.shree.communityconnect.entity.OrganizerEntity;
import com.shree.communityconnect.entity.ParticipantEntity;
import com.shree.communityconnect.entity.UserEntity;

public interface ParticipantDAO {

    void createParticipant(ParticipantEntity participantEntity);

    ParticipantEntity findParticipantByUser(UserEntity userEntity);

    void update(ParticipantEntity participantEntity);
}
