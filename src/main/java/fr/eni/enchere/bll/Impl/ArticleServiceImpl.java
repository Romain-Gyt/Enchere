package fr.eni.enchere.bll.Impl;

import fr.eni.enchere.bll.ArticleService;
import fr.eni.enchere.bo.Article;
import fr.eni.enchere.dal.ArticleDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
/******** Declaration ********/
    private ArticleDAO articleDAO;

    /********** Constructor ********/

    public ArticleServiceImpl(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

    /******** Methods ********/

    @Override
    public List<Article> getAllArticles() {
        List<Article> articles = articleDAO.getAllArticles();
        return articleDAO.getAllArticles();
    }

    @Override
    public List<Article> getArticlesByCategory(long categoryId) {
        return articleDAO.getArticlesByCategory(categoryId);

    }

}
