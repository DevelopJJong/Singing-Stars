package com.developjjong.singingstars.service;

import com.developjjong.singingstars.DataNotFoundException;
import com.developjjong.singingstars.domain.SiteUser;
import com.developjjong.singingstars.domain.Question;
import com.developjjong.singingstars.domain.Comment;
import com.developjjong.singingstars.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment getComment(BigInteger id){
        Optional<Comment> comment = this.commentRepository.findById(id);
        if (comment.isPresent()) {
            return comment.get();
        } else {
            throw new DataNotFoundException("comment not found");
        }
    }

    public Comment create(Question question, String content, SiteUser siteUser){
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreated_at(LocalDateTime.now());
        comment.setQuestion(question);
        comment.setSiteUser(siteUser);
        this.commentRepository.save(comment);

        return comment;
    }
    public void delete(Comment comment){
        commentRepository.delete(comment);
    }

}
