package parser;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class SqlRuDateTimeParser implements DateTimeParser {

    @Override
    public LocalDateTime parse(String s) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yy, HH:mm");
        if (s.contains("сегодня")) {
            return shortDate(s);
        } else if (s.contains("вчера")) {
            return shortDate(s).minusDays(1);
        }
        return LocalDateTime.parse(s, formatter);
    }
    private LocalDateTime shortDate(String s) {
        int hh = Integer.parseInt(s.split(" ")[1].substring(0,2));
        int mm = Integer.parseInt(s.split(":")[1]);
        LocalTime localTime = LocalTime.of(hh, mm);
        LocalDate localDate = LocalDate.now();
        return LocalDateTime.of(localDate, localTime);
    }

    public static void main(String[] args) {
        SqlRuDateTimeParser ob = new SqlRuDateTimeParser();
        String date1 = "11 июля 21, 20:20";
        String date2 = "вчера, 12:39";
        System.out.println(ob.parse(date1));
        System.out.println(ob.parse(date2));
    }
}
