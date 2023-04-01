package com.developjjong.singingstars.service.singup;

import com.developjjong.singingstars.domain.SiteUser;
import com.developjjong.singingstars.domain.singup.SingUp;
import com.developjjong.singingstars.domain.singup.SingUpComment;
import com.developjjong.singingstars.repository.singup.SingUpCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class SingUpCommentService {

    private final SingUpCommentRepository singUpCommentRepository;

    public SingUpComment create(SingUp singUp, String content, SiteUser siteUser){
        SingUpComment singUpComment = new SingUpComment();
        singUpComment.setContent(content);
        singUpComment.setCreated_at(LocalDateTime.now());
        singUpComment.setSingUp(singUp);
        singUpComment.setSiteUser(siteUser);
        this.singUpCommentRepository.save(singUpComment);

        return singUpComment;
    }
}
