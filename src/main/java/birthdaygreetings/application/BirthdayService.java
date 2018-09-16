package birthdaygreetings.application;

import birthdaygreetings.core.*;

import java.util.List;

public class BirthdayService {

    private EmployeesRepository employeesRepository;
    private GreetingsSender greetingsSender;

    public BirthdayService(EmployeesRepository employeesRepository, GreetingsSender greetingsSender) {
        this.employeesRepository = employeesRepository;
        this.greetingsSender = greetingsSender;
    }

    public void sendGreetings(OurDate date) {
        send(greetingMessagesFor(employeesHavingBirthday(date)));
    }

    private List<GreetingMessage> greetingMessagesFor(List<Employee> employees) {
        return GreetingMessage.generateForSome(employees);
    }

    private List<Employee> employeesHavingBirthday(OurDate today) {
        return employeesRepository.whoseBirthdayIs(today);
    }

    private void send(List<GreetingMessage> messages) {
        greetingsSender.send(messages);
    }
}
