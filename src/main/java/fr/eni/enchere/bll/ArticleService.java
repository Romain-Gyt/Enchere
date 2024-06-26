package fr.eni.enchere.bll;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.User;
import fr.eni.enchere.bo.Withdrawals;


import java.util.List;

public interface ArticleService {
    void setAuctions(Article article);

    void createArticle(Article article, Withdrawals withdrawals);

    void insertBidAmountById(Long userId, int id, int bid_amount);

    List<Article> getAllArticles();
    List<Article> getAllSellingArticlesByUser(String articleName,Long categoryId,Long userId);

    Article getArticleById(int itemId);

    List<Article> getArticlesByCategory(long categoryId);

    void updateArticle(Article existingArticle, Withdrawals withdrawals);

    List<Article> getAllArticleByNameAndCategory(String nameArticle, Long categoryId);
    List<Article> getArticlesByBuying  (User userSession,
                                        String nameArticle,
                                        Long categoryId,
                                        boolean openAuction,
                                        boolean currentAuction,
                                        boolean closedAuction
    );
    List<Article> getArticlesBySelling(  User userSession,
                                         String nameArticle,
                                         Long categoryId,
                                         boolean currentSelling,
                                         boolean nonStartedSelling,
                                         boolean closedSelling);

    void deleteArticle(Long idArticle);
}
