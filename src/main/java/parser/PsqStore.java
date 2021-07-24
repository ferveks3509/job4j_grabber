package parser;

import org.jsoup.select.Evaluator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class PsqStore implements Store, AutoCloseable {
    private Connection connection;

    public PsqStore(Properties cfg) {
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
            connection = DriverManager.getConnection(
                    cfg.getProperty("url"),
                    cfg.getProperty("username"),
                    cfg.getProperty("password")
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(Post post) {
        try(PreparedStatement statement = connection.prepareStatement(
                "insert into posts(name, description, link, created) values (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, post.getTitle());
            statement.setString(2, post.getDescription());
            statement.setString(3, post.getLink());
            statement.setTimestamp(4, Timestamp.valueOf(post.getCreated()));
            statement.execute();
            try (ResultSet genKey = statement.getGeneratedKeys()) {
                if (genKey.next()) {
                    post.setId(genKey.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Post> getAll() {
        List<Post> rsl = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("select * from posts")) {
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    rsl.add(
                            new Post(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getString("link"),
                            rs.getTimestamp("created").toLocalDateTime()
                            )
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rsl;
    }

    @Override
    public Post findById(int id) {
        Post post = null;
        try(PreparedStatement statement = connection.prepareStatement("select * from posts where id = ?")) {
            statement.setInt(1, Integer.parseInt("id"));
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    post = new Post(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("description"),
                            rs.getString("link"),
                            rs.getTimestamp("created").toLocalDateTime()
                    );
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }
    @Override
    public void close() throws Exception {
        if (connection != null) {
            connection.close();
        }
    }

    public static void main(String[] args) throws Exception {
        SqlRuDateTimeParser sqlRuDateTimeParser = new SqlRuDateTimeParser();
        SqlRuParse sqlRuParse = new SqlRuParse(sqlRuDateTimeParser);
        Post post = sqlRuParse.detail("https://www.sql.ru/forum/1337530/ishhem-budushhego-java-razrabotchika-udalennaya-rabota-do-2500");
        System.out.println(post.getTitle());
        System.out.println(post.getCreated());
        System.out.println(post.getLink());
        System.out.println(post.getCreated());
        Properties properties = new Properties();
        properties.load(new FileReader("./src/main/resources/app.properties"));
        PsqStore psqStore = new PsqStore(properties);
        psqStore.save(post);
    }
}
