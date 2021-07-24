package parser;

import java.time.*;
import java.util.HashMap;
import java.util.Map;

public class SqlRuDateTimeParser implements DateTimeParser {
    private static final Map<String, Integer> month = new HashMap<>();
    {
        month.put("янв", 1);
        month.put("фев", 2);
        month.put("мар", 3);
        month.put("апр", 4);
        month.put("май", 5);
        month.put("июн", 6);
        month.put("июл", 7);
        month.put("авг", 8);
        month.put("сен", 9);
        month.put("окт", 10);
        month.put("ноя", 11);
        month.put("дек", 12);
    }

    @Override
    public LocalDateTime parse(String s) {
        if (s.contains("сегодня")) {
            return shortDate(s);
        } else if (s.contains("вчера")) {
            return shortDate(s).minusDays(1);
        }
        String[] arrData = s.split(" ");
        int years = Integer.parseInt(arrData[2].substring(0,1));
        int month = this.month.get(arrData[1]);
        int day = Integer.parseInt(arrData[0]);
        int hours = Integer.parseInt(arrData[3].substring(0, 1));
        int min = Integer.parseInt(arrData[3].split(":")[1]);

        return LocalDateTime.of(years, month, day, hours, min);
    }
    private LocalDateTime shortDate(String s) {
        int hh = Integer.parseInt(s.split(" ")[1].substring(0,2));
        int mm = Integer.parseInt(s.split(":")[1]);
        LocalTime localTime = LocalTime.of(hh, mm);
        LocalDate localDate = LocalDate.now();
        return LocalDateTime.of(localDate, localTime);
    }
}
