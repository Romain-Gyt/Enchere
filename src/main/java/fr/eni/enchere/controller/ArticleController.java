package fr.eni.enchere.controller;

import fr.eni.enchere.bll.ArticleService;
import fr.eni.enchere.bo.Article;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("memberSession")
public class ArticleController {
    /******** Declaration ********/
    ArticleService articleService;

    /********** Constructor ********/
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    /*********ROUTES*********/

    @GetMapping("/article")
    public String getArticle(
            @RequestParam("idArticle") Long idArticle,
            Model model
    ) {
       Article article = articleService.getArticleById(idArticle);
        model.addAttribute("article", article);
        System.out.println(article.getAuctions());
        System.out.println(article.getAuctions());
        System.out.println(article.getUser());
        return "index.html";
    }
}
