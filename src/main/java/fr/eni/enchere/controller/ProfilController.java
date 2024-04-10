package fr.eni.enchere.controller;

import fr.eni.enchere.bll.UserService;
import fr.eni.enchere.bo.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/profil/display")
    public String displayUpdatedProfil(Model model, HttpSession session) {
        User userSession = (User) session.getAttribute("memberSession");
        model.addAttribute("memberSession", mem);
        return "profil/profil.html";
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
            HttpSession session
    ) {
        Map<String, String> passwordMap = new HashMap<>();
        passwordMap.put("currentPassword", user.getPassword());
        passwordMap.put("newPassword", newPassword);
        passwordMap.put("confirmPassword", confirmPassword);
        User userSession = (User) session.getAttribute("memberSession");
         userSession = userService.udpadeUser(user, userSession, passwordMap);
        session.setAttribute("memberSession",userSession);
        System.out.println(userSession);
        return "redirect:/profil/display";
    }

    @GetMapping("/profil/delete")
    public String deleteProfil(@RequestParam Long id) {
        System.out.println(id);
        //userService.deleteUser(id);
        return "redirect:/logout";
    }
}
