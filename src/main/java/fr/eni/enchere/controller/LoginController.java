package fr.eni.enchere.controller;

import fr.eni.enchere.bo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"memberSession"})
public class LoginController {

    @GetMapping("/login")
    public String login(){
        return "login/login.html";
    }

    @ModelAttribute("memberSession")
    public User memberSession(){
        return new User();
    }
}
