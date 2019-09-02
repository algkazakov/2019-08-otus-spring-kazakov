package ru.otus.spring01.dao;

import ru.otus.spring01.domain.Question;
import ru.otus.spring01.domain.Survay;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class CSVSurvayDaoTest {

    private TestSurvayDao dao = new TestSurvayDao();

    @org.junit.jupiter.api.Test
    void getSurvay() {
         Survay sv = dao.getSurvay();
         assertEquals(5, sv.getQuestions().size());
         for (Question q: sv.getQuestions()) {
             assertEquals(4, q.getAnswers().size());
         }
    }
}