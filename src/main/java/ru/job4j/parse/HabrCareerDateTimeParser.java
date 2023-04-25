package ru.job4j.parse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HabrCareerDateTimeParser implements  DateTimeParser {
    @Override
    public LocalDateTime parse(String parse) {
        return LocalDateTime.from(DateTimeFormatter.ISO_LOCAL_DATE_TIME.parse(parse));
    }
}
