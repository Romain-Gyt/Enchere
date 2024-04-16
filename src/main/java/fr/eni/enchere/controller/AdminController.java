package fr.eni.enchere.controller;

import fr.eni.enchere.bll.ArticleService;
import fr.eni.enchere.bll.AuctionService;
import fr.eni.enchere.bll.UserService;
import fr.eni.enchere.bo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller
@SessionAttributes({"memberSession"})
public class AdminController {
    /********* DECLARATION DES VARIABLES *********/

    private UserService userService;
    private AuctionService auctionService;
    private ArticleService articleService;


    /********* CONSTRUCTEUR *********/
    public AdminController(
            UserService userService,
            AuctionService auctionService,
            ArticleService articleService
    ) {
        this.userService = userService;
        this.auctionService = auctionService;
        this.articleService = articleService;
    }


    /********* ROUTES *********/
    @GetMapping("/admin")
    public String displayAdmin(Model model) {
        List<User> users = userService.loadAll();
        model.addAttribute("users", users);
        return "admin/admin.html";
    }

    @GetMapping("/admin/delete")
    public String deleteByAdmin(
            @RequestParam("user_id") String id) {
        auctionService.deleteAuction(Integer.parseInt(id));
        articleService.deleteArticle(Long.parseLong(id));
        userService.deleteUser(Long.parseLong(id));
        return "redirect:/admin";
    }

    @GetMapping("/admin/disable")
    public String disableByAdmin(
            @RequestParam("user_id") String id) {
        userService.disableUser(Long.parseLong(id));
        return "redirect:/admin";
    }

    @GetMapping("/admin/enable")
    public String enableByAdmin(
            @RequestParam("user_id") String id) {
        userService.enableUser(Long.parseLong(id));
        return "redirect:/admin";
    }
}
