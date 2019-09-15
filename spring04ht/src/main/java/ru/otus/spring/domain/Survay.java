package ru.otus.spring.domain;

import java.util.ArrayList;
import java.util.List;

public class Survay {

    private List<Question> questions = new ArrayList();

    public List<Question> getQuestions() {
        return questions;
    }

    public Question getQuestion(int number) {
        return questions.get(number);
    }

    public void addQuestion(Question q) {
        questions.add(q);
    }
}
