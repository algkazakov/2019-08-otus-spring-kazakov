package ru.otus.spring.service;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.AppProperties;
import ru.otus.spring.dao.SurvayDao;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

@Service
public class ConsoleServiceImpl implements ConsoleService {

    private final MessageSource ms;
    private final Locale locale;
    private final Scanner in = new Scanner(System.in);


    public ConsoleServiceImpl(final SurvayDao dao, final MessageSource ms, final AppProperties properties) {
        this.ms = ms;
        this.locale = properties.getLocale() == null ? new Locale(ConsoleService.DEFAULT_LOCALE) : new Locale(properties.getLocale());
    }

    @Override
    public String getName() {
        System.out.println(ms.getMessage(ConsoleService.SURVAY_FIO, null, locale));
        return in.nextLine();
    }

    @Override
    public void printQuestion(Question q) {
        System.out.println(q.getText().trim());
        for (Answer a : q.getAnswers()) {
            System.out.println("  " + a.getText().trim());
        }
    }

    @Override
    public void printResult(String name, int score, int qCount, int pCount) {
        System.out.println(ms.getMessage(ConsoleService.SURVAY_RESULT, new Object [] {name, score, qCount}, locale));
        if (score >= pCount) {
            System.out.println(ms.getMessage(ConsoleService.SURVAY_RESULT_PASS, null, locale));
        } else {
            System.out.println(ms.getMessage(ConsoleService.SURVAY_RESULT_FAIL, null, locale));
        }
    }

    @Override
    public void printAnswer(int answerCount) {
        System.out.println(ms.getMessage(ConsoleService.SURVAY_ANSWER, new Object [] {answerCount}, locale));
    }

    @Override
    public int getAnswer() {
        int number;
        try {
            number = in.nextInt();
        } catch (InputMismatchException e) {
            number = 0;
        }
        return number;
    }

    @Override
    public void printAnswerAgain(int tryCount) {
        System.out.println(ms.getMessage(ConsoleService.SURVAY_ANSWER_NOT_EXISTS, null, locale) +
                (tryCount > 0 ? ms.getMessage(ConsoleService.SURVAY_ANSWER_TRYCOUNT, new Object[]{tryCount}, locale) : ""));
    }

    public void printError(String errorName) {
        System.out.println(ms.getMessage(errorName, null, locale));
    }
}