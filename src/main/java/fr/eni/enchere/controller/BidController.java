package fr.eni.enchere.controller;

import fr.eni.enchere.bll.*;
import fr.eni.enchere.bo.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes({"memberSession"})
public class BidController {

    private WithdrawalsService withdrawalsService;
    private UserService userService;
    private BidService bidService;
    private ArticleService articleService;

    public BidController(ArticleService articleService,
                         WithdrawalsService withdrawalsService,
                         UserService userService,
                         BidService bidService) {
        this.articleService = articleService;
        this.withdrawalsService = withdrawalsService;
        this.userService = userService;
        this.bidService = bidService;
    }

    @PostMapping("/showDetails")
    public String showDetails(@RequestParam("itemId") int itemId, @ModelAttribute("memberSession") User userSession, Model model) {
        Article article = articleService.getArticleById(itemId);
        Withdrawals withdrawals = withdrawalsService.getWithdrawalsById(itemId);
        Bid bid = bidService.getBidById(itemId);

        if (bid != null) {
            model.addAttribute("bid", bid);
        } else {
            Bid bidAmount = new Bid();
            bidAmount.setBid_amount(0);
            model.addAttribute("bid", bidAmount);
        }
        model.addAttribute("article", article);
        model.addAttribute("withdrawals", withdrawals);
        return "bid/bidDetails";
    }

    @PostMapping("/addBid")
    public String addBid(@RequestParam("bidAmount") int bidAmount,
                         @RequestParam("itemId") int itemId,
                         @RequestParam(value = "userId", required = false) Integer userId,
                         @RequestParam("lastBidAmount") int lastBidAmount,
                         @ModelAttribute("memberSession") User userSession) {
        bidService.insertBidAmountById(userSession.getIdUser(), itemId, bidAmount);
        if(userId != null){
            System.out.println("user davant");
            User user = userService.loadUserById(userId);
            int returnCreditLastUser = user.getCredit() + lastBidAmount;
            userService.updateUserCredit(user, returnCreditLastUser);
        }

        int newCreditUser = userSession.getCredit() - bidAmount;
        userService.updateUserCredit(userSession, newCreditUser);

        return "index";
    }
}
