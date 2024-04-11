$(document).ready(function() {
    // Fonction pour filtrer les cartes en fonction de la catégorie sélectionnée
    function filterCards(categoryId) {
        $('.col-md-6').each(function() {
            let card = $(this);
            let cardCategoryId = card.attr('data-category-id');
            if (categoryId === '' || cardCategoryId === categoryId) {
                card.show();
            } else {
                card.hide();
            }
        });
    }

    // Gestionnaire d'événement pour le changement de sélection de catégorie
    $('#categorySelect').on('change', function() {
        let categoryId = $(this).val();
        filterCards(categoryId);
    });

    // Fonction pour filtrer les cartes en fonction du texte de recherche
    function searchAuctions() {
        let input, filter, cards, card, title, i, txtValue;
        input = $('#searchInput').val().toUpperCase();
        cards = $('.col-md-6');
        for (i = 0; i < cards.length; i++) {
            card = $(cards[i]);
            title = card.find('.card-title');
            txtValue = title.text().toUpperCase();
            if (txtValue.indexOf(input) > -1) {
                card.show();
            } else {
                card.hide();
            }
        }
    }

    // Gestionnaire d'événement pour la recherche d'enchères
    $('#searchInput').on('keyup', function() {
        searchAuctions();
    });

    // Appel initial pour afficher les cartes en fonction de la catégorie sélectionnée
    let initialCategoryId = $('#categorySelect').val();
    filterCards(initialCategoryId);
});