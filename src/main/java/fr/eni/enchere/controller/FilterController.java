package fr.eni.enchere.controller;

import fr.eni.enchere.bo.Auction;
import fr.eni.enchere.bll.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FilterController {

    @Autowired
    private AuctionService auctionService;

    @GetMapping("/filterByCategory")
    public ResponseEntity<?> filterByCategory(@RequestParam Long categoryId) {
        List<Auction> filteredAuctions = auctionService.getAuctionsByCategory(categoryId);
        return ResponseEntity.ok(filteredAuctions);
    }
}
