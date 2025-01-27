package com.shree.communityconnect.util;


import org.mindrot.jbcrypt.BCrypt;

public class CommonUtil {


    public static String hashPassword(String password) {
        String salt = BCrypt.gensalt();

        return BCrypt.hashpw(password, salt);
    }

    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
}
