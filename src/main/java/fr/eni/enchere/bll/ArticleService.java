package fr.eni.enchere.bll;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.User;

import java.util.List;

public interface ArticleService {
    List<Article> getAllArticles();
    Article getArticleById(Long idArticle);
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
}
