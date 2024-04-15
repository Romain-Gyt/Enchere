package fr.eni.enchere.controller;

import fr.eni.enchere.bll.ArticleService;
import fr.eni.enchere.bll.CategoryService;
import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Category;
import fr.eni.enchere.bo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller
@SessionAttributes({"memberSession"})
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

    @GetMapping("/search")
    public String filterSearch(
            @ModelAttribute("memberSession") User userSession,
            @RequestParam(value="searchInput", required = false,defaultValue = "false") String searchInput,
            @RequestParam(value="category_id", required = false,defaultValue = "false") String category_id,
            @RequestParam(value="buying", required = false,defaultValue = "false") String buying,
            @RequestParam(value="selling", required = false,defaultValue = "false") String selling,
            @RequestParam(value="open_auction", required = false,defaultValue = "false") String openAuction,
            @RequestParam(value="won_auction", required = false,defaultValue = "false") String closedAuction,
            @RequestParam(value="current_auction", required = false,defaultValue = "false") String currentAuction,
            @RequestParam(value="current_selling", required = false,defaultValue = "false") String currentSelling,
            @RequestParam(value="non_started_selling", required = false,defaultValue = "false") String non_started_selling,
            @RequestParam(value="finished_selling", required = false,defaultValue = "false") String finished_selling,
            Model model

    ) {
        List<Article> articles = null;
        if(buying.toLowerCase().equals("false") && selling.toLowerCase().equals("false")) {
            articles = articleService.getAllArticleByNameAndCategory(searchInput, Long.parseLong(category_id));
        }

        if(buying.toLowerCase().equals("true") ) {
           articles = articleService.getArticlesByBuying(
                   userSession,
                   searchInput,
                   Long.parseLong(category_id),
                   Boolean.parseBoolean(openAuction),
                   Boolean.parseBoolean(currentAuction),
                   Boolean.parseBoolean(closedAuction));
        }
        if(selling.toLowerCase().equals("true")) {
            articles = articleService.getArticlesBySelling(
                    userSession,
                    searchInput,
                    Long.parseLong(category_id),
                    Boolean.parseBoolean(currentSelling),
                    Boolean.parseBoolean(non_started_selling),
                    Boolean.parseBoolean(finished_selling));
        }
        model.addAttribute("articles", articles);

            return "index.html";
    }

}
