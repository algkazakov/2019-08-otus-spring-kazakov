package ru.otus.spring01.dao;

import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

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
        setCSVLocalizedResourceName();
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
                    LOGGER.error(e.getMessage(), e);
                }
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    private void setCSVLocalizedResourceName()  {
        if (locale != null) {
            String csvLocaleFileName = "i18n/" + csvFileName.substring(0, csvFileName.indexOf('.')) + "_" + locale + "." + csvFileName.substring(csvFileName.indexOf('.') + 1);
            try (InputStream is = CSVSurvayDao.class.getResourceAsStream("/" + csvLocaleFileName)) {
                if (is != null) {
                    csvFileName = csvLocaleFileName;
                } else {
                    LOGGER.info("Resource " + csvFileName + " with locale " + locale + " not found");
                }
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
                csvFileName = "i18n/" + csvFileName.substring(0, csvFileName.indexOf('.')) + "_ru.csv";
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
                        String text = record.get(i).endsWith("*") ?
                                record.get(i).substring(0, record.get(i).length() - 1) : record.get(i);
                        boolean isRight = record.get(i).endsWith("*");
                        Answer a = new Answer(text, isRight);
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
