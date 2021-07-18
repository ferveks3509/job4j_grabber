package parser;

import java.time.LocalDateTime;

public interface DateTimeParser {
    LocalDateTime parse(String s);
}
