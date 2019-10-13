package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.AppProperties;
import ru.otus.spring.dao.SurvayDao;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.Survay;

@Service
public class SurvayRunnerServiceImpl implements SurvayRunnerService {

    private final SurvayDao dao;
    private final ConsoleService consoleService;
    private final AppProperties properties;

    private String name;
    private int score;
    private int qCount;

    public SurvayRunnerServiceImpl(final SurvayDao dao, final ConsoleService consoleService, final AppProperties properties) {
        this.dao = dao;
        this.properties = properties;
        this.consoleService = consoleService;
    }

    @Override
    public void getGreetingName() {
        name = consoleService.getName();
    }

    @Override
    public void runSurvay() {
        score = 0;
        qCount = 0;
        Survay sv = dao.getSurvay();
        if (sv != null && sv.getQuestions() != null && !sv.getQuestions().isEmpty()) {
            for (Question q : sv.getQuestions()) {
                if (q.getAnswers().size() > 1) {
                    qCount++;
                    int tryCount = 3;
                    consoleService.printQuestion(q);
                    int number = getNumber(q.getAnswers().size(), tryCount);
                    if (number > 0 && q.getAnswers().get(number - 1).isRigth()) {
                        score++;
                    }
                }
            }
        } else {
            consoleService.printError(ConsoleService.SURVAY_CSVLOAD_ERROR);
        }
    }

    @Override
    public void printResult() {
        int pCount = properties.getPassNumber() > qCount ? qCount : properties.getPassNumber();
        consoleService.printResult(name, score, qCount, pCount);
    }

    private int getNumber(int answerCount, int tryCount) {
        if (tryCount < 0) {
            return -1;
        }
        consoleService.printAnswer(answerCount);
        int number = consoleService.getAnswer();
        if (number < 1 || number > answerCount) {
            consoleService.printAnswerAgain(tryCount);
            return getNumber(answerCount, tryCount - 1);
        }
        return number;
    }
}