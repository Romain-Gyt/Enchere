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



    public List<Article> getAllArticleByNameAndCategory(String nameArticle, Long categoryId) {
        nameArticle = checkNameArticle(nameArticle);
        categoryId = checkCategoryId(categoryId);
        return articleDAO.getAllArticleByNameAndCategory(nameArticle, categoryId);
    }

    @Override
    public List<Article> getArticlesByOpenedBuying() {
        return articleDAO.getArticlesByOpenedBuying();
    }

    @Override
    public List<Article> getArticlesByBuying(User userSession, String nameArticle, Long categoryId, boolean openAuction, boolean currentAuction, boolean closedAuction) {
       nameArticle = checkNameArticle(nameArticle);
       categoryId = checkCategoryId(categoryId);
       if(openAuction){
           return articleDAO.getArticlesByOpenedBuying();
       }
       if(currentAuction){
           return articleDAO.getArticlesByCurrentBuying(nameArticle, categoryId, userSession.getIdUser());
       }
       if(closedAuction){
              return articleDAO.getArticlesByClosedBuying(nameArticle, categoryId, userSession.getIdUser());

       }
        return null;
    }

    public List<Article> getArticlesBySelling(User userSession, String nameArticle, Long categoryId, boolean currentSelling, boolean nonStartedSelling, boolean closedSelling) {
        nameArticle = checkNameArticle(nameArticle);
        categoryId = checkCategoryId(categoryId);
        return null;
    }


    private String checkNameArticle(String nameArticle) {
        if(nameArticle.isEmpty()){
            return "%";
        }
        return nameArticle + "%";
    }

    private Long checkCategoryId(Long categoryId) {
        if(categoryId == 0){
            return null;
        }
        return categoryId;
    }


}
