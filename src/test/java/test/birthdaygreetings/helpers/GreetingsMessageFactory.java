package test.birthdaygreetings.helpers;

import birthdaygreetings.core.Employee;
import birthdaygreetings.core.GreetingMessage;

import java.util.Arrays;
import java.util.List;

public class GreetingsMessageFactory {

    public static List<GreetingMessage> greetingMessagesFor(Employee... employees) {
        return GreetingMessage.generateForSome(Arrays.asList(employees));
    }
}
