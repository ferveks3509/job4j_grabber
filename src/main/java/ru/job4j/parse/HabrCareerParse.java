package ru.job4j.parse;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.job4j.model.Post;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HabrCareerParse implements Parse {
    private static final Logger LOGGER = LoggerFactory.getLogger(HabrCareerParse.class.getName());

    private static final String SOURCE_LINK = "https://career.habr.com";
    private static final String PAGE_LINK = String.format("%s/vacancies/java_developer?page=", SOURCE_LINK);

    private final DateTimeParser dateTimeParser;

    public HabrCareerParse(DateTimeParser dateTimeParser) {
        this.dateTimeParser = dateTimeParser;
    }

    private static String retrieveDescription(String link) {
        StringBuilder sb = new StringBuilder();
        try {
            Connection connection = Jsoup.connect(link);
            Document document = connection.get();
            Elements rows = document.select(".vacancy-description__text");
            rows.forEach(row -> {
                sb.append(row.text());
                sb.append(System.lineSeparator());
            });
        } catch (IOException e) {
            LOGGER.error("Parse description", e);
        }
        return sb.toString();
    }

    @Override
    public List<Post> list(String link) {
        List<Post> posts = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            try {
                Connection connection = Jsoup.connect(String.format("%s%s", link, i));
                Document document = connection.get();
                Elements rows = document.select(".vacancy-card__inner");
                rows.forEach(row -> {
                    Element tittleElement = row.select(".vacancy-card__title").first();
                    Element dateElement = row.select(".vacancy-card__date").first();
                    Element linkElement = tittleElement.child(0);
                    String vacancyName = linkElement.text();
                    Element date = dateElement.child(0);
                    String strDate = date.attr("datetime");
                    strDate = strDate.substring(0, 16);
                    LocalDateTime localDateTime = dateTimeParser.parse(strDate);
                    String linkHref = String.format("%s%s", SOURCE_LINK, linkElement.attr("href"));
                    String desc = retrieveDescription(linkHref);
                    Post post = new Post();
                    post.setTitle(vacancyName);
                    post.setLink(linkHref);
                    post.setDescription(desc);
                    post.setCreated(localDateTime);
                    posts.add(post);
                });
            } catch (IOException e) {
                LOGGER.error("To list parse", e);
            }
        }
        return posts;
    }

    public static void main(String[] args) throws IOException {
    }
}
        /*
        for (int i = 1; i < 6; i++) {
            System.out.println("page: " + i);
            Connection connection = Jsoup.connect(PAGE_LINK + i);
            Document document = connection.get();
            Elements rows = document.select(".vacancy-card__inner");
            rows.forEach(row -> {
                Element titleElement = row.select(".vacancy-card__title").first();
                Element dateElement = row.select(".vacancy-card__date").first();

                Element linkElement = titleElement.child(0);
                Element el = dateElement.child(0);
                String vacancyName = titleElement.text();

                String date = el.attr("datetime");
                date = date.substring(0, 16);
                String link = String.format("%s%s", SOURCE_LINK, linkElement.attr("href"));

                String desc = retrieveDescription(link);
                HabrCareerDateTimeParser parser = new HabrCareerDateTimeParser();
                LocalDateTime localDateTime = parser.parse(date);

                System.out.printf("%s %s %s %s%n", vacancyName, link, localDateTime, desc);
            });
        }
    }
}
         */
