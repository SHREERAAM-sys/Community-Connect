package com.shree.communityconnect.controller;

import com.shree.communityconnect.bean.ActivityBean;
import com.shree.communityconnect.bean.UserBean;
import com.shree.communityconnect.service.ActivityService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/participant")
public class ParticipantActivityController {


    @Autowired
    private ActivityService activityService;


    @GetMapping("/activities")
    public String showAllActivity(Model model) {

        List<ActivityBean> activityBeanList = activityService.getAllActivity();

        model.addAttribute("activityList", activityBeanList);

        return "show-activities";
    }

    @GetMapping("/activities/register/{activityId}")
    public String registerForActivity(@PathVariable("activityId") String activityId, HttpSession session, RedirectAttributes redirectAttributes) {
        // Retrieve the current user from the session
        UserBean userBean = (UserBean) session.getAttribute("user");

        try {
            // Register the user for the activity
            activityService.registerParticipantForActivity(userBean, activityId);

            // Set a success message
            redirectAttributes.addFlashAttribute("message", "Successfully registered for the activity.");
        } catch (Exception e) {
            // Set an error message if registration fails
            redirectAttributes.addFlashAttribute("error", "Registration failed: " + e.getMessage());
        }

        // Redirect back to the activities list
        return "redirect:/participant/activities";
    }


    @GetMapping("/registered-activities")
    public String showParticipantRegisteredActivityPage(Model model, HttpSession session) {

        UserBean userBean = (UserBean) session.getAttribute("user");

        List<ActivityBean> activityBeanList = activityService.getParticipantRegisteredActivity(userBean);
        model.addAttribute("activityBeanList", activityBeanList);

        return "registered-activities";

    }

    @PostMapping("/activities/unregister/{activityId}")
    public String unregisterFromActivity(@PathVariable("activityId") String activityId, HttpSession session, RedirectAttributes redirectAttributes) {
        // Get the current user from the session
        UserBean userBean = (UserBean) session.getAttribute("user");

        try {
            // Perform the unregister operation
            activityService.unregisterActivityForParticipant(userBean, activityId);

            redirectAttributes.addFlashAttribute("message", "You have successfully unregistered from the activity.");
        }
        catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Unable unregistered from the activity.");

        }

        // Redirect to the registered activities page
        return "redirect:/participant/registered-activities";
    }

}
