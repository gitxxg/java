package cn.ghl.web.controller;

import cn.ghl.web.entity.Article;
import cn.ghl.web.entity.Author;
import cn.ghl.web.entity.Tutorial;
import cn.ghl.web.service.ArticleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 6/11/2018
 */
@RestController
public class ElasticsearchController {

    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchController.class);

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ArticleService articleService;

    @RequestMapping(value = "/web/elasticsearch/context", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String elasticsearch() {
        for (long i = 0; i < 10; i++) {
            Author author = new Author(null, "xmp", null);
            Tutorial tutorial = new Tutorial(i, "test" + String.valueOf(i));
            Article article = new Article(i, "title", "abs", "content", new Date(), i, author, tutorial);
            Article art = articleService.save(article);
            logger.info("save: {}", art.toString());
        }

        Iterable<Article> it = articleService.findAll();
        it.forEach(article -> logger.info("query: {}", article.toString()));
        return null;
    }

}
