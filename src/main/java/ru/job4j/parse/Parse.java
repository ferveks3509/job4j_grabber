package ru.job4j.parse;

import ru.job4j.model.Post;

import java.util.List;

public interface Parse {
    List<Post> list(String link);
}
