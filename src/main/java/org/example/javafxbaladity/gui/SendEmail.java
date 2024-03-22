package org.example.javafxbaladity.gui;


import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class SendEmail {

    public static boolean send(String to, String subject, String text) {
        String from = "ferchichimedemir12@gmail.com";
        String password = "ydby mdze fehu zdpt"; // Replace with your real password
        String host = "smtp.gmail.com";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
            System.out.println("Sent message successfully....");
            return true; // Email sent successfully
        } catch (MessagingException mex) {
            mex.printStackTrace();
            return false; // Failed to send email
        }
    }
}

