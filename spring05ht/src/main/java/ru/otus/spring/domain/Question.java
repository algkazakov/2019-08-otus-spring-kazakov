package ru.otus.spring.domain;

import java.util.ArrayList;
import java.util.List;

public class Question {

    private final String text;
    private List<Answer> answers;

    public Question(final String text) {
        this.text = text;
        answers = new ArrayList();
    }

    public List<Answer> getAnswers() {
        return  answers;
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    public String getText() {
        return text;
    }
}
