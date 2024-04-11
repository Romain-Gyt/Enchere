package fr.eni.enchere.controller;

import fr.eni.enchere.bo.User;
import fr.eni.enchere.bll.UserService;
import fr.eni.enchere.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@SessionAttributes({"memberSession"})
public class RegisterController {

    private UserService userService;

//    @Autowired
//    private UserDetailsService userDetailsService;


    public RegisterController(
            UserService userService
    ) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());
        return "register/register.html";
    }

    @PostMapping("/register")
    public String createUser(
            @Valid @ModelAttribute("user") User user,
            BindingResult bindingResult,
            @RequestParam("confirmPassword") String confirmPassword
    ) {
        if (bindingResult.hasErrors()) {
            return "register/register.html";
        } else {
            if(!user.getPassword().equals(confirmPassword)) {
                bindingResult.reject("error.password", "Les mots de passe ne correspondent pas.");
                return "register/register.html";
            }
            try {
//                userService.createUser(user);
//                UserDetails userDetails = userDetailsService.loadUserByUsername(user.getPseudo());
//                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                SecurityContextHolder.getContext().setAuthentication(authentication);

//                UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getPseudo(), user.getPassword());
//                // Authenticate the user
//                Authentication authentication = authenticationManager.authenticate(authRequest);
//                SecurityContext securityContext = SecurityContextHolder.getContext();
//                securityContext.setAuthentication(authentication);
//
//                // Create a new session and add the security context.
//                HttpSession session = request.getSession(true);
//                session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);

                return "redirect:/";
            } catch (BusinessException businessException) {
                businessException.getKeys().forEach(key -> {
                    ObjectError error = new ObjectError("globalError", key);
                    bindingResult.addError(error);
                });
                return "register/register.html";
            }
        }
    }
}