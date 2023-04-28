package com.example.genshinwiki;

import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.crypto.SecureUtil;
import com.example.genshinwiki.application.services.UserService;
import com.example.genshinwiki.domain.abstracts.Article;
import com.example.genshinwiki.domain.entity.Subject;
import com.example.genshinwiki.domain.entity.User;
import com.example.genshinwiki.domain.entity.article.Tip;
import com.example.genshinwiki.domain.enums.ArticleStatus;
import com.example.genshinwiki.domain.enums.Role;
import com.example.genshinwiki.domain.repository.ArticleRepo;
import com.example.genshinwiki.domain.repository.SubjectRepo;
import com.example.genshinwiki.domain.repository.UserRepo;
import com.example.genshinwiki.infrastructure.common.support.SnowflakeIdGenerator;
import com.example.genshinwiki.infrastructure.consts.AppConst;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@SpringBootTest
class GenshinWikiApplicationTests {
    @Autowired
    private StringRedisTemplate template;
    @Autowired
    private UserService service;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ArticleRepo articleRepo;
    @Autowired
    private SubjectRepo subjectRepo;

    @Test
    void testRedis() {
        template.opsForValue().set("id", "token");
        System.out.println(template.opsForValue().get("id"));
    }

    @Test
    void testPasswordEncode() {
        System.out.println(SecureUtil.md5("123456"));
    }

    @Test
    void testAspect() {
        template.opsForValue().set("id", "352706293162573824");

    }

    @Data
    static class TestBean {
        String link;
        String title;
        String content;
        String author;
        String tag;
        String readingVolume;

    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    void readTip() {
        final CsvReader reader = CsvUtil.getReader();
        final String pwd = "123456";

        final List<TestBean> result = reader.read(
                ResourceUtil.getReader("D:\\19103\\课程设计\\课设2022.6 大三下\\genshin-wiki\\genshinTip.csv", Charset.forName("gbk")), TestBean.class);
//        result.forEach(System.out::println);
        Map<String, User> userMap = new HashMap<>();
        AtomicInteger i = new AtomicInteger(0);
        userMap.put("root", userRepo.findUserByUsernameIs("root"));
        result.forEach(r -> {
            if (Objects.equals(r.author, "")) {
                r.author = "root";
            }
            if (!userMap.containsKey(r.author)) {
                User user = new User(Role.ROLE_USER);
                user.setPassword(SecureUtil.md5(pwd));
                user.setPortrait(Paths.get(
                        AppConst.Dir.USER_DIR,
                        AppConst.Dir.IMG_DIR,
                        AppConst.IMG_LIST.get(i.get() % AppConst.IMG_LIST.size())
                ).toString());
                user.setName(r.author);
                user.setUsername(SnowflakeIdGenerator.SNOWFLAKE.nextIdStr().substring(10));
                userMap.put(user.getName(), userRepo.save(user));
            }
            i.getAndIncrement();
        });
        result.forEach(
                r -> {
                    User user = userMap.get(r.author);
                    if (Objects.isNull(subjectRepo.findByTitleIs(r.tag))) {
                        Subject subject = new Subject();
                        subject.setAuthor(userMap.get("root"));
                        subject.setTitle(r.tag);
                        subjectRepo.save(subject);
                    }
                    Article article = new Tip(user);
                    Subject subject = subjectRepo.findByTitleIs(r.tag);
                    subject.setDiscussion(subject.getDiscussion() + 1);
                    article.setSubjects(ListUtil.toList(subjectRepo.save(subject)));
                    if (Objects.equals(r.readingVolume, "")) {
                        r.readingVolume = "0";
                    }
                    article.setReadingVolume(Integer.parseInt(r.readingVolume));
                    article.setStatus(ArticleStatus.Published);
                    article.setContent(r.content);
                    article.setTitle(r.title);
                    articleRepo.save(article);
                }
        );
    }

    @Test
    public void testGetHostAddress() throws UnknownHostException {
        InetAddress address = InetAddress.getLocalHost();
        System.out.println(Arrays.toString(address.getAddress()));
        System.out.println(address.getHostAddress());

    }
}
