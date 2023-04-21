package com.developjjong.singingstars.controller;

import com.developjjong.singingstars.domain.Comment;
import com.developjjong.singingstars.domain.Question;
import com.developjjong.singingstars.domain.SiteUser;
import com.developjjong.singingstars.form.CommentForm;
import com.developjjong.singingstars.service.CommentService;
import com.developjjong.singingstars.service.QuestionService;
import com.developjjong.singingstars.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.security.Principal;

@RequestMapping("/comment")
@RequiredArgsConstructor
@Controller
public class SingUpCommentController {

    private final QuestionService questionService;
    private final CommentService commentService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String create(Model model, @PathVariable("id") BigInteger id,
                         @Valid CommentForm commentForm, BindingResult bindingResult, Principal principal) {

        Question question = this.questionService.getQuestion(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", question);
            return "singup/singup_detail";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.commentService.create(question, commentForm.getContent(), siteUser);
        return String.format("redirect:/singup/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String answerDelete(Principal principal, @PathVariable("id") BigInteger id) {
        Comment comment = this.commentService.getComment(id);
        if (!comment.getSiteUser().getEmail().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.commentService.delete(comment);
        return String.format("redirect:/detail/%s", comment.getQuestion().getId());
    }


}
