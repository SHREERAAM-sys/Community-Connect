package com.shree.communityconnect.dao;

import com.shree.communityconnect.bean.UserBean;
import com.shree.communityconnect.entity.UserEntity;

public interface UserDAO {

    void createUser(UserEntity user);
    UserEntity findUserByEmail(String email);
    void updateUser(UserEntity user);
}
