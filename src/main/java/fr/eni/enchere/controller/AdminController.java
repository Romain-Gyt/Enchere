package fr.eni.enchere.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"memberSession"})
public class AdminController {
    /********* DECLARATION DES VARIABLES *********/



    /********* CONSTRUCTEUR *********/



    /********* ROUTES *********/
    @GetMapping("/admin")
    public String displayAdmin() {
        return "admin/admin.html";
    }
}
