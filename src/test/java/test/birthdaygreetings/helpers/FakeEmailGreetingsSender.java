package test.birthdaygreetings.helpers;

import birthdaygreetings.infrastructure.grettings_senders.EmailGreetingsSender;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.util.List;

public class FakeEmailGreetingsSender extends EmailGreetingsSender {
    private final List<Message> messagesSent;

    public FakeEmailGreetingsSender(String from, String smtpHost, int smtpPort, List<Message> messagesSent) {
        super(from, smtpHost, smtpPort);
        this.messagesSent = messagesSent;
    }

    @Override
    protected void sendMessage(Message msg) throws MessagingException {
        messagesSent.add(msg);
    }
}
