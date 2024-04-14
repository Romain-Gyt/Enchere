package fr.eni.enchere.controller;

import fr.eni.enchere.bll.ArticleService;
import fr.eni.enchere.bo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class HomeController {

  /**************** Declaration **************/
    private ArticleService articleService;

    /******** Constructor ********/
    public HomeController(ArticleService articleService) {
        this.articleService = articleService;
    }


    /******** Routes **********/
    @GetMapping("/")
    public String home(Model model) {
        List<Article> articles = articleService.getAllArticles();
        model.addAttribute("articles", articles);
        return "index.html";
    }

}
