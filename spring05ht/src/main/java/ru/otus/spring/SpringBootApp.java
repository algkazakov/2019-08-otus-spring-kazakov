package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
//import ru.otus.spring.service.SurvayRunnerService;

@SpringBootApplication
public class SpringBootApp {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(SpringBootApp.class, args);
		//SurvayRunnerService runner = context.getBean(SurvayRunnerService.class);
		//runner.run();
	}

}
