package com.developjjong.singingstars.service.singup;

import com.developjjong.singingstars.DataNotFoundException;
import com.developjjong.singingstars.domain.SiteUser;
import com.developjjong.singingstars.domain.singup.SingUp;
import com.developjjong.singingstars.repository.singup.SingUpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SingUpService {

    private final SingUpRepository singUpRepository;

    public List<SingUp> getList(){
        return singUpRepository.findAll();
    }

    public SingUp detail(BigInteger id){
        Optional<SingUp> singUp = this.singUpRepository.findById(id);
        if (singUp.isPresent()){
            return singUp.get();
        } else{
            throw new DataNotFoundException("게시글이 존재하지 않습니다.");
        }
    }

    public void create(String title, String content, SiteUser siteUser){
        SingUp s = new SingUp();
        s.setTitle(title);
        s.setContent(content);
        s.setCreated_at(LocalDateTime.now());
        s.setSiteUser(siteUser);
        this.singUpRepository.save(s);
    }

    public void update(SingUp singUp, String title, String content){
        singUp.setTitle(title);
        singUp.setContent(content);
        singUp.setModified_at(LocalDateTime.now());
        this.singUpRepository.save(singUp);
    }
    public Page<SingUp> getList(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("created_at"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return this.singUpRepository.findAll(pageable);
    }
    public void delete(BigInteger id){
        singUpRepository.deleteById(id);
    }
}
