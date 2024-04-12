package fr.eni.enchere.controller;

import fr.eni.enchere.bll.*;
import fr.eni.enchere.bo.Bid;
import fr.eni.enchere.bo.SoldItem;
import fr.eni.enchere.bo.User;
import fr.eni.enchere.bo.Withdrawals;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@SessionAttributes({"memberSession"})
public class BidController {

    private CategoryService categoryService;
    private SoldItemService soldItemService;
    private WithdrawalsService withdrawalsService;
    private UserService userService;
    private BidService bidService;

    public BidController(CategoryService categoryService,
                         SoldItemService soldItemService,
                         WithdrawalsService withdrawalsService,
                         UserService userService,
                         BidService bidService) {
        this.categoryService = categoryService;
        this.soldItemService = soldItemService;
        this.withdrawalsService = withdrawalsService;
        this.userService = userService;
        this.bidService = bidService;
    }

    @PostMapping("/showDetails")
    public String showDetails(@RequestParam("itemId") int itemId, @RequestParam("userId") int userId, @ModelAttribute("memberSession") User userSession, Model model) {
        SoldItem soldItem = soldItemService.getSoldItemById(itemId);
        String categorie = categoryService.getCategorieById(soldItem.getCategoryId());
        Withdrawals withdrawals = withdrawalsService.getWithdrawalsById(itemId);
        Bid bid = bidService.getBidById(itemId);

        if (bid != null) {
            model.addAttribute("bid", bid);
        } else {
            Bid bidAmount = new Bid();
            bidAmount.setBid_amount(0);
            model.addAttribute("bid", bidAmount);
        }
        User user = userService.loadUserById(userId);
        model.addAttribute("soldItem", soldItem);
        model.addAttribute("categorie", categorie);
        model.addAttribute("withdrawals", withdrawals);
        model.addAttribute("user", user);
        return "bid/bidDetails";
    }

    @PostMapping("/addBid")
    public String addBid(@RequestParam("bidAmount") int bidAmount,
                         @RequestParam("itemId") int itemId,
                         @RequestParam(value = "userId", required = false) Integer userId,
                         @RequestParam("lastBidAmount") int lastBidAmount,
                         @ModelAttribute("memberSession") User userSession,
                         Model model) {
        bidService.insertBidAmountById(userSession.getIdUser(), itemId, bidAmount);
        System.out.println(userId);
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
