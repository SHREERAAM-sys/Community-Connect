package com.shree.communityconnect.controller;


import com.shree.communityconnect.bean.ActivityBean;
import com.shree.communityconnect.bean.UserBean;
import com.shree.communityconnect.entity.ParticipantEntity;
import com.shree.communityconnect.service.ActivityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/organizer")
public class OrganizerActivityController {


    @Autowired
    private ActivityService activityService;

    @GetMapping("/activity/create")
    public String showCreateActivityPage(Model model) {

        if (!model.containsAttribute("activityBean")) {
            model.addAttribute("activityBean", new ActivityBean());
        }

        return "create-activity-form";
    }

    @PostMapping("/create-new-activity")
    public String createActivity(HttpServletRequest request, @Valid @ModelAttribute("activityBean") ActivityBean activityBean,
                                    BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        // Check if the activity date is in the past
        if (activityBean.getActivityDate() != null && activityBean.getActivityDate().isBefore(LocalDateTime.now())) {
            bindingResult.rejectValue("activityDate", "error.activityBean", "Activity date must be in the future or current date.");
        }

        //redirect to create activity page if the result has errors
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.activityBean", bindingResult);
            redirectAttributes.addFlashAttribute("activityBean", activityBean);
            return "redirect:/organizer/activity/create";
        }

        HttpSession session = request.getSession(false);
        UserBean userBean = (UserBean) session.getAttribute("user");

        activityService.create(userBean, activityBean);

        redirectAttributes.addFlashAttribute("message", activityBean.getName() + " activity created successfully!");

        return "redirect:/home";
    }

    @GetMapping("/activities")
    public String showAllPostedActivity(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession(false);
        UserBean userBean = (UserBean) session.getAttribute("user");

        List<ActivityBean> activityBeanList = activityService.fetchAllPostedActivityForOrganizer(userBean);
        model.addAttribute("activityList", activityBeanList);

        return "posted-activities";
    }

    @GetMapping("/activities/edit/{activityId}")
    public String showEditActivityForm(HttpServletRequest request, @PathVariable("activityId") String activityId, Model model) {
        if (!model.containsAttribute("activityBean")) {
            ActivityBean activityBean = activityService.getActivityById(activityId);
            model.addAttribute("activityBean", activityBean);
        }
        return "edit-activity";
    }

    @PostMapping("/activities/update")
    public String updateActivity(HttpServletRequest request, @Valid @ModelAttribute("activityBean") ActivityBean activityBean,
                                 BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        // Check if the activity date is in the past
        if (activityBean.getActivityDate() != null && activityBean.getActivityDate().isBefore(LocalDateTime.now())) {
            bindingResult.rejectValue("activityDate", "error.activityBean", "Activity date must be in the future or current date.");
        }

        //redirect to create activity page if the result has errors
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.activityBean", bindingResult);
            redirectAttributes.addFlashAttribute("activityBean", activityBean);
            return "redirect:/organizer/activities/edit/"+activityBean.getId();
        }


        activityService.updateActivity(activityBean);

        redirectAttributes.addFlashAttribute("message", activityBean.getName() + " activity updated successfully!");

        return "redirect:/organizer/activities";
    }

    @GetMapping("/activities/viewParticipants/{activityId}")
    public String viewParticipants(@PathVariable("activityId") String activityId, Model model) {


        List<UserBean> participantBeansList = activityService.getParticipantList(activityId);

        model.addAttribute("participants", participantBeansList);

        if(participantBeansList.isEmpty()) {
            model.addAttribute("message", "No registration found");
        }


        return "view-participants"; // Return the name of the Thymeleaf template
    }

    @GetMapping("activities/delete/{activityId}")
    public String deleteJobListing(@PathVariable("activityId") String activityId, RedirectAttributes redirectAttributes) {

        boolean isDeleted = activityService.deleteActivity(activityId);

        if(isDeleted) {
            redirectAttributes.addFlashAttribute("message",  " activity is deleted successfully");
        }
        else {
            redirectAttributes.addFlashAttribute("error", "unable to delete activity ");
        }

        return "redirect:/organizer/activities";
    }
}
