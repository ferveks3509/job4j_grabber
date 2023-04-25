package ru.job4j.parse;

import java.time.LocalDateTime;

public interface DateTimeParser {
    LocalDateTime parse(String parse);
}
