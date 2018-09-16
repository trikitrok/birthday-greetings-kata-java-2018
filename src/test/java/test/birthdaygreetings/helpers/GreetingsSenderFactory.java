package test.birthdaygreetings.helpers;

import birthdaygreetings.core.GreetingsSender;

public interface GreetingsSenderFactory {
    GreetingsSender createGreetingsSenderThatFailsWhenCreatingMessage();

    GreetingsSender createGreetingsSenderThatFailsWhenSendingMessage();
}
