package com.shree.communityconnect.controller;

import com.shree.communityconnect.bean.LoginBean;
import com.shree.communityconnect.bean.UserBean;
import com.shree.communityconnect.service.EmailService;
import com.shree.communityconnect.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;


    @GetMapping("/")
    public String loginForm(HttpServletRequest request, Model model) {
//        if (request.getAttribute("sessionExpired") != null) {
//            model.addAttribute("sessionError", "Your session has expired. Please log in again.");
//        }
        model.addAttribute("loginBean", new LoginBean());
        return "login-form";
    }

    @PostMapping("/user/login")
    public String loginUser(@ModelAttribute("loginBean") LoginBean loginBean, Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        if(session.getAttribute("user") != null) {
            return "home";
        }

        UserBean userBean = userService.verifyUser(loginBean);
        if(userBean == null) {
            redirectAttributes.addFlashAttribute("error", "Invalid User name or Password");
            return "redirect:/";
        }
        session.setAttribute("user", userBean);

        //return to home
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String showHomepage() {

        return "home";
    }

    @GetMapping("/register")
    public String registrationForm(Model model){
        if (!model.containsAttribute("userBean")) {
            model.addAttribute("userBean", new UserBean());
        }
        return "registration-form";
    }

    @PostMapping("/user/register")
    public String registerUser(@Valid @ModelAttribute("userBean") UserBean userBean, BindingResult bindingResult, RedirectAttributes redirectAttributes) {


        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userBean", bindingResult);
            redirectAttributes.addFlashAttribute("userBean", userBean);
            return "redirect:/register";
        }

        String email = userBean.getEmail();
        UserBean existingUserBean = userService.findUserByEmail(email);

        if(existingUserBean!= null) {
            redirectAttributes.addFlashAttribute("emailError", "Email already exists. Please try a different email.");
            redirectAttributes.addFlashAttribute("userBean", userBean);
            return "redirect:/register";
        }

        userService.registerUser(userBean);
        emailService.sendWelcomeEmail(userBean);
        redirectAttributes.addFlashAttribute("successMessage", "User registered successfully!");

        return "redirect:/";

    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }


}
