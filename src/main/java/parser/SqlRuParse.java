package parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;


public class SqlRuParse implements  Parse {

    @Override
    public List<Post> list(String link) {
        List<Post> listPost = new ArrayList<>();
        try {
            Document document = Jsoup.connect(link).get();
            Elements row = document.select(".postslisttopic");
            for (Element el : row) {
                Element href = el.child(0);
                Post post = detail(String.valueOf(href.attr("href")));
                listPost.add(post);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listPost;
    }

    @Override
    public Post detail(String url) {
        Post post = null;
        try {
            Document document = Jsoup.connect(url).get();
            Element title = document.select(".messageHeader").get(1);
            Element description = document.select(".msgBody").get(1);
            Element created = document.select("msgFooter").get(1);
            String cutFooter = created.text().substring(1, 16);
            SqlRuDateTimeParser sqlRuDateTimeParser = new SqlRuDateTimeParser();
            post = new Post(title.text(), url, description.text(), sqlRuDateTimeParser.parse(cutFooter));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
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
