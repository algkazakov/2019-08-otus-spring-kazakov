package ru.otus.spring.shell;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.spring.service.SurvayRunnerService;

@ShellComponent
public class ShellCommands {

    private final SurvayRunnerService survay;

    private boolean isGreet = false;
    private boolean isRun = false;

    public ShellCommands(SurvayRunnerService survay) {
        this.survay = survay;
    }

    @ShellMethod(value = "Greeting", key = {"greeting", "grt", "g"})
    @ShellMethodAvailability(value = "isNotGreet")
    public void greeting() {
        survay.getGreetingName();
        isGreet = true;
    }

    @ShellMethod(value = "Run Survay", key = {"run", "r"})
    @ShellMethodAvailability(value = "isGreet")
    public void runSurvay() {
        survay.runSurvay();
        isRun = true;
    }

    @ShellMethod(value = "Print result", key = {"print", "p"})
    @ShellMethodAvailability(value = "isRun")
    public void printResult() {
        survay.printResult();
    }

    private Availability isNotGreet() {
        return isGreet? Availability.unavailable("You already enter your name"): Availability.available();
    }

    private Availability isGreet() {
        return !isGreet? Availability.unavailable("Enter your name please"): Availability.available();
    }

    private Availability isRun() {
        return !(isGreet && isRun) ? Availability.unavailable("Enter your name and run survay"): Availability.available();
    }
}
