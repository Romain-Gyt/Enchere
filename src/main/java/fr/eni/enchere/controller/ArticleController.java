package fr.eni.enchere.controller;

import fr.eni.enchere.bll.*;
import fr.eni.enchere.bo.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@SessionAttributes({"memberSession"})
public class ArticleController {
    /******** Declaration ********/
    ArticleService articleService;

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
        Auction auction = auctionService.getAuctionById(itemId);

        if (auction != null) {
            User user = userService.loadUserById(auction.getUserId());
            model.addAttribute("auction", auction);
            model.addAttribute("user", user);
            Date currentDate = new Date();
            Date endDate = article.getEndAuctionDate();
            if (endDate != null && endDate.before(currentDate)){
                model.addAttribute("winner", user.getPseudo());
            }
        } else {
            Auction bidAmount = new Auction();
            bidAmount.setBidAmount(0);
            model.addAttribute("auction", bidAmount);
            model.addAttribute("user", null);
        }
        model.addAttribute("article", article);
        model.addAttribute("withdrawals", withdrawals);
        return "article/articleDetail";
    }


    @PostMapping("/article/addBid")
    public String addBid(@RequestParam("bidAmount") int bidAmount,
                         @RequestParam("itemId") int itemId,
                         @RequestParam(value = "userId", required = false) Integer userId,
                         @RequestParam("lastBidAmount") int lastBidAmount,
                         @ModelAttribute("memberSession") User memberSession) {
        articleService.insertBidAmountById(memberSession.getIdUser(), itemId, bidAmount);

        User userSession = userService.loadUserById(memberSession.getIdUser());
        int newCreditUser = userSession.getCredit() - bidAmount;
        userService.updateUserCredit(userSession, newCreditUser);

        if(userId != null){
            User user = userService.loadUserById(userId);
            int returnCreditLastUser = user.getCredit() + lastBidAmount;
            userService.updateUserCredit(user, returnCreditLastUser);
        }

        return "redirect:/";
    }
}
