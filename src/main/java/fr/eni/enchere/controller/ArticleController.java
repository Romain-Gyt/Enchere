package fr.eni.enchere.controller;

import fr.eni.enchere.bll.ArticleService;
import fr.eni.enchere.bll.CategoryService;
import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;

@Controller
@SessionAttributes({"memberSession"})
public class ArticleController {

    private final ArticleService articleService;
    private final CategoryService categoryService;

    public ArticleController(ArticleService articleService, CategoryService categoryService) {
        this.articleService = articleService;
        this.categoryService = categoryService;
    }

    @GetMapping("/auction/create")
    public String showAuctionCreationForm(Model model) {
        model.addAttribute("article", new Article());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "bidCreate";
    }

    @PostMapping("/auction/create")
    public String createAuction(@ModelAttribute("memberSession") User userSession,
                                @ModelAttribute("article") Article article,
                                BindingResult result,
                                Model model,
                                @RequestParam("image") MultipartFile imageFile) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "bidCreate";
        }


        String image = article.getImage();
        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = article.getItemId() + ".jpg";
            article.setImage(fileName);
        }

        // Set user as the seller
        article.setUser(userSession);

        // Save the article
        articleService.createArticle(article);

        return "redirect:/auction/success";
    }

    @GetMapping("/auction/success")
    public String showSuccessPage() {
        return "successPage";
    }
}
