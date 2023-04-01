package com.developjjong.singingstars.service.singup;

import com.developjjong.singingstars.domain.SiteUser;
import com.developjjong.singingstars.repository.singup.SingUpRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class SingUpServiceTest {

    @Autowired private SingUpRepository singUpRepository;
    @Autowired private SingUpService singUpService;

    @Test
    void create() {
        SiteUser siteUser = new SiteUser();
        siteUser.setEmail("asd@naver.com");
        siteUser.setNickname("asd");
        siteUser.setPassword("123");
        String title = "첫번째";
        String content = "내용";

    this.singUpService.create(title,content, siteUser);
    }
}