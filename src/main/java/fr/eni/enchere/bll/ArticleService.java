package fr.eni.enchere.bll;

import fr.eni.enchere.bo.Article;

import java.util.List;

public interface ArticleService {
    List<Article> getAllArticles();
    List<Article> selectByCategory(int categoryId);
}
