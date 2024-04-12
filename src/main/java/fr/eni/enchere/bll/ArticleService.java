package fr.eni.enchere.bll;

import fr.eni.enchere.bo.Article;

import java.util.List;

public interface ArticleService {
    void setAuctions(Article article);
    List<Article> getAllArticles();
    Article getArticleById(int itemId);
}
