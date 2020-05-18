package at.ac.tuwien.sepm.groupphase.backend.service.impl;

import at.ac.tuwien.sepm.groupphase.backend.entity.ResetPassword;
import at.ac.tuwien.sepm.groupphase.backend.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.lang.invoke.MethodHandles;

@Service
public class CustomEmailService implements EmailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private static final String host = "smtp.gmail.com";
    private static final String from = "service.ticketline@gmail.com";

    @Override
    public void sendResetPasswordEmail(ResetPassword resetPassword) {

        String to = resetPassword.getEmail();
        Properties properties = System.getProperties();

        // Setup mail server
        properties.setProperty("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the default Session object.
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(from, "ticketline2020");

            }

        });


        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("Reset Password request");

            // Now set the actual message
            message.setText("Greetings! You can use the following link to reset your password: localhost:4200/api/v1/users/change-password/" + resetPassword.getResetPasswordCode());

            // Send message
            Transport.send(message);
            LOGGER.info("Sent reset password email successfully....");
        } catch (MessagingException mex) {
            LOGGER.error("Reset password email could not be sent....");
            mex.printStackTrace();
        }
    }

}
