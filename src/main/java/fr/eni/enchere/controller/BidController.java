package fr.eni.enchere.controller;

import fr.eni.enchere.bll.*;
import fr.eni.enchere.bo.SoldItem;
import fr.eni.enchere.bo.User;
import fr.eni.enchere.bo.Withdrawals;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"memberSession"})
public class BidController {

    private CategoryService categoryService;
    private SoldItemService soldItemService;
    private WithdrawalsService withdrawalsService;
    private UserService userService;

    public BidController(CategoryService categoryService, SoldItemService soldItemService, WithdrawalsService withdrawalsService, UserService userService) {
        this.categoryService = categoryService;
        this.soldItemService = soldItemService;
        this.withdrawalsService = withdrawalsService;
        this.userService = userService;
    }

    @PostMapping("/showDetails")
    public String showDetails(@RequestParam("itemId") int itemId, @RequestParam("userId") int userId, Model model) {
        SoldItem soldItem = soldItemService.getSoldItemById(itemId);
        String categorie = categoryService.getCategorieById(soldItem.getCategoryId());
        Withdrawals withdrawals = withdrawalsService.getWithdrawalsById(itemId);
        System.out.println(userId);
        User user = userService.getUserById(userId);
        model.addAttribute("soldItem", soldItem);
        model.addAttribute("categorie", categorie);
        model.addAttribute("withdrawals", withdrawals);
        model.addAttribute("user", user);
        return "bid/bidDetails";
    }
}
