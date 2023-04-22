package com.developjjong.singingstars.controller;

import com.developjjong.singingstars.domain.Question;
import com.developjjong.singingstars.domain.SiteUser;
import com.developjjong.singingstars.form.QuestionForm;
import com.developjjong.singingstars.service.QuestionService;
import com.developjjong.singingstars.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@RequestMapping("/practice")
@RequiredArgsConstructor
@Controller
public class PracticeController {

    private final QuestionService questionService;
    private final UserService userService;

    @GetMapping("/list")
    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        Page<Question> questionList = questionService.findByType("practice", page);
        model.addAttribute("questionList", questionList);
        return "practice";
    }

    @GetMapping("/view")
    public String view(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        Page<Question> questionList = questionService.findByTypeOrderByView("practice", page);
        model.addAttribute("questionList", questionList);
        return "practice";
    }

    @GetMapping("/vote")
    public String voter(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        Page<Question> questionList = questionService.findByTypeOrderByVote("practice", page);
        model.addAttribute("questionList", questionList);
        return "practice";
    }
    @GetMapping("/comments")
    public String comments(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        Page<Question> questionList = questionService.findByTypeOrderByComments("practice", page);
        model.addAttribute("questionList", questionList);
        return "practice";
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
        q.setType("practice");
        this.questionService.create(q.getType(), questionForm.getSubject(), questionForm.getContent(), siteUser);
        return "redirect:/practice/list";
    }

}
