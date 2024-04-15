    package fr.eni.enchere.bll.Impl;

    import fr.eni.enchere.bll.ArticleService;
    import fr.eni.enchere.bo.Article;
    import fr.eni.enchere.bo.Auction;
    import fr.eni.enchere.dal.ArticleDAO;
    import fr.eni.enchere.dal.AuctionDAO;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.jdbc.core.JdbcTemplate;
    import org.springframework.stereotype.Service;

    import java.time.LocalDate;
    import java.util.Comparator;
    import java.util.List;

    @Service
    public class ArticleServiceImpl implements ArticleService {

        /******** Declaration ********/

        @Autowired
        private ArticleDAO articleDAO;

        @Autowired
        private AuctionDAO auctionDAO;

        @Autowired
        private JdbcTemplate jdbcTemplate;

        /********** Constructor ********/

        public ArticleServiceImpl(ArticleDAO articleDAO, AuctionDAO auctionDAO) {
            this.articleDAO = articleDAO;
            this.auctionDAO = auctionDAO;
        }

        /******** Methods ********/

        @Override
        public void setAuctions(Article article) {
            List<Auction> auctions = auctionDAO.getAuctionsByItemId(article.getItemId());
            article.setAuctions(auctions);
        }

        @Override
        public void createArticle(Article article) {
            articleDAO.createArticle(article);
        }

        @Override
        public List<Article> getAllArticles() {
            List<Article> articles = articleDAO.getAllArticles();
            LocalDate now = LocalDate.now();

            for (Article article : articles) {
                List<Auction> auctions = auctionDAO.getAuctionsByItemId(article.getItemId());
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
                List<Auction> auctions = auctionDAO.getAuctionsByItemId(itemId);
                article.setAuctions(auctions);

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
