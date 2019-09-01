package ru.otus.spring01.domain;

public class Answer {

    private String text;
    private boolean isRigth;

    public Answer (String text) {
        this.text = text.endsWith("*") ? text.substring(0, text.length() - 1) : text;
        this.isRigth = text.endsWith("*");
    }

    public boolean isRigth() {
        return isRigth;
    }

    public String getText() {
        return text;
    }
}
