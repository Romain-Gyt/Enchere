package fr.eni.enchere.controller;

import fr.eni.enchere.bo.Auction;
import fr.eni.enchere.bll.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private AuctionService auctionService;

    @GetMapping("/")
    public String home(Model model) {
        List<Auction> auctions = auctionService.getAllAuctions();
        model.addAttribute("auctions", auctions);
        return "index";
    }
}