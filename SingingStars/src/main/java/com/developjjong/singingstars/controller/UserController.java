package com.developjjong.singingstars.controller;

import com.developjjong.singingstars.DataNotFoundException;
import com.developjjong.singingstars.domain.Question;
import com.developjjong.singingstars.domain.SiteUser;
import com.developjjong.singingstars.form.UserCreateForm;
import com.developjjong.singingstars.service.CommentService;
import com.developjjong.singingstars.service.QuestionService;
import com.developjjong.singingstars.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigInteger;

@RequestMapping("/user")
@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;
    private final QuestionService questionService;
    private final CommentService commentService;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "/author/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/author/signup";
        }

        if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "/author/signup";
        }

        try {
            userService.create(userCreateForm.getEmail(),
                    userCreateForm.getPassword1(), userCreateForm.getNickname());
        }catch(DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "/author/signup";
        }catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "/author/signup";
        }

        return "redirect:/";
    }

    @GetMapping("/findpw")
    public String findPw(){
        return "/author/findpw";
    }

    @PostMapping("/findpw")
    public String findPw(@RequestParam String email){
        Boolean result = userService.findPw(email);

        if(result==true) {
            return "redirect:/user/login";
        } else {
            throw new DataNotFoundException("이메일이 존재하지 않거나 틀렸습니다");
        }
    }

    @GetMapping("/login")
    public String login() {
        return "/author/login";
    }

}
