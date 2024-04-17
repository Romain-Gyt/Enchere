package fr.eni.enchere.dal;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Withdrawals;

import java.util.List;

public interface ArticleDAO {
    List<Article> getAllArticles();
    List<Article> getArticlesByCategory(Long categoryId);
    Article getArticleById(int itemId);
    void createArticle(Article article);
    void updateArticle(Article article);
}
