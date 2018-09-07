package ru.javawebinar.topjava.util;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;

import static ru.javawebinar.topjava.util.DateTimeUtil.DATE_FORMATTER;
import static ru.javawebinar.topjava.util.DateTimeUtil.TIME_FORMATTER;


public class LocalDateTimeFormatter {
    public final static class LocalDateFormatter implements Formatter<LocalDate> {

        @Override
        public LocalDate parse(String text, Locale locale) throws ParseException {
            return DateTimeUtil.parseLocalDate(text);
        }

        @Override
        public String print(LocalDate object, Locale locale) {
            return object != null ? object.format(DATE_FORMATTER) : null;
        }
    }

    public final static class LocalTimeFormatter implements Formatter<LocalTime> {
        public LocalTime parse(String text, Locale locale) throws ParseException {
            return DateTimeUtil.parseLocalTime(text);
        }

        @Override
        public String print(LocalTime object, Locale locale) {
            return object != null ? object.format(TIME_FORMATTER) : null;
        }
    }
}
