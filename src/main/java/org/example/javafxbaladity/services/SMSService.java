package org.example.javafxbaladity.Services;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SMSService {
    public static final String ACCOUNT_SID = "AC6bc68f374a84320b4c95dd34ff1e1a86";
    public static final String AUTH_TOKEN = "d886b82754ad2ac6654bcddab4702d56";
    public static final String FROM_PHONE_NUMBER = "+19286155139";

    public static void sendSMS(String to, String messageBody) {
        try {
            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
            Message message = Message.creator(
                            new PhoneNumber(to),
                            new PhoneNumber(FROM_PHONE_NUMBER),
                            messageBody)
                    .create();
            System.out.println("SMS envoyé avec l'ID : " + message.getSid());
        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi du SMS : " + e.getMessage());
        }
    }
}

