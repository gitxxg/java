package cn.ghl.web.entity;

import java.io.Serializable;
import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @Author: Hailong Gong
 * @Description:
 * @Date: Created in 6/11/2018
 */

@Document(indexName = "xmp", type = "news")
public class Article implements Serializable {

    @Id
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 摘要
     */
    private String abstracts;

    /**
     * 内容
     */
    private String content;

    /**
     * 发表时间
     */
    private Date postTime;

    /**
     * 点击率
     */
    private Long clickCount;

    /**
     * 作者
     */
    private Author author;

    /**
     * 所属教程
     */
    private Tutorial tutorial;

    public Article() {
    }

    public Article(Long id, String title, String abstracts, String content, Date postTime, Long clickCount, Author author,
        Tutorial tutorial) {
        this.id = id;
        this.title = title;
        this.abstracts = abstracts;
        this.content = content;
        this.postTime = postTime;
        this.clickCount = clickCount;
        this.author = author;
        this.tutorial = tutorial;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstracts() {
        return abstracts;
    }

    public void setAbstracts(String abstracts) {
        this.abstracts = abstracts;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getPostTime() {
        return postTime;
    }

    public void setPostTime(Date postTime) {
        this.postTime = postTime;
    }

    public Long getClickCount() {
        return clickCount;
    }

    public void setClickCount(Long clickCount) {
        this.clickCount = clickCount;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Tutorial getTutorial() {
        return tutorial;
    }

    public void setTutorial(Tutorial tutorial) {
        this.tutorial = tutorial;
    }

    @Override
    public String toString() {
        return "Article{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", abstracts='" + abstracts + '\'' +
            ", content='" + content + '\'' +
            ", postTime=" + postTime +
            ", clickCount=" + clickCount +
            ", author=" + author +
            ", tutorial=" + tutorial +
            '}';
    }
}
