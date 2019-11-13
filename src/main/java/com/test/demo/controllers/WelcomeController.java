package com.test.demo.controllers;

import com.test.demo.models.User;
import com.test.demo.utils.sys.Const;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.logging.Logger;


@Controller
@RequestMapping("/")
@Scope("session")
public class WelcomeController {
    Logger log = Logger.getLogger(this.getClass().getName());

    @PostMapping
    public String getLogin(Model model) {
        log.warning("=========================START Welcome getLogin=============================");
        model.addAttribute("loginUser", new User());

        log.warning("=========================END  Welcome getLogin=============================");
        return "index";
    }


    @GetMapping
    public String getPersons(Model model, HttpSession session) {
        log.warning("=========================START Welcome getPersons=============================");
        session.setAttribute("isVisible",true);

        log.warning("=========================END  Welcome getPersons=============================");
        return "index";
    }
//    @PostMapping("/logout")
    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        log.warning("=========================Logout Success=============================");
        return Const.PAGE_MAIN;
    }

}
