package fr.eni.enchere.dal;

import fr.eni.enchere.bo.Article;

import java.util.List;

public interface ArticleDAO {
    List<Article> getAllArticles();
    List<Article> getArticlesByOpenedBuying();
    List<Article> getAllArticleByNameAndCategory(String nameArticle, Long categoryId);
    List<Article> getArticlesByCurrentBuying(String nameArticle, Long categoryId, Long userId);
    List<Article> getArticlesByClosedBuying(String nameArticle, Long categoryId, Long userId);
}
