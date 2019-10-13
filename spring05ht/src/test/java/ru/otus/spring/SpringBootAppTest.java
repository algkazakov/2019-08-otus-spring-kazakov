package ru.otus.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.dao.SurvayDao;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.Survay;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SpringBootAppTest {
	@Autowired
	private AppProperties properties;

	@Autowired
	private SurvayDao dao;

	@Test
	public void contextLoads() {
		Survay sv = dao.getSurvay();
		assertEquals(5, sv.getQuestions().size());
		for (Question q: sv.getQuestions()) {
			assertEquals(4, q.getAnswers().size());
		}
	}

}