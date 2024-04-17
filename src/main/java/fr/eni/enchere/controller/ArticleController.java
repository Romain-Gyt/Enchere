package fr.eni.enchere.controller;

import fr.eni.enchere.bll.*;
import fr.eni.enchere.bo.*;
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
    private final UserService userService;

    public ArticleController(ArticleService articleService, CategoryService categoryService, UserService userService) {
        this.articleService = articleService;
        this.categoryService = categoryService;
        this.userService = userService;
    }

    @GetMapping("/article/create")
    public String showArticleCreationForm(Model model) {
        model.addAttribute("article", new Article());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "article/articleCreate";
    }

    @PostMapping("/article/create")
    public String createArticle(@ModelAttribute("memberSession") User userSession,
                                @ModelAttribute("article") Article article,
                                BindingResult result,
                                Model model,
                                @RequestParam("image") MultipartFile imageFile) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("errorMessage", "Le formulaire n'a pas été soumis correctement");
            return "article/articleCreate";
        }

        article.setUser(userSession);

        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = UUID.randomUUID().toString() + ".jpg";
            article.setImage(fileName);
        }

        articleService.createArticle(article);

        return "redirect:/article/success";
    }

    @GetMapping("/article/edit/{itemId}")
    public String showArticleEditForm(@PathVariable int itemId, Model model) {
        Article article = articleService.getArticleById(itemId);
        model.addAttribute("article", article);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "article/articleEdit";
    }

    @PostMapping("/article/edit/{itemId}")
    public String editArticle(@PathVariable int itemId,
                              @ModelAttribute("article") Article article,
                              BindingResult result,
                              Model model,
                              @RequestParam(value = "image", required = false) MultipartFile imageFile) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("errorMessage", "Le formulaire n'a pas été soumis correctement");
            return "article/articleEdit";
        }

        Article existingArticle = articleService.getArticleById(itemId);
        if (existingArticle == null) {
            model.addAttribute("errorMessage", "Article introuvable");
            return "article/error";
        }

        existingArticle.setItemName(article.getItemName());
        existingArticle.setDescription(article.getDescription());
        existingArticle.setCategory(article.getCategory());
        existingArticle.setInitialPrice(article.getInitialPrice());
        existingArticle.setStartAuctionDate(article.getStartAuctionDate());
        existingArticle.setEndAuctionDate(article.getEndAuctionDate());

        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = UUID.randomUUID().toString() + ".jpg";
            existingArticle.setImage(fileName);
        }

        articleService.updateArticle(existingArticle);

        return "redirect:/article/details/" + itemId;
    }

    @GetMapping("/article/detail/{itemId}")
    public String showArticleDetails(@PathVariable int itemId, Model model) {
        Article article = articleService.getArticleById(itemId);
        model.addAttribute("article", article);
        return "article/articleDetail";
    }

    // Mapping pour ajouter une enchère
    @PostMapping("/article/addBid")
    public String addBid(@RequestParam("bidAmount") int bidAmount,
                         @RequestParam("itemId") int itemId,
                         @ModelAttribute("memberSession") User memberSession) {
        articleService.insertBidAmountById(memberSession.getIdUser(), itemId, bidAmount);

        User userSession = userService.loadUserById(memberSession.getIdUser());
        int newCreditUser = userSession.getCredit() - bidAmount;
        userService.updateUserCredit(userSession, newCreditUser);

        return "redirect:/";
    }

}
