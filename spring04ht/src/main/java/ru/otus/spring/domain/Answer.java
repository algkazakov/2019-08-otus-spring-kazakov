package ru.otus.spring.domain;

public class Answer {

    private final String text;
    private final boolean isRigth;

    public Answer (final String text, final boolean isRight) {
        this.text = text;
        this.isRigth = isRight;
    }

    public boolean isRigth() {
        return isRigth;
    }

    public String getText() {
        return text;
    }
}
