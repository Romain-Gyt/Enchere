package fr.eni.enchere.bll.impl;

import fr.eni.enchere.bll.ArticleService;
import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Auction;
import fr.eni.enchere.dal.ArticleDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleDAO articleDAO;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Article> getAllArticles() {
        return articleDAO.getAllArticles();
//        foreaxch (auction: auctions) {
//            List<Auction> auctions = auctionDAO.getAllAuctions(articleId);
//            article.setAuctions(auctions);
//        }
    }
//
//    getArticleById(int articleId){
//        Article article = articleDAO.getArticlesByCategory(articleId);
//        List<Auction> auctions = auctionDAO.getAllAuctions(articleId);
//        article.setAuctions(auctions);
//
//
//        return article;
//    }

    @Override
    public List<Article> getArticlesByCategory(long categoryId) {
        return articleDAO.getArticlesByCategory(categoryId);

    }

}
