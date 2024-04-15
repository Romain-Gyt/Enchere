package fr.eni.enchere.controller;

import fr.eni.enchere.bll.UserService;
import fr.eni.enchere.bo.User;
import fr.eni.enchere.exception.RegisterException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class RegisterController {
    /************ DECLARATION ************/
    UserService userService;

    /************ CONSTRUCTOR ************/
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    /************ ROUTES ************/
    @GetMapping("/register")
    public String register(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register/register.html";
    }

    @PostMapping("/register")
    public String registerUser(
          @ModelAttribute("user") User user,
          @RequestParam("confirm_password") String confirmPassword,
          BindingResult bindingResult
    ) {
        Map<String, String> passwordMap = new HashMap<>();
        passwordMap.put("password", user.getPassword());
        passwordMap.put("confirmPassword", confirmPassword);
        System.out.println("errors");
        System.out.println(bindingResult.getAllErrors());
            try{
                userService.createUser(user,passwordMap);
                return "login/login.html";
            } catch(RegisterException e){
                e.getKeys().forEach(key -> {
                    ObjectError error = new ObjectError("globalError", key);
                    bindingResult.addError(error);
                });
                return "register/register.html";
            }
        }
}

