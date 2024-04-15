package fr.eni.enchere.bll;

import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.User;

import java.util.List;

public interface ArticleService {
    List<Article> getAllArticles();
    List<Article> getArticlesByCategory(long categoryId);
    List<Article> getArticlesByBuying(
            User userSession,
            String nameArticle,
            Long categoryId,
            boolean openAuction,
            boolean currentAuction,
            boolean closedAuction
    );
}
