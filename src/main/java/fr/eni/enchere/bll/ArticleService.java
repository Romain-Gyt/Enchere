package fr.eni.enchere.bll;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Withdrawals;

import java.util.List;

public interface ArticleService {
    List<Article> getAllArticles();
    List<Article> getArticlesByCategory(long categoryId);
    Article getArticleById(int id);
    void setAuctions(Article article);
    void createArticle(Article article, Withdrawals withdrawals);
    void insertBidAmountById(Long userId, int id, int bid_amount);
    void updateArticle(Article existingArticle, Withdrawals withdrawals);
}
