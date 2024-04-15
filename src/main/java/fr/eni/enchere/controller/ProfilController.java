package fr.eni.enchere.controller;

import fr.eni.enchere.bll.UserService;
import fr.eni.enchere.bo.User;
import fr.eni.enchere.exception.RegisterException;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@SessionAttributes({"memberSession"})
public class ProfilController {

    /********* DECLARATION *********/
    private UserService userService;

    /********* CONSTRUCTOR *********/
    public ProfilController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profil")
    public String displayProfil() {
        return "profil/profil.html";
    }

    @GetMapping("/profil/{id}")
    public String displayViewedProfil(
            @PathVariable("id") String id,
            Model model
    ) {
        System.out.println(id);
        User user = userService.loadUserById(Long.parseLong(id));
        model.addAttribute("user", user);
        System.out.println(user);
        return "profil/profil-view.html";
    }

    @GetMapping("/profil/details")
    public String displayProfilDetails(
            Model model
    ) {
        User user = new User();
        model.addAttribute("user", user);
        return "profil/details.html";
    }

    @PostMapping("/profil/details")
    public String updateProfil(
            @ModelAttribute("user") User user,
            @RequestParam("new_password") String newPassword,
            @RequestParam("confirm_password") String confirmPassword,
            HttpSession session,
            Model model,
            BindingResult bindingResult
    ) {
        Map<String, String> passwordMap = new HashMap<>();
        passwordMap.put("currentPassword", user.getPassword());
        passwordMap.put("newPassword", newPassword);
        passwordMap.put("confirmPassword", confirmPassword);
        User userSession = (User) session.getAttribute("memberSession");
        try{
            userSession = userService.udpadeUser(user, userSession, passwordMap);
            model.addAttribute("memberSession", userSession);
        } catch (RegisterException e){
            e.getKeys().forEach(key -> {
                ObjectError error = new ObjectError("globalError", key);
                bindingResult.addError(error);
            });
            return "profil/details.html";
        }

        return "profil/profil.html";
    }

    @GetMapping("/profil/delete")
    public String deleteProfil(
            @RequestParam("user_id") String id) {
        userService.deleteUser(Long.parseLong(id));
        return "redirect:/logout";
    }
}
