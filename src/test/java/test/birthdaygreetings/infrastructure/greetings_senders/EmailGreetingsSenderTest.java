package test.birthdaygreetings.infrastructure.greetings_senders;

import birthdaygreetings.core.Employee;
import birthdaygreetings.core.GreetingMessage;
import birthdaygreetings.core.GreetingsSender;
import birthdaygreetings.infrastructure.grettings_senders.EmailGreetingsSender;
import org.junit.Before;
import org.junit.Test;
import test.birthdaygreetings.core.GreetingsSenderTest;
import test.birthdaygreetings.helpers.FakeEmailGreetingsSender;
import test.birthdaygreetings.helpers.GreetingsSenderFactory;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static test.birthdaygreetings.helpers.GreetingsMessageFactory.greetingMessagesFor;
import static test.birthdaygreetings.helpers.OurDateFactory.ourDateFromString;

public class EmailGreetingsSenderTest extends GreetingsSenderTest {
    private static final int SMTP_PORT = 25;
    private String SMTP_HOST = "localhost";
    private static final String FROM = "sender@here.com";
    private List<Message> messagesSent;
    GreetingsSender greetingsSender;

    @Before
    public void setUp() {
        messagesSent = new ArrayList<>();
        greetingsSender = new FakeEmailGreetingsSender(FROM, SMTP_HOST, SMTP_PORT, messagesSent);
    }

    @Override
    public GreetingsSenderFactory getInstanceFactory() {
        return new EmailGreetingsSenderFactory();
    }

    @Test
    public void sends_all_greeting_messages_by_email() throws ParseException, MessagingException, IOException {
        List<GreetingMessage> greetingMessages = greetingMessagesFor(
            new Employee("koko", "loko", ourDateFromString("2018/09/25"), "koko@lala.com")
        );

        greetingsSender.send(greetingMessages);

        assertEquals(1, messagesSent.size());
        Message message = messagesSent.get(0);
        assertEquals("Happy Birthday, dear koko!", message.getContent());
        assertEquals("Happy Birthday!", message.getSubject());
        assertEquals(1, message.getAllRecipients().length);
        assertEquals("koko@lala.com", message.getAllRecipients()[0].toString());
    }

    private class EmailGreetingsSenderFactory implements GreetingsSenderFactory {
        @Override
        public GreetingsSender createGreetingsSenderThatFailsWhenCreatingMessage() {
            return new EmailGreetingsSender(FROM, SMTP_HOST, SMTP_PORT) {
                @Override
                protected Message createMessage(GreetingMessage message) throws MessagingException {
                    throw new MessagingException("");
                }
            };
        }

        @Override
        public GreetingsSender createGreetingsSenderThatFailsWhenSendingMessage() {
            return new EmailGreetingsSender(FROM, SMTP_HOST, SMTP_PORT) {
                @Override
                protected void sendMessage(Message msg) throws MessagingException {
                    throw new MessagingException("");
                }
            };
        }
    }
}
