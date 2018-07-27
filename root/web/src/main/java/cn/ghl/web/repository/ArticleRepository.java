package cn.ghl.web.repository;


import cn.ghl.web.entity.Article;
import cn.ghl.web.entity.Author;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 6/11/2018
 */
public interface ArticleRepository extends ElasticsearchRepository<Article, Long> {

    Page<Article> findByAuthor(Author author, Pageable pageable);

    List<Article> findByTitle(String title);

    Page<Article> findByTitle(String title, Pageable pageable);
}

