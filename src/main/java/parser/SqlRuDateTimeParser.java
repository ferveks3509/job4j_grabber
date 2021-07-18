package parser;

import java.time.*;
import java.time.format.DateTimeFormatter;

public class SqlRuDateTimeParser implements DateTimeParser {

    @Override
    public LocalDateTime parse(String s) {
        DateTimeFormatter formatterFull = DateTimeFormatter.ofPattern("dd MMMM yy, HH:mm");
        LocalDateTime localDate = LocalDateTime.parse(s, formatterFull);
        if (s.contains("вчера") || (s.contains("сегодня"))) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd, HH:mm");
            LocalDateTime shortDate = LocalDateTime.parse(setShortDate(s), formatter);
            return shortDate;
        }
        return localDate;
    }
    private LocalDateTime setShortDate(String s) {
        String time = s.split(",")[1];
        int hours = Integer.parseInt(time.substring(1, 2));
        int min = Integer.parseInt(time.split(":")[1]);
        LocalDateTime rsl = null;
        if (s.contains("вчера")) {
            LocalDate yesterday = LocalDate.now().minusDays(1);
            LocalTime localTime = LocalTime.of(hours,min);
            rsl.of(yesterday, localTime);
        } else if(s.contains("сегодня")) {
            LocalDate toDay = LocalDate.now();
            LocalTime localTime = LocalTime.of(hours, min);
            rsl.of(toDay, localTime);
        }
        return rsl;
    }

    public static void main(String[] args) {
        SqlRuDateTimeParser ob = new SqlRuDateTimeParser();
        String date1 = "11 июля 21, 20:20";
        String date2 = "вчера, 11:39";
        System.out.println(ob.parse(date1));
        System.out.println(ob.parse(date2));

        String time = date2.split(",")[1];
        int hh = Integer.parseInt(time.substring(1, 2));
        int mm = Integer.parseInt(time.split(":")[1]);
        LocalDate localDate = LocalDate.now().minusDays(1);
        LocalTime localTime = LocalTime.of(hh,mm);
        System.out.println(LocalDateTime.of(localDate, localTime));
    }
}
