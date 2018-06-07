package main.java.cn.ghl.tools.json;

import main.java.cn.ghl.others.Book;
import main.java.cn.ghl.others.Person;
import main.java.cn.ghl.others.Picture;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import main.java.cn.ghl.tools.object.GhlObjectTranslate;
import main.java.cn.ghl.tools.object.GhlObjectUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyObjectUtilTest {

    private static Logger LOG = LoggerFactory
        .getLogger(MyObjectUtilTest.class);

    private Gson gson = new Gson();

    @Test
    public void parsePropertyTest() {
        Person person = getPerson();
        LOG.debug("{}", gson.toJson(person));
        //GhlObjectUtil.parseProperty(person, "name");
        GhlObjectUtil.parseProperty(person, "age");
        //GhlObjectUtil.parseProperty(person, "man");
        //GhlObjectUtil.parseObject(person, "bookList");
        GhlObjectUtil.parseProperty(person, "bookList.price", new GhlObjectTranslate(10));
        GhlObjectUtil.parseProperty(person, "bookList.pictureList.color");
        GhlObjectUtil.parseProperty(person, "bookList.pictureList.color", new GhlObjectTranslate(10));
        GhlObjectUtil.setProperty(person, "bookList.pictureList.color", 1);
        LOG.debug("{}", gson.toJson(person));
    }

    public Person getPerson() {

        List<Picture> pictureList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Picture picture = new Picture();
            picture.setColor(100 + i);
            picture.setType(500 + i);
            pictureList.add(picture);
        }

        List<Book> bookList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Book book = new Book();
            book.setBookName("book" + i);
            book.setPrice(i);
            book.setTotalPage(100 + i);
            //book.setPictureList(pictureList);

            bookList.add(book);
        }

        Person person = new Person();
        person.setAge(12);
        person.setName("ghl");
        person.setMan(true);
        person.setBookList(bookList);

        return person;
    }
}
