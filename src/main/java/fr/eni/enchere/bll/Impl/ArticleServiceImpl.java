package fr.eni.enchere.bll.Impl;

import fr.eni.enchere.bll.ArticleService;
import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Auction;
import fr.eni.enchere.bo.User;
import fr.eni.enchere.dal.ArticleDAO;
import fr.eni.enchere.dal.AuctionDAO;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ArticleServiceImpl implements ArticleService {
/******** Declaration ********/
    private ArticleDAO articleDAO;
    private AuctionDAO auctionDAO;

    /********** Constructor ********/

    public ArticleServiceImpl(
            ArticleDAO articleDAO,
            AuctionDAO auctionDAO) {
        this.auctionDAO = auctionDAO;
        this.articleDAO = articleDAO;
    }

    /******** Methods ********/

    @Override
    public List<Article> getAllArticles() {
        List<Article> articles = articleDAO.getAllArticles();
        return articleDAO.getAllArticles();
    }

    @Override
    public Article getArticleById(Long idArticle) {
        Article article = articleDAO.getArticleById(idArticle);
        List<Auction> auctions = auctionDAO.getAllAuctions(article.getItemId());
        article.setAuctions(auctions);
        return article;
    }


    public List<Article> getAllArticleByNameAndCategory(String nameArticle, Long categoryId) {
        nameArticle = checkNameArticle(nameArticle);
        categoryId = checkCategoryId(categoryId);
        return articleDAO.getAllArticleByNameAndCategory(nameArticle, categoryId);
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
        System.out.println(nonStartedSelling);
       List<Article> articles = new ArrayList<>();
        if(currentSelling){
            articles.addAll(articleDAO.getArticlesByCurrentSelling(nameArticle, categoryId, userSession.getIdUser()));
        }

        if(nonStartedSelling){
            articles.addAll(articleDAO.getArticlesByNonStartedSelling(nameArticle, categoryId, userSession.getIdUser()));
        }
        if(closedSelling){
            articles.addAll(articleDAO.getArticlesByClosedSelling(nameArticle, categoryId, userSession.getIdUser()));
        }
        return articles;
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
