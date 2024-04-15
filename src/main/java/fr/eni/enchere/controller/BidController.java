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
    private AuctionService auctionService;
    private ArticleService articleService;

    public BidController(ArticleService articleService,
                         WithdrawalsService withdrawalsService,
                         UserService userService,
                         AuctionService auctionService) {
        this.articleService = articleService;
        this.withdrawalsService = withdrawalsService;
        this.userService = userService;
        this.auctionService = auctionService;
    }

    @PostMapping("/showDetails")
    public String showDetails(@RequestParam("itemId") int itemId, Model model) {
        Article article = articleService.getArticleById(itemId);
        Withdrawals withdrawals = withdrawalsService.getWithdrawalsById(itemId);
        Auction auction = auctionService.getAuctionById(itemId);

        if (auction != null) {
            User user = userService.loadUserById(auction.getUserId());
            model.addAttribute("auction", auction);
            model.addAttribute("user", user);
        } else {
            Auction bidAmount = new Auction();
            bidAmount.setBidAmount(0);
            model.addAttribute("auction", bidAmount);
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
                         @ModelAttribute("memberSession") User memberSession) {
        auctionService.insertBidAmountById(memberSession.getIdUser(), itemId, bidAmount);

        User userSession = userService.loadUserById(memberSession.getIdUser());

        int newCreditUser = userSession.getCredit() - bidAmount;
        userService.updateUserCredit(userSession, newCreditUser);

        if(userId != null){
            User user = userService.loadUserById(userId);
            int returnCreditLastUser = user.getCredit() + lastBidAmount;
            userService.updateUserCredit(user, returnCreditLastUser);
        }

        return "index";
    }
}
