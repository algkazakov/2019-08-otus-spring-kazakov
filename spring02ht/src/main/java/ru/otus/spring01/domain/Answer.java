package ru.otus.spring01.domain;

public class Answer {

    private String text;
    private boolean isRigth;

    public Answer (String text, boolean isRight) {
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
