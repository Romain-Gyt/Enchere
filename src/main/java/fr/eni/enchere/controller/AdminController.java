package fr.eni.enchere.controller;

import fr.eni.enchere.bll.AdminService;
import fr.eni.enchere.bll.ArticleService;
import fr.eni.enchere.bll.AuctionService;
import fr.eni.enchere.bll.UserService;
import fr.eni.enchere.bo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller
@SessionAttributes({"memberSession"})
public class    AdminController {
    /********* DECLARATION DES VARIABLES *********/

    private UserService userService;
    private AuctionService auctionService;
    private ArticleService articleService;
    private AdminService adminService;


    /********* CONSTRUCTEUR *********/
    public AdminController(
            UserService userService,
            AuctionService auctionService,
            ArticleService articleService,
            AdminService adminService
    ) {
        this.userService = userService;
        this.auctionService = auctionService;
        this.articleService = articleService;
        this.adminService = adminService;
    }


    /********* ROUTES *********/
    @GetMapping("/admin")
    public String displayAdmin(Model model) {
        List<User> users = userService.loadAll();
        List<User> deletedUsers = adminService.loadAllDeletedAccount();
        model.addAttribute("users", users);
        model.addAttribute("deletedUsers", deletedUsers);
        return "admin/admin.html";
    }

    @GetMapping("/admin/delete")
    public String deleteByAdmin(
            @RequestParam("user_id") String id) {
        User user = userService.loadUserById(Long.parseLong(id));
        adminService.insertDeleteAccount(user);
        auctionService.deleteAuction(Integer.parseInt(id));
        articleService.deleteArticle(Long.parseLong(id));
        adminService.deleteUser(Long.parseLong(id));
        return "redirect:/admin";
    }

    @GetMapping("/admin/disable")
    public String disableByAdmin(
            @RequestParam("user_id") String id) {
        adminService.disableUser(Long.parseLong(id));
        return "redirect:/admin";
    }

    @GetMapping("/admin/enable")
    public String enableByAdmin(
            @RequestParam("user_id") String id) {
        adminService.enableUser(Long.parseLong(id));
        return "redirect:/admin";
    }

    @GetMapping("/admin/filter")
    @ResponseBody
    public List<User> filterUsers(Model model,
                              @RequestParam("sort") String sort) {

        if(sort.equals("all")) {
          return  userService.loadAll();

        }
        if(sort.equals("deleted")) {
           return adminService.loadAllDeletedAccount();
        }
        if(sort.equals("actif")) {
            return adminService.loadActiveAccount();
        }

        if(sort.equals("disabled")) {
            return adminService.loadDisabledAccount();
        }
        return userService.loadAll();
    }
}
