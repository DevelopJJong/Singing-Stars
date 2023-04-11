package com.developjjong.singingstars.controller.singupcontroller;

import com.developjjong.singingstars.domain.Question;
import com.developjjong.singingstars.domain.SiteUser;
import com.developjjong.singingstars.form.CommentForm;
import com.developjjong.singingstars.form.QuestionForm;
import com.developjjong.singingstars.service.UserService;
import com.developjjong.singingstars.service.QuestionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.security.Principal;
import java.util.List;

@RequestMapping("/singup")
@RequiredArgsConstructor
@Controller
@Slf4j
public class SingUpController {
    private final QuestionService questionService;
    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        Page<Question> questionList = questionService.findByType("singup", page);
        model.addAttribute("questionList", questionList);
        return "/singup/singup_list";
    }

    @GetMapping("/view")
    public String view(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        Page<Question> questionList = questionService.findByTypeOrderByView("singup", page);
        model.addAttribute("questionList", questionList);
        return "/singup/singup_list";
    }

    @GetMapping("/vote")
    public String voter(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        Page<Question> questionList = questionService.findByTypeOrderByVote("singup", page);
        model.addAttribute("questionList", questionList);
        return "/singup/singup_list";
    }


    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") BigInteger id, CommentForm commentForm){
        questionService.viewCount(id);
        model.addAttribute("question",questionService.detail(id));

        return "/singup/singup_detail";
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/update/{id}")
    public String update(QuestionForm questionForm, @PathVariable("id") BigInteger id, Principal principal){
        Question question = questionService.getQuestion(id);
        if(!question.getSiteUser().getEmail().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        questionForm.setSubject(question.getTitle());
        questionForm.setContent(question.getContent());
        return "create";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/update/{id}")
    public String update(@Valid QuestionForm questionForm, BindingResult bindingResult,
        Principal principal, @PathVariable("id") BigInteger id){
        if (bindingResult.hasErrors()) {
            return "create";
        }
        Question question = this.questionService.getQuestion(id);
        if (!question.getSiteUser().getEmail().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.questionService.update(question, questionForm.getSubject(), questionForm.getContent());
        return String.format("redirect:/detail/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String create(QuestionForm questionForm){
            return "create";
        }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String create(@Valid QuestionForm questionForm,
                         BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "create";
        }
        SiteUser siteUser = this.userService.getUser(principal.getName());
        Question q = new Question();
        q.setType("singup");
        this.questionService.create(q.getType(), questionForm.getSubject(), questionForm.getContent(), siteUser);
    return "redirect:/singup/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String delete(Principal principal, @PathVariable("id") BigInteger id){
        Question question = this.questionService.getQuestion(id);
        if (!question.getSiteUser().getEmail().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.questionService.delete(id);
        return "redirect:/singup/list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/vote/{id}")
    public String questionVote(Principal principal, @PathVariable("id") BigInteger id) {
        Question question = this.questionService.getQuestion(id);
        SiteUser siteUser = this.userService.getUser(principal.getName());
        this.questionService.vote(question, siteUser);
        return String.format("redirect:/detail/%s", id);
    }

}
