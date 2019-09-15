package ru.otus.spring01.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.spring01.domain.Question;
import ru.otus.spring01.domain.Survay;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CSVSurvayDaoTest {

    private CSVSurvayDao dao = new CSVSurvayDao();
    @BeforeEach
    void init() {
        dao.setCsvFileName("survay_test.csv");
    }

    @Test
    void getSurvay() {
        Survay sv = dao.getSurvay();
        assertEquals(5, sv.getQuestions().size());
        for (Question q: sv.getQuestions()) {
            assertEquals(4, q.getAnswers().size());
        }
    }
}