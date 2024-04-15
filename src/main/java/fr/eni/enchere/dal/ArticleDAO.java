package fr.eni.enchere.dal;

import fr.eni.enchere.bo.Article;

import java.util.List;

public interface ArticleDAO {
    List<Article> getAllArticles();
    List<Article> getArticlesByCategory(Long categoryId);
    Article getArticleById(int id);
}
