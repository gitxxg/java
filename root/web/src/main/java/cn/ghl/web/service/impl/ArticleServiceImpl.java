package cn.ghl.web.service.impl;

import cn.ghl.web.entity.Article;
import cn.ghl.web.entity.Author;
import cn.ghl.web.repository.ArticleRepository;
import cn.ghl.web.service.ArticleService;
import java.util.List;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 6/11/2018
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Article save(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public void delete(Article article) {
        articleRepository.delete(article);
    }

    @Override
    public Article findOne(Long id) {
        //return articleRepository.findOne(id);
        return null;
    }

    @Override
    public Iterable<Article> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public Page<Article> findByAuthor(Author author, PageRequest pageRequest) {
        return articleRepository.findByAuthor(author, pageRequest);
    }

    @Override
    public Page<Article> findByTitle(String title, PageRequest pageRequest) {
        return articleRepository.findByTitle(title, pageRequest);
    }

    @Override
    public List<Article> findByTitle(String title) {
        return articleRepository.findByTitle(title);
    }

    @Override
    public Iterable<Article> search(String queryString) {
        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(queryString);
        return articleRepository.search(builder);
    }

}
