package com.developjjong.singingstars.service.singup;

import com.developjjong.singingstars.domain.SiteUser;
import com.developjjong.singingstars.repository.QuestionRepository;
import com.developjjong.singingstars.repository.UserRepository;
import com.developjjong.singingstars.service.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class QuestionServiceTest {

    @Autowired private QuestionRepository questionRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private QuestionService questionService;

    @Test
    void create() {

    }
}