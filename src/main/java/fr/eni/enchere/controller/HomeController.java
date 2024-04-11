//package fr.eni.enchere.controller;
//
//import fr.eni.enchere.bll.ArticleService;
//import fr.eni.enchere.bll.CategoryService;
//import fr.eni.enchere.bo.Article;
//import fr.eni.enchere.bo.Category;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.util.List;
//
//@Controller
//public class HomeController {
//
//
//    @Autowired
//    private CategoryService categoryService;
//    @Autowired
//    private ArticleService articleService;
//
//    @GetMapping("/")
//    public String home(Model model) {
//        model.addAttribute("articles", articleService.getAllArticles());
//        List<Category> categories = categoryService.getAllCategories();
//        model.addAttribute("categories", categories);
//        return "index";
//    }
//
//    @PostMapping("/")
//    public String filterByCategory(@RequestParam Long categoryId, Model model) {
//        List<Article> filteredArticles = articleService.getArticlesByCategory(categoryId);
//        model.addAttribute("articles", filteredArticles);
//        List<Category> categories = categoryService.getAllCategories();
//        model.addAttribute("categories", categories);
//        return "index";
//    }
//}
