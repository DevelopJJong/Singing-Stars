package com.developjjong.singingstars.service;

import com.developjjong.singingstars.DataNotFoundException;
import com.developjjong.singingstars.domain.SiteUser;
import com.developjjong.singingstars.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
}
