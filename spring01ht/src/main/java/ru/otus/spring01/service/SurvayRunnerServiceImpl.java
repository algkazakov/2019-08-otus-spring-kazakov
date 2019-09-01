package ru.otus.spring01.service;

import ru.otus.spring01.dao.SurvayDao;
import ru.otus.spring01.domain.Answer;
import ru.otus.spring01.domain.Question;
import ru.otus.spring01.domain.Survay;

import java.util.InputMismatchException;
import java.util.Scanner;

public class SurvayRunnerServiceImpl implements SurvayRunnerService {

    private Survay sv;

    public SurvayRunnerServiceImpl(SurvayDao dao) {
        sv = dao.getSurvay();
    }

    public void run() {
        if (sv != null || sv.getQuestions().isEmpty()) {
            Scanner in = new Scanner(System.in);
            System.out.println("Здравствуйте, введите Ваше ФИО: ");
            String name = in.nextLine();
            int score = 0;
            int qCount = 0;
            for (Question q : sv.getQuestions()) {
                if (q.getAnswers().size() > 1) {
                    qCount++;
                    int tryCount = 3;
                    System.out.println(q.getText().trim());
                    for (Answer a : q.getAnswers()) {
                        System.out.println("  " + a.getText().trim());
                    }
                    int number = getNumber(in, q.getAnswers().size(), tryCount);
                    if (number > 0 && q.getAnswers().get(number - 1).isRigth()) {
                        score++;
                    }
                }
            }
            System.out.println("Уважаемый " + name + ", Вы ответили правильно на " + score + " из " + qCount + " вопросов");
        } else {
            System.out.println("Извините, произошла ошибка при загрузке теста. Проверьте доступность ресурса с вопросами");
        }
    }

    private int getNumber(Scanner in, int answerCount, int tryCount) {
        if (tryCount < 0) {
            return -1;
        }
        System.out.println("Ваш ответ (1-" + answerCount + "): ");
        int number = 1;
        try {
            number = in.nextInt();
        } catch (InputMismatchException e) {
            number = 0;
        }
        if (number < 1 || number > answerCount) {
            System.out.println("Введенный Вами номер ответа не существует" +
                    (tryCount > 0 ? (", повторите ввод. Осталось попыток: " + tryCount) : ""));
            return getNumber(in, answerCount, tryCount - 1);
        }
        return number;
    }
}
