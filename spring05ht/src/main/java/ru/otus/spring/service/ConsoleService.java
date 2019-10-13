package ru.otus.spring.service;

import ru.otus.spring.domain.Question;

public interface ConsoleService {

    public static final String SURVAY_CSVLOAD_ERROR = "survay.csvLoad.error";
    public static final String DEFAULT_LOCALE = "ru";
    public static final String SURVAY_FIO = "survay.fio";
    public static final String SURVAY_RESULT = "survay.result";
    public static final String SURVAY_RESULT_PASS = "survay.result.pass";
    public static final String SURVAY_RESULT_FAIL = "survay.result.fail";
    public static final String SURVAY_ANSWER = "survay.answer";
    public static final String SURVAY_ANSWER_NOT_EXISTS = "survay.answer.notExists";
    public static final String SURVAY_ANSWER_TRYCOUNT = "survay.answer.tryCount";

    String getName();
    void printQuestion(Question q);
    public void printAnswer(int answerCount);
    int getAnswer();
    public void printAnswerAgain(int tryCount);
    void printResult(String name, int score, int qCount, int pCount);
    void printError(String errorName);
}
