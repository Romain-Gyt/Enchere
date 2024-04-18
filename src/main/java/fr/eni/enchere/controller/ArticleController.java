package fr.eni.enchere.controller;

import fr.eni.enchere.bll.*;
import fr.eni.enchere.bll.Impl.AuctionServiceImpl;
import fr.eni.enchere.bo.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@SessionAttributes({"memberSession"})
public class ArticleController {

    private final ArticleService articleService;
    private final CategoryService categoryService;
    private final UserService userService;
    private final WithdrawalsService withdrawalsService;
    private final AuctionService auctionService;

    public ArticleController(ArticleService articleService, CategoryService categoryService, UserService userService, WithdrawalsService withdrawalsService, AuctionService auctionService) {
        this.articleService = articleService;
        this.categoryService = categoryService;
        this.userService = userService;
        this.withdrawalsService = withdrawalsService;
        this.auctionService = auctionService;
    }

    @GetMapping("/article/create")
    public String showArticleCreationForm(Model model) {
        Article article = new Article();
        model.addAttribute("article", article);

        model.addAttribute("categories", categoryService.getAllCategories());
        return "article/articleCreate";
    }

    @PostMapping("/article/create")
    public String createArticle(@ModelAttribute("memberSession") User userSession,
                                @ModelAttribute("article") Article article,
                                BindingResult result,
                                Model model) throws IOException {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            model.addAttribute("errorMessage", "Le formulaire n'a pas été soumis correctement");
            return "article/articleCreate";
        }

        article.setUser(userSession);
        articleService.createArticle(article, article.getWithdrawals());

        return "/article/success";
    }

    @GetMapping("/article/edit/{itemId}")
    public String showArticleEditForm(@PathVariable int itemId, Model model) {
        Article article = articleService.getArticleById(itemId);
        model.addAttribute("article", article);
        model.addAttribute("withdrawals", new Withdrawals());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "article/articleEdit";
    }

    @PostMapping("/article/edit/{itemId}")
    public String editArticle(@PathVariable int itemId,
                              @ModelAttribute("article") Article article,
                              @ModelAttribute("withdrawals") Withdrawals withdrawals,
                              BindingResult result,
                              Model model) throws IOException {
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


        articleService.updateArticle(existingArticle, withdrawals);

        return "redirect:/article/details/" + itemId;
    }


    @GetMapping("/article/detail/{itemId}")
    public String showArticleDetails(@PathVariable int itemId, Model model) {
        Article article = articleService.getArticleById(itemId);
        Withdrawals withdrawals = withdrawalsService.getWithdrawalsByArticleId(itemId);
        List<Auction> auctions = auctionService.getAuctionsByArticleId(itemId);
        model.addAttribute("article", article);
        model.addAttribute("withdrawals", withdrawals);
        model.addAttribute("auctions", auctions);
        return "article/articleDetail";
    }


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
