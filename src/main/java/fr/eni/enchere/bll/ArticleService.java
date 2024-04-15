package fr.eni.enchere.bll;

import fr.eni.enchere.bo.Article;

import java.util.List;

public interface ArticleService {
    List<Article> getAllArticles();
    List<Article> getArticlesByCategory(long categoryId);
    Article getArticleById(int id);
}
