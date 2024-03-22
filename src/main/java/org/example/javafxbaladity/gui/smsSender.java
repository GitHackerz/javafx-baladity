package org.example.javafxbaladity.gui;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class smsSender {
    // Your Twilio Account SID and Auth Token
    public static final String ACCOUNT_SID = "AC2f98e0756ce5b2c151f24ab185718cad";
    public static final String AUTH_TOKEN = "17b6e655b8a76db17bb401e3f4471851";

    // Your Twilio phone number
    public static final String TWILIO_PHONE_NUMBER = "+1 954 241 3163";

    public static void sendSMS(String toPhoneNumber, String messageBody) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                        new PhoneNumber(toPhoneNumber),  // To
                        new PhoneNumber(TWILIO_PHONE_NUMBER),  // From
                        messageBody)
                .create();

        System.out.println("SMS sent successfully! SID: " + message.getSid());
    }

    public static void main(String[] args) {
        sendSMS("+21692510826","salut cava");
    }
}
