package com.shree.communityconnect.service;

import com.shree.communityconnect.bean.ActivityBean;
import com.shree.communityconnect.bean.UserBean;
import com.shree.communityconnect.entity.ActivityEntity;
import com.shree.communityconnect.entity.ParticipantEntity;
import com.shree.communityconnect.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class EmailServiceImpl implements EmailService {

    private final ExecutorService executorService = Executors.newFixedThreadPool(5); // Adjust the thread pool size as needed

    @Autowired
    private JavaMailSender mailSender;


    @Async
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        mailSender.send(message);
    }

    @Override
    @Async
    public void sendWelcomeEmail(UserBean userBean) {
        // Set the recipient's email
        String to = userBean.getEmail();

        // Define the subject of the email
        String subject = "Welcome to Community Connect!";

        // Create a welcome message including first and last name
        String text = "Dear " + userBean.getFirstName() + " " + userBean.getLastName() + ",\n\n" +
                "Welcome to Community Connect! We are thrilled to have you as a part of our community. " +
                "Our platform is designed to bring people together and create meaningful connections through various activities and events.\n\n" +
                "If you have any questions or need assistance, feel free to reach out to our support team at any time. " +
                "We're here to help you make the most of your experience.\n\n" +
                "Thank you for joining us, and we look forward to seeing you get involved in the community!\n\n" +
                "Best regards,\n" +
                "Team Community Connect";

        // Send the email using the sendEmail method
        sendEmail(to, subject, text);
    }

    @Override
    @Async
    public void sendActivityRegistrationEmail(ActivityEntity activityEntity, UserEntity participant) {
        String subject = "Activity Registration Success - " + activityEntity.getName();

        String text = String.format(
                "Dear %s %s,\n\n" +
                        "You have successfully registered for the following activity:\n\n" +
                        "Activity Name: %s\n" +
                        "Description: %s\n" +
                        "Date: %s\n\n" +
                        "We look forward to your participation!\n\n" +
                        "Best Regards,\n" +
                        "The Community Connect Team",
                participant.getFirstName(),
                participant.getLastName(),
                activityEntity.getName(),
                activityEntity.getDescription(),
                activityEntity.getActivityDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))
        );

        sendEmail(participant.getEmail(), subject, text);
    }

    @Override
    @Async
    public void sendActivityUnregistrationEmail(ActivityEntity activityEntity, UserEntity participant) {
        String subject = "Activity Unregistration Confirmation - " + activityEntity.getName();

        String text = String.format(
                "Dear %s %s,\n\n" +
                        "You have successfully unregistered from the following activity:\n\n" +
                        "Activity Name: %s\n" +
                        "Description: %s\n" +
                        "Date: %s\n\n" +
                        "We're sorry to see you go! If you change your mind, you're always welcome to register again.\n\n" +
                        "Best Regards,\n" +
                        "The Community Connect Team",
                participant.getFirstName(),
                participant.getLastName(),
                activityEntity.getName(),
                activityEntity.getDescription(),
                activityEntity.getActivityDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"))
        );

        sendEmail(participant.getEmail(), subject, text);
    }


    @Override
    @Async
    public void sendBulkActivityDeletionEmail(ActivityBean activityBean, List<UserBean> participantBeanList) {
        String subject = "Activity Cancellation Notification";
        String textTemplate = "Dear %s %s,\n\n" +
                "We regret to inform you that the activity '%s' you were registered for has been cancelled.\n" +
                "We apologize for any inconvenience this may have caused.\n\n" +
                "Best regards,\n" +
                "The Community Connect Team";


        for (UserBean user : participantBeanList) {

            String recipientEmail = user.getEmail();
            String recipientFirstName = user.getFirstName();
            String recipientLastName = user.getLastName();
            String activityName = activityBean.getName();

            String text = String.format(textTemplate, recipientFirstName, recipientLastName, activityName);

            sendEmail(recipientEmail, subject, text);
        }
    }


}
