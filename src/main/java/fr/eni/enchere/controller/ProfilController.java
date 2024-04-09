package fr.eni.enchere.controller;

import fr.eni.enchere.bo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"memberSession"})
public class ProfilController {

    @GetMapping("/profil/details")
    public String displayProfil() {
        return "profil/details.html";
    }
}
