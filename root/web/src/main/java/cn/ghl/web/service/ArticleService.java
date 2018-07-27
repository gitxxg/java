package cn.ghl.web.service;

import cn.ghl.web.entity.Article;
import cn.ghl.web.entity.Author;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 6/11/2018
 */
public interface ArticleService {

    Article save(Article article);

    void delete(Article article);

    Article findOne(Long id);

    Iterable<Article> findAll();

    Page<Article> findByAuthor(Author author, PageRequest pageRequest);

    Page<Article> findByTitle(String title, PageRequest pageRequest);

    List<Article> findByTitle(String title);

    Iterable<Article> search(String queryString);
}

