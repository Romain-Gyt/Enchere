package fr.eni.enchere.controller;

import fr.eni.enchere.bll.AuctionService;
import fr.eni.enchere.bll.CategoryService;
import fr.eni.enchere.bo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private AuctionService auctionService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public String home(Model model) {
        // Récupérer toutes les enchères
        model.addAttribute("auctions", auctionService.getAllAuctions());
        // Récupérer toutes les catégories
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return "index"; // Assurez-vous que "index" est le nom de votre vue Thymeleaf
    }
}
