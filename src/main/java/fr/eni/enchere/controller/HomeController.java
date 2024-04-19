package fr.eni.enchere.controller;

import fr.eni.enchere.bll.ArticleService;
import fr.eni.enchere.bll.CategoryService;
import fr.eni.enchere.bo.Article;
import fr.eni.enchere.bo.Category;
import fr.eni.enchere.bo.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/search")
    public String filterSearch(
            HttpSession session,
            @RequestParam(value="searchInput", required = false,defaultValue = "") String searchInput,
            @RequestParam(value="category_id", required = false,defaultValue = "0") String category_id,
            @RequestParam(value="transactionType", required = false,defaultValue = "false") String buying,
            @RequestParam(value="transactionType", required = false,defaultValue = "false") String selling,
            @RequestParam(value="open_auction", required = false,defaultValue = "false") String openAuction,
            @RequestParam(value="won_auction", required = false,defaultValue = "false") String closedAuction,
            @RequestParam(value="current_auction", required = false,defaultValue = "false") String currentAuction,
            @RequestParam(value="current_selling", required = false,defaultValue = "false") String currentSelling,
            @RequestParam(value="non_started_selling", required = false,defaultValue = "false") String non_started_selling,
            @RequestParam(value="finished_selling", required = false,defaultValue = "false") String finished_selling,
            Model model

    ) {
        List<Article> articles = null;
        if(buying.equals("false") && selling.equals("false")) {
            articles = articleService.getAllArticleByNameAndCategory(searchInput, Long.parseLong(category_id));
        }
        User userSession = (User) session.getAttribute("memberSession");
        if(userSession != null) {
            System.out.println(buying);
            if(buying.toLowerCase().equals("buying") ) {
                articles = articleService.getArticlesByBuying(
                        userSession,
                        searchInput,
                        Long.parseLong(category_id),
                        Boolean.parseBoolean(openAuction),
                        Boolean.parseBoolean(currentAuction),
                        Boolean.parseBoolean(closedAuction));
            }
            System.out.println(selling);
            if(selling.toLowerCase().equals("selling")) {
                System.out.println(non_started_selling);
                articles = articleService.getArticlesBySelling(
                        userSession,
                        searchInput,
                        Long.parseLong(category_id),
                        Boolean.parseBoolean(currentSelling),
                        Boolean.parseBoolean(non_started_selling),
                        Boolean.parseBoolean(finished_selling));
            }
            if (articles.isEmpty()) {
                articles = articleService.getAllArticleByNameAndCategory(searchInput, Long.parseLong(category_id));
            }
        }
        
        List<Category> categories = categoryService.getCategoryByIdFilter(Long.parseLong(category_id));
        model.addAttribute("articles", articles);
        model.addAttribute("categories", categories);
        return "index.html";
    }



}
