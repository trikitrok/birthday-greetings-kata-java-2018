package birthdaygreetings.infrastructure.grettings_senders;

import birthdaygreetings.core.exceptions.CannotCreateMessageException;
import birthdaygreetings.core.exceptions.CannotSendMessageException;
import birthdaygreetings.core.GreetingMessage;
import birthdaygreetings.core.GreetingsSender;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;

public class EmailGreetingsSender implements GreetingsSender {
    private final String from;
    private final Session session;

    public EmailGreetingsSender(String from, String smtpHost, int smtpPort) {
        this.from = from;
        this.session = createMailSession(smtpHost, smtpPort);
    }

    public void send(List<GreetingMessage> greetingMessages) {
        for (GreetingMessage greetingMessage : greetingMessages) {
            Message msg = tryToCreateMessage(greetingMessage);
            tryToSendMessage(msg);
        }
    }

    private void tryToSendMessage(Message msg) {
        try {
            sendMessage(msg);
        } catch (MessagingException e) {
            throw new CannotSendMessageException("cannot send message",e);
        }
    }

    private Message tryToCreateMessage(GreetingMessage greetingMessage) {
        Message msg;
        try {
            msg = createMessage(greetingMessage);
        } catch (MessagingException e) {
            throw new CannotCreateMessageException("cannot create message",e);
        }
        return msg;
    }

    // made protected for testing :-(
    protected Message createMessage(GreetingMessage message) throws MessagingException {
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(this.from));
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(message.to()));
        msg.setSubject(message.subject());
        msg.setText(message.text());
        return msg;
    }

    private Session createMailSession(String smtpHost, int smtpPort) {
        java.util.Properties props = new java.util.Properties();
        props.put("mail.smtp.host", smtpHost);
        props.put("mail.smtp.port", "" + smtpPort);
        return Session.getDefaultInstance(props, null);
    }

    // made protected for testing :-(
    protected void sendMessage(Message msg) throws MessagingException {
        Transport.send(msg);
    }
}
