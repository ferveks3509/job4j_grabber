import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class SqlRuDateTimeParser implements DateTimeParser {

    @Override
    public LocalDateTime parse(String s) {

        LocalDateTime month;
        if (s.contains("вчера")) {
            month = LocalDateTime.now().minusDays(1);
        } else if (s.contains("сегодня")) {
            month = LocalDateTime.now();
        }
        return null;
    }

    public static void main(String[] args) {
        String s = "14 июл 21, 13:32";
        LocalDateTime.parse(s);
        int w1 = Integer.parseInt("11");
        int w2 = Integer.parseInt("11");
        int w3 = Integer.parseInt("11");
        int w4 = Integer.parseInt("11");
        int w5 = Integer.parseInt("11");
        //q = LocalDateTime.of(w1,w2,w3,w4,w5);
        String str = "04-08 12:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);
        System.out.println(dateTime);
    }
}
