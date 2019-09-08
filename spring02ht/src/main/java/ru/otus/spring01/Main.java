package ru.otus.spring01;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring01.service.SurvayRunnerService;

@Configuration
@ComponentScan
public class Main {

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Main.class)) {
            SurvayRunnerService service = context.getBean(SurvayRunnerService.class);

            service.run();
        }
    }
}
