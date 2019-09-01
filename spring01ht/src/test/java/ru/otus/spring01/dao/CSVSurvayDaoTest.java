package ru.otus.spring01.dao;

import ru.otus.spring01.domain.Survay;

import static org.junit.jupiter.api.Assertions.*;

class CSVSurvayDaoTest {
    CSVSurvayDao dao = new CSVSurvayDao();

    @org.junit.jupiter.api.Test
    void getSurvay() {
         Survay sv = dao.getSurvay();
         assertEquals(5, sv.getQuestions().size());
    }
}