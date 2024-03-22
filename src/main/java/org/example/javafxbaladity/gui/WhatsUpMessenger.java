package org.example.javafxbaladity.gui;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public class WhatsUpMessenger {
    public static final String ACCOUNT_SID = "ACd966825c8cb6dadd5b5cb750aa0b3cc3";
    public static final String AUTH_TOKEN = "b174c51de8829b806d3da598796dbba4";

    public static void SendWhatsUpMessage(String MsgBody,String RecveiverNumber)
    {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("whatsapp:+216"+RecveiverNumber),
                        new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                MsgBody)
                .create();
        System.out.println(message.getSid());
    }

}
