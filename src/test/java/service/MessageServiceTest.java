package service;

import com.gbax.TopicsTestTask.service.MessageService;
import com.gbax.TopicsTestTask.system.exception.CantParseStringDateException;
import utils.AbstractTest;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Тест метода getDateFromString из MessageService
 * Created by Баянов on 07.02.2015.
 */
public class MessageServiceTest extends AbstractTest {

    private MessageService messageService;
    private List<String> formats = Arrays.asList(
            "dd.MM.yyyy",
            "dd:MM:yy",
            "dd-MM-yyyy hh-mm",
            "dd-MM hh-mm",
            "MM-dd-yyyy hh:mm"
    );
    private Date originalDate;

    @Before
    public void init() {
        messageService = new MessageService();
        originalDate = new Date();
    }

    /**
     * Тест получения дат по заданным шаблонам
     * @throws ParseException
     */
    @Test
    public void testFormat() throws ParseException {
        for (String format : formats) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
            String originalDateString = simpleDateFormat.format(originalDate);
            Date originalDateParsed = simpleDateFormat.parse(originalDateString);
            Date date = null;
            try {
                date = messageService.getDateFromString(originalDateString);
            } catch (CantParseStringDateException e) {
                assert false;
            }

            assert date != null;
            assert originalDateParsed.equals(date);
        }
    }

    /**
     * Тест попытки получения даты из неверной строки
     * @throws ParseException
     */
    @Test
    public void testWrongFormat() throws ParseException {
        try {
            Date date = messageService.getDateFromString("");
        } catch (CantParseStringDateException e) {
            assertEquals(
                    "Невозможно преобразовать дату, строка не должна быть пустой", e.getError());
        }
        try {
            Date date = messageService.getDateFromString("00+00+01");
        } catch (CantParseStringDateException e) {
            assertEquals("Невозможно преобразовать дату 00+00+01. Неизвестный формат даты.", e.getError());
        }

    }
}
