package ru.otus.spring01.domain;

import java.util.ArrayList;
import java.util.List;

public class Question {

    private String text;
    private List<Answer> answers;

    public Question(String text) {
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
