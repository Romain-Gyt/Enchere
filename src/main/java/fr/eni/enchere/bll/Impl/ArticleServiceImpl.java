package fr.eni.enchere.bll.impl;

import fr.eni.enchere.bll.ArticleService;
import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Auction;
import fr.eni.enchere.dal.ArticleDAO;
import fr.eni.enchere.dal.AuctionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDAO articleDAO;

    @Autowired
    private AuctionDAO auctionDAO;

    @Override
    public void setAuctions(Article article) {
        List<Auction> auctions = auctionDAO.getAuctionsByItemId(article.getItemId());
        article.setAuctions(auctions);
    }

    @Override
    public List<Article> getAllArticles() {
        List<Article> articles = articleDAO.getAllArticles();
        for (Article article : articles) {
            List<Auction> auctions = auctionDAO.getAuctionsByItemId(article.getItemId());
            article.setAuctions(auctions);
        }
        return articles;
    }

    @Override
    public Article getArticleById(int itemId) {
        Article article = articleDAO.getArticleById(itemId);
        List<Auction> auctions = auctionDAO.getAuctionsByItemId(itemId);
        article.setAuctions(auctions);
        return article;
    }

    @Override
    public List<Article> getArticlesByCategory(long categoryId) {
        return articleDAO.getArticlesByCategory(categoryId);
    }

}
