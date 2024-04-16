package fr.eni.enchere.controller;

import fr.eni.enchere.bll.UserService;
import fr.eni.enchere.bo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.security.Principal;

@Controller
@SessionAttributes({"memberSession"})
public class LoginController {

    private UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login(){
        return "login/login.html";
    }


    @GetMapping("/session")
    public String session(
            @ModelAttribute("memberSession") User memberSession,
            Principal principal
    ){
        String username = principal.getName();
        User user = userService.loadUserByPseudo(username);
        memberSession.setIdUser(user.getIdUser());
        memberSession.setFirstName(user.getFirstName());
        memberSession.setLastName(user.getLastName());
        memberSession.setAdmin(user.isAdmin());
        memberSession.setPseudo(user.getPseudo());
        memberSession.setEmail(user.getEmail());
        memberSession.setPhoneNumber(user.getPhoneNumber());
        memberSession.setStreet(user.getStreet());
        memberSession.setZipCode(user.getZipCode());
        memberSession.setCity(user.getCity());
        memberSession.setCity(user.getCity());
        memberSession.setCredit(user.getCredit());
        memberSession.setPassword(user.getPassword());
        memberSession.setAdmin(user.isAdmin());
        memberSession.setDisabled(user.isDisabled());
        return "redirect:/";
    }

    @ModelAttribute("memberSession")
    public User memberSession(){
        User user = new User();
        return user;
    }
}
