import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class SqlRuParse {

    public static void main(String[] args) throws Exception {
        Document document = Jsoup.connect("https://www.sql.ru/forum/job-offers").get();
        Elements row = document.select(".postslisttopic");
        for (Element el : row) {
            Element href = el.child(0);
            Element time = el.parent().child(5);
            System.out.println(href.attr("href"));
            System.out.println(href.text());
            System.out.println(time.text());
        }
    }
}
