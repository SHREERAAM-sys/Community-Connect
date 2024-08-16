package com.shree.communityconnect.config;

import com.shree.communityconnect.bean.UserBean;
import com.shree.communityconnect.constants.Role;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
//            // User is not logged in or session has expired
//            request.setAttribute("sessionExpired", true);
//            request.getRequestDispatcher("/").forward(request, response);
            response.sendRedirect("/");
            return false;
        }

        // Check for user roles
        UserBean userBean = (UserBean) session.getAttribute("user");
        Role role = userBean.getRole();

        String uri = request.getRequestURI();
        if (role == Role.ROLE_ORGANIZER && uri.startsWith("/organizer") ||
                role == Role.ROLE_PARTICIPANT && uri.startsWith("/participant") ||
                uri.startsWith("/home")) {
            return true;
        }


        // If none of the above conditions are met, redirect to an access denied page or login
        response.sendRedirect("/access-denied");
        return false;
    }
}
