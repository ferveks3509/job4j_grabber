package parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class SqlRuParse {
    public Post info(String url) throws Exception {
        Document document = Jsoup.connect(url).get();
        Element title = document.select(".messageHeader").get(1);
        Element description = document.select(".msgBody").get(1);
        Element created = document.select("msgFooter").get(1);
        String cutFooter = created.text().substring(1, 16);
        SqlRuDateTimeParser sqlRuDateTimeParser = new SqlRuDateTimeParser();
        return new Post(title.text(), url, description.text(), sqlRuDateTimeParser.parse(cutFooter));
    }

    public static void main(String[] args) throws Exception {
        /*
        for (int i = 1; i <= 5; i++) {
            Document document = Jsoup.connect("https://www.sql.ru/forum/job-offers/" + i).get();
            Elements row = document.select(".postslisttopic");
            for (Element el : row) {
                Element href = el.child(0);
                Element time = el.parent().child(5);
                System.out.println(href.attr("href"));
                System.out.println(href.text());
                System.out.println(time.text());
            }
        }
         */
    }
}
