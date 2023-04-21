package com.developjjong.singingstars.controller;

import com.developjjong.singingstars.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/mail")
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    @GetMapping("/send")
    public String MailPage(){
        return "author/mail";
    }

    @ResponseBody
    @PostMapping("/send")
    public String MailSend(String email) {
        int number = mailService.sendMail(email);

        String num = "" + number;

        return num;
    }
}
