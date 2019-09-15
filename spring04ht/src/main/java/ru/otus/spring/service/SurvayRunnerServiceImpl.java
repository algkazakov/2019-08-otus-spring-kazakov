package ru.otus.spring.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.AppProperties;
import ru.otus.spring.dao.SurvayDao;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.Survay;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

@Service
public class SurvayRunnerServiceImpl implements SurvayRunnerService {

    private final SurvayDao dao;
    private final MessageSource ms;
    private final Locale locale;
    private final AppProperties properties;

    public SurvayRunnerServiceImpl(final SurvayDao dao, final MessageSource ms, final AppProperties properties) {
        this.dao = dao;
        this.ms = ms;
        this.properties = properties;
        this.locale = properties.getLocale() == null ? new Locale("ru") : new Locale(properties.getLocale());
    }

    @Override
    public void run() {
        Survay sv = dao.getSurvay();
        if (sv != null && sv.getQuestions() != null && !sv.getQuestions().isEmpty()) {
            Scanner in = new Scanner(System.in);
            System.out.println(ms.getMessage("survay.fio", null, locale));
            String name = in.nextLine();
            int score = 0;
            int qCount = 0;
            for (Question q : sv.getQuestions()) {
                if (q.getAnswers().size() > 1) {
                    qCount++;
                    int tryCount = 3;
                    System.out.println(q.getText().trim());
                    for (Answer a : q.getAnswers()) {
                        System.out.println("  " + a.getText().trim());
                    }
                    int number = getNumber(in, q.getAnswers().size(), tryCount);
                    if (number > 0 && q.getAnswers().get(number - 1).isRigth()) {
                        score++;
                    }
                }
            }
            System.out.println(ms.getMessage("survay.result", new Object [] {name, score, qCount}, locale));
            int pCount = properties.getPassNumber() > qCount ? qCount : properties.getPassNumber();
            if (score >= pCount) {
                System.out.println(ms.getMessage("survay.result.pass", null, locale));
            } else {
                System.out.println(ms.getMessage("survay.result.fail", null, locale));
            }
        } else {
            System.out.println(ms.getMessage("survay.csvLoad.error", null, locale));
        }
    }

    private int getNumber(Scanner in, int answerCount, int tryCount) {
        if (tryCount < 0) {
            return -1;
        }
        System.out.println(ms.getMessage("survay.answer", new Object [] {answerCount}, locale));
        int number = 1;
        try {
            number = in.nextInt();
        } catch (InputMismatchException e) {
            number = 0;
        }
        if (number < 1 || number > answerCount) {
            System.out.println(ms.getMessage("survay.answer.notExists", null, locale) +
                    (tryCount > 0 ? ms.getMessage("survay.answer.tryCount", new Object [] {tryCount}, locale) : ""));
            return getNumber(in, answerCount, tryCount - 1);
        }
        return number;
    }
}