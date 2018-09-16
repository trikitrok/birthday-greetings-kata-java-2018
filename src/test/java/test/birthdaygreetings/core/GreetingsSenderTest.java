package test.birthdaygreetings.core;

import birthdaygreetings.core.*;
import birthdaygreetings.core.exceptions.CannotCreateMessageException;
import birthdaygreetings.core.exceptions.CannotSendMessageException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import test.birthdaygreetings.helpers.GreetingsSenderFactory;

import java.text.ParseException;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static test.birthdaygreetings.helpers.GreetingsMessageFactory.greetingMessagesFor;
import static test.birthdaygreetings.helpers.OurDateFactory.ourDateFromString;

abstract public class GreetingsSenderTest {

    abstract protected GreetingsSenderFactory getInstanceFactory();

    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Test
    public void throws_exception_when_message_cannot_be_created() throws Exception {
        expected.expect(CannotCreateMessageException.class);
        expected.expectMessage(containsString("cannot create message"));

        GreetingsSenderFactory senderFactory = getInstanceFactory();
        GreetingsSender sender = senderFactory.createGreetingsSenderThatFailsWhenCreatingMessage();
        sender.send(someGreetingMessages());
    }

    @Test
    public void throws_exception_when_message_cannot_be_sent() throws Exception {
        expected.expect(CannotSendMessageException.class);
        expected.expectMessage(containsString("cannot send message"));

        GreetingsSenderFactory senderFactory = getInstanceFactory();
        GreetingsSender sender = senderFactory.createGreetingsSenderThatFailsWhenSendingMessage();
        sender.send(someGreetingMessages());
    }

    private List<GreetingMessage> someGreetingMessages() throws ParseException {
        return greetingMessagesFor(
            new Employee("koko", "loko", ourDateFromString("2018/09/25"), "koko@lala.com")
        );
    }
}
