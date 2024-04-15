package fr.eni.enchere.controller;

import fr.eni.enchere.bll.ArticleService;
import fr.eni.enchere.bll.CategoryService;
import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class HomeController {

  /**************** Declaration **************/
    private ArticleService articleService;
    private CategoryService categoryService;

    /******** Constructor ********/
    public HomeController(
            ArticleService articleService,
            CategoryService categoryService
    ) {
        this.articleService = articleService;
        this.categoryService = categoryService;
    }


    /******** Routes **********/
    @GetMapping("/")
    public String home(Model model) {
        List<Article> articles = articleService.getAllArticles();
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("articles", articles);
        return "index.html";
    }

    @GetMapping("/category")
    public String category(Model model,
                           @RequestParam String category_id
    ) {
        List<Article> articles = null;
        if(!category_id.isEmpty()){
             articles = articleService.getArticlesByCategory(Long.parseLong(category_id));
        } else {
           articles = articleService.getAllArticles();
        }
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("articles", articles);
        System.out.println(category_id);
        System.out.println(articles);

        return "index.html";
    }

}
