package ru.otus.spring01.dao;

import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.otus.spring01.domain.Answer;
import ru.otus.spring01.domain.Question;
import ru.otus.spring01.domain.Survay;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@PropertySource("classpath:survay.properties")
public class CSVSurvayDao implements SurvayDao {

    @Value("${csv.file}")
    private String csvFileName;

    @Value("${app.locale}")
    private String locale;

    @Override
    public Survay getSurvay() {
        List<List<String>> records = getCSVRecords();
        return recordsToSurvay(records);
    }

    private List<List<String>> getCSVRecords() {
        try {
            setCSVLocalizedResourceName();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    private void setCSVLocalizedResourceName() throws IOException {
        if (locale != null) {
            String csvLocaleFileName = "i18n/" + csvFileName.substring(0, csvFileName.indexOf('.')) + "_" + locale + "." + csvFileName.substring(csvFileName.indexOf('.') + 1);
            try (InputStream is = CSVSurvayDao.class.getResourceAsStream("/" + csvLocaleFileName)) {
                if (is != null) {
                    csvFileName = csvLocaleFileName;
                }
            }
        }
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
