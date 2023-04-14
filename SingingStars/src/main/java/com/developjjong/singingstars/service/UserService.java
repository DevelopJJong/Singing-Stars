package com.developjjong.singingstars.service;

import com.developjjong.singingstars.DataNotFoundException;
import com.developjjong.singingstars.domain.SiteUser;
import com.developjjong.singingstars.repository.UserRepository;
import jakarta.mail.internet.MimeMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
@Getter
@Setter
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    private final JavaMailSender javaMailSender;

    public static class RandomStringGenerator {
        private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        public static String generateRandomString(int length) {
            StringBuilder sb = new StringBuilder();
            Random random = new Random();
            for (int i = 0; i < length; i++) {
                int index = random.nextInt(CHARACTERS.length());
                sb.append(CHARACTERS.charAt(index));
            }
            return sb.toString();
        }
    }

    public SiteUser create(String email, String password, String nickname){
        SiteUser siteUser = new SiteUser();
        siteUser.setEmail(email);
        siteUser.setNickname(nickname);
        siteUser.setPassword(passwordEncoder.encode(password));
        siteUser.setCreated_at(LocalDateTime.now());
        return userRepository.save(siteUser);
    }

    public SiteUser getUser(String email){
        Optional<SiteUser> user = userRepository.findByEmail(email);
        if (user.isPresent()){
            return user.get();
        } else {
            throw new DataNotFoundException("Nickname is not found");
        }
    }
    public SiteUser getUserById(BigInteger id) {
        Optional<SiteUser> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new DataNotFoundException("User is not found");
        }
    }


        public Boolean findPw(String email){
        SiteUser siteUser = getUser(email);
        if(siteUser != null){
            String newPassword = RandomStringGenerator.generateRandomString(8);
            String newEncodedPassword = passwordEncoder.encode(newPassword);
            siteUser.setPassword(newEncodedPassword);
            userRepository.save(siteUser);
            MimeMessage message = mailService.findPwMail(email, newPassword);
            javaMailSender.send(message);
            return true;
        }else{
            return false;
        }
    }

}
