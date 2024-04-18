package fr.eni.enchere.dal;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Withdrawals;

import java.util.List;

public interface ArticleDAO {
    List<Article> getAllArticles();
    List<Article> getArticlesByCategory(Long categoryId);
    Article getArticleById(int itemId);
    int createArticle(Article article);
    void updateArticle(Article article);
    List<Article> getArticlesByOpenedBuying();
    List<Article> getAllArticleByNameAndCategory(String nameArticle, Long categoryId);
    List<Article> getArticlesByCurrentBuying(String nameArticle, Long categoryId, Long userId);
    List<Article> getArticlesByClosedBuying(String nameArticle, Long categoryId, Long userId);
    List<Article> getArticlesByCurrentSelling(String nameArticle, Long categoryId, Long userId);
    List<Article> getArticlesByNonStartedSelling(String nameArticle, Long categoryId, Long userId);
    List<Article> getArticlesByClosedSelling(String nameArticle, Long categoryId, Long userId);
    void deleteArticle(Long idArticle);
}
