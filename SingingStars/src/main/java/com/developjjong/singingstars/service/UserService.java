package com.developjjong.singingstars.service;

import com.developjjong.singingstars.DataNotFoundException;
import com.developjjong.singingstars.domain.SiteUser;
import com.developjjong.singingstars.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

        return userRepository.save(siteUser);
    }

    public SiteUser getUser(String nickname){
        Optional<SiteUser> user = userRepository.findByNickname(nickname);
        if (user.isPresent()){
            return user.get();
        } else {
            throw new DataNotFoundException("user not found");
        }
    }
}
