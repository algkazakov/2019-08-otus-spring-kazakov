package ru.otus.spring01.dao;

import com.opencsv.CSVReader;
import ru.otus.spring01.domain.Answer;
import ru.otus.spring01.domain.Question;
import ru.otus.spring01.domain.Survay;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVSurvayDao implements SurvayDao {

    private String csvFileName;

    @Override
    public Survay getSurvay() {
        List<List<String>> records = getCSVRecords();
        return recordsToSurvay(records);
    }

    private List<List<String>> getCSVRecords() {
        try (InputStream is = CSVSurvayDao.class.getResourceAsStream("/" + csvFileName)) {
            if (is != null) {
                try (CSVReader csvReader = new CSVReader(new InputStreamReader(is));) {
                    String[] values = null;
                    List<List<String>> records = new ArrayList();
                    while ((values = csvReader.readNext()) != null) {
                        records.add(Arrays.asList(values));
                    }
                    return records;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Survay recordsToSurvay(List<List<String>> records) {
        if (records != null && !records.isEmpty()) {
            Survay sv = new Survay();
            for (List<String> record : records) {
                if (!record.isEmpty()) {
                    Question q = new Question(record.get(0));
                    for (int i = 1; i < record.size();  i++) {
                        Answer a = new Answer(record.get(i));
                        q.addAnswer(a);
                    }
                    sv.addQuestion(q);
                }
            }
            return sv;
        }
        return null;
    }

    public void setCsvFileName(String csvFileName) {
        this.csvFileName = csvFileName;
    }
}
