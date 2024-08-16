package com.shree.communityconnect.service;

import com.shree.communityconnect.bean.LoginBean;
import com.shree.communityconnect.bean.UserBean;

public interface UserService {

    void registerUser(UserBean userBean);

    UserBean verifyUser(LoginBean loginBean);

    UserBean findUserByEmail(String email);
}
