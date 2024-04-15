package fr.eni.enchere.bll.Impl;

import fr.eni.enchere.bll.ArticleService;
import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.User;
import fr.eni.enchere.dal.ArticleDAO;
import org.springframework.stereotype.Service;

import java.util.Date;
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

    @Override
    public List<Article> getArticlesByBuying(User userSession, String nameArticle, Long categoryId, boolean openAuction, boolean currentAuction, boolean closedAuction) {
       nameArticle = checkNameArticle(nameArticle);
       categoryId = checkCategoryId(categoryId);
       Date dateOpenAuction = checkOpenAuction(openAuction);
       long idUser = checkCurrentAuction(userSession,currentAuction);


//        return articleDAO.getArticlesByBuying(nameArticle, categoryId, openAuction, currentAuction, closedAuction);
        return null;
    }

    private String checkNameArticle(String nameArticle) {
        if(nameArticle.isEmpty()){
            return "%";
        }
        return nameArticle + "%";
    }

    private Long checkCategoryId(Long categoryId) {
        if(categoryId == null){
            return null;
        }
        return categoryId;
    }

    private Date checkOpenAuction(boolean openAuction) {
        if(openAuction){
            return new Date(System.currentTimeMillis());
        }
        return null;
    }

    private Long checkCurrentAuction(User userSession,boolean currentAuction) {
        if(currentAuction){
            return userSession.getIdUser() ;
        }
        return null;
    }

}
