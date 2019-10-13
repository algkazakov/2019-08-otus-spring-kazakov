package ru.otus.spring.domain;

import java.util.ArrayList;
import java.util.List;

public class Survay {

    private final List<Question> questions = new ArrayList();

    public List<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question q) {
        questions.add(q);
    }
}
