package com.developjjong.singingstars.service;

import com.developjjong.singingstars.repository.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Getter
@Setter
@RequiredArgsConstructor
public class MailService{

    private final JavaMailSender javaMailSender;

    private static final String senderEmail= "DevelopJJong@gmail.com";
    private static int number;
    private final UserRepository userRepository;

    public static void createNumber(){
        number = (int)(Math.random() * (90000)) + 100000;// (int) Math.random() * (최댓값-최소값+1) + 최소값
    }

    public MimeMessage CreateMail(String email){
        createNumber();
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, email);
            message.setSubject("이메일 인증");
            String body = "";
            body += "<h3>" + "요청하신 인증 번호입니다." + "</h3>";
            body += "<h1>" + number + "</h1>";
            body += "<h3>" + "감사합니다." + "</h3>";
            message.setText(body,"UTF-8", "html");

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return message;
    }

    public MimeMessage findPwMail(String email, String pw){
        MimeMessage message = javaMailSender.createMimeMessage();


        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, email);
            message.setSubject("임시비밀번호입니다.");
            String body = "";
            body += "<h3>" + "요청하신 임시 비밀번호입니다." + "</h3>";
            body += "<h1>" + pw + "</h1>";
            body += "<h3>" + "감사합니다." + "</h3>";
            message.setText(body,"UTF-8", "html");

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        return message;
    }

    public int sendMail(String email){

        MimeMessage message = CreateMail(email);

        javaMailSender.send(message);

        return number;
    }
}
