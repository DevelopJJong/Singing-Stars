package com.developjjong.singingstars.controller.singupcontroller;

import com.developjjong.singingstars.domain.singup.SingUp;
import com.developjjong.singingstars.service.UserService;
import com.developjjong.singingstars.service.singup.SingUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;

@RequestMapping("/api")
@RequiredArgsConstructor
@Controller
public class SingUpController {
    private final SingUpService singUpService;
    private final UserService userService;

//    @GetMapping("/singup")
//    public String list(Model model, @RequestParam(value="page", defaultValue = "0") int page) {
//        Page<SingUp> paging = singUpService.getList(page);
//        model.addAttribute("paging", paging);
//        return "/singup/singup_list";
//    }

//    @GetMapping("/singup/{singup-id}")
//    public String detail(Model model, @PathVariable BigInteger id, )
}
