package fr.eni.enchere.bll.Impl;

import fr.eni.enchere.bll.ArticleService;
import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.User;
import fr.eni.enchere.dal.ArticleDAO;
import org.springframework.stereotype.Service;

import java.util.*;

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
       List<Article>articles = new ArrayList<>();
       if(openAuction){
           articles.addAll(articleDAO.getArticlesByOpenedBuying()) ;
       }
       if(currentAuction){
           articles.addAll(articleDAO.getArticlesByCurrentBuying(nameArticle, categoryId, userSession.getIdUser()));
       }
       if(closedAuction){
              articles.addAll(articleDAO.getArticlesByClosedBuying(nameArticle, categoryId, userSession.getIdUser()));

       }
        articles = removeDuplicates(articles);
        return articles;
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

    public List<Article> removeDuplicates(List<Article> articles) {
        Map<Integer, Article> uniqueArticles = new LinkedHashMap<>();
        for (Article article : articles) {
            uniqueArticles.put(article.getItemId(), article);
        }
        return new ArrayList<>(uniqueArticles.values());
    }

}
