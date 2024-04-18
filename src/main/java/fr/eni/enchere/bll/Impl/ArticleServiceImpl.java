    package fr.eni.enchere.bll.Impl;
    import fr.eni.enchere.bll.ArticleService;
    import fr.eni.enchere.bo.Article;
    import fr.eni.enchere.bo.Auction;
    import fr.eni.enchere.bo.Withdrawals;
    import fr.eni.enchere.dal.ArticleDAO;
    import fr.eni.enchere.dal.AuctionDAO;
    import fr.eni.enchere.dal.WithdrawalsDAO;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.jdbc.core.JdbcTemplate;
    import org.springframework.stereotype.Service;

    import java.time.LocalDate;
    import java.util.Comparator;
    import java.util.List;

    @Service
    public class ArticleServiceImpl implements ArticleService {

/******** Declaration ********/
    private ArticleDAO articleDAO;
    private AuctionDAO auctionDAO;
     private WithdrawalsDAO withdrawalsDAO;

        /******** Declaration ********/

        /********** Constructor ********/
    public ArticleServiceImpl(
            ArticleDAO articleDAO,
            AuctionDAO auctionDAO,
          WithdrawalsDAO withdrawalsDAO
    ) {
        this.auctionDAO = auctionDAO;
        this.articleDAO = articleDAO;
        this.withdrawalsDAO = withdrawalsDAO;
    }
      
        /******** Methods ********/

        @Override
        public void setAuctions(Article article) {
            List<Auction> auctions = auctionDAO.getAuctionsByArticleId(article.getItemId());
            article.setAuctions(auctions);
        }

        @Override
        public void updateArticle(Article article, Withdrawals withdrawals) {

            articleDAO.updateArticle(article);
            withdrawalsDAO.updateWithdrawals(withdrawals);
        }

        @Override
        public void createArticle(Article article, Withdrawals withdrawals) {

          int itemId = articleDAO.createArticle(article);
            withdrawals.setItem_id(itemId);
            System.out.println(itemId);
            withdrawalsDAO.createWithdrawals(withdrawals);
        }

        @Override
        public void insertBidAmountById(Long userId, int id, int bid_amount) {
            auctionDAO.create(userId, id, bid_amount);
        }

        @Override
        public List<Article> getAllArticles() {
            List<Article> articles = articleDAO.getAllArticles();
            LocalDate now = LocalDate.now();

    @Override
    public Article getArticleById(Long idArticle) {
        Article article = articleDAO.getArticleById(idArticle);
//        List<Auction> auctions = auctionDAO.getAllAuctions(article.getItemId());
//        article.setAuctions(auctions);
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

    @Override
    public void deleteArticle(Long idArticle) {
        articleDAO.deleteArticle(idArticle);
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

            for (Article article : articles) {
                List<Auction> auctions = auctionDAO.getAuctionsByArticleId(article.getItemId());
                article.setAuctions(auctions);

                if (!auctions.isEmpty()) {
                    LocalDate startAuctionDate = article.getStartAuctionDate().toLocalDate();
                    LocalDate endAuctionDate = article.getEndAuctionDate().toLocalDate();

                    if (now.isAfter(endAuctionDate)) {
                        article.setStatus("Enchères terminées");
                    } else if (now.isAfter(startAuctionDate)) {
                        article.setStatus("En cours");
                    } else {
                        article.setStatus("Créée");
                    }
                }
            }
            return articles;
        }

        @Override
        public Article getArticleById(int itemId) {
            Article article = articleDAO.getArticleById(itemId);
            if (article != null) {
                List<Auction> auctions = auctionDAO.getAuctionsByArticleId(itemId);
                article.setAuctions(auctions);

                Withdrawals withdrawals = withdrawalsDAO.getWithdrawalsByArticleId(itemId);
                article.setWithdrawals(withdrawals);

                if (auctions != null && !auctions.isEmpty()) {
                    // Trouver l'offre la plus récente
                    Auction latestAuction = auctions.stream()
                            .max(Comparator.comparing(Auction::getBidDate))
                            .orElse(null);

                    if (latestAuction != null) {
                        int latestBidAmount = latestAuction.getBidAmount();
                        article.setLatestBidAmount(latestBidAmount);
                    }
                }

                LocalDate now = LocalDate.now();
                LocalDate startAuctionDate = article.getStartAuctionDate().toLocalDate();
                LocalDate endAuctionDate = article.getEndAuctionDate().toLocalDate();

                if (now.isAfter(endAuctionDate)) {
                    article.setStatus("Enchères terminées");
                } else if (now.isBefore(startAuctionDate)) {
                    article.setStatus("En cours");
                } else {
                    article.setStatus("Créée");
                }
            }
            return article;
        }

        @Override
        public List<Article> getArticlesByCategory(long categoryId) {
            return articleDAO.getArticlesByCategory(categoryId);
        }

    }
