package ru.otus.spring01;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring01.service.SurvayRunnerService;

public class Main {

    public static void main(String[] args) {
        try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml")) {
            SurvayRunnerService service = context.getBean(SurvayRunnerService.class);
            service.run();
        }
    }
}
