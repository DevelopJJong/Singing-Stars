package com.developjjong.singingstars.controller;

import com.developjjong.singingstars.domain.Question;
import com.developjjong.singingstars.domain.SiteUser;
import com.developjjong.singingstars.form.CommentForm;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;
import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final QuestionService questionService;
    private final UserService userService;

    @GetMapping("/")
    String root(Model model){
        List<Question> list = questionService.getViewList();
        List<Question> list2 = questionService.getVoterList();
        List<Question> list3 = questionService.getCommentsList();
        model.addAttribute("list", list);
        model.addAttribute("list2", list2);
        model.addAttribute("list3", list3);
        return "index";
    }


    @GetMapping("/list")
    public String list(Model model, @RequestParam(value="page", defaultValue="0") int page) {
        Page<Question> questionList = questionService.findByType("announce", page);
        model.addAttribute("questionList", questionList);
        return "announce";
    }

    @GetMapping("/detail/{id}")
    public String detail(Model model, @PathVariable("id") BigInteger id, CommentForm commentForm){
        questionService.viewCount(id);
        model.addAttribute("question",questionService.detail(id));

        return "singup/singup_detail";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/create")
    public String create(QuestionForm questionForm){
        return "create";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public String create(@Valid QuestionForm questionForm,
                         BindingResult bindingResult, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "create";
        }

        SiteUser siteUser = this.userService.getUser(principal.getName());
        Question q = new Question();
        q.setType("announce");
        this.questionService.create(q.getType(), questionForm.getSubject(), questionForm.getContent(), siteUser);
        return "redirect:/list";
    }

    @GetMapping("/search")
    public String search(Model model, String search, @RequestParam(value="page", defaultValue="0") int page){

        Page<Question> questionList = questionService.search(search, page);
        if(search != null) {
            model.addAttribute("questionList", questionList);
            return "search";
        }else
            return "redirect:/";
    }
}
