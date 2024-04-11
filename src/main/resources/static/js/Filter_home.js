$(document).ready(function() {
    // Définition de la fonction filterByCategory
    function filterByCategory(categoryId) {
        $.ajax({
            url: '/filterByCategory',
            method: 'GET',
            data: { categoryId: categoryId },
            success: function(response) {
                $('.auctions-container').html(response); // Remplace le contenu des cartes avec les nouvelles données
            },
            error: function(xhr, status, error) {
                console.error(error);
            }
        });
    }

    // Définition de la fonction searchAuctions
    function searchAuctions() {
        var input, filter, cards, card, title, i, txtValue;
        input = document.getElementById('searchInput');
        filter = input.value.toUpperCase();
        cards = document.querySelectorAll('.col-md-6');
        for (i = 0; i < cards.length; i++) {
            card = cards[i];
            title = card.querySelector('.card-title');
            txtValue = title.textContent || title.innerText;
            if (txtValue.toUpperCase().indexOf(filter) > -1) {
                card.style.display = '';
            } else {
                card.style.display = 'none';
            }
        }

        // Réinitialiser l'affichage si la barre de recherche est vide
        if (filter === "") {
            cards.forEach(function(card) {
                card.style.display = '';
            });
        }
    }

    // Gestionnaire d'événement pour le changement de catégorie
    $('#categorySelect').on('change', function() {
        var categoryId = $(this).val();
        filterByCategory(categoryId);
    });

    // Gestionnaire d'événement pour la recherche d'enchères
    $('#searchInput').on('keyup', function() {
        searchAuctions();
    });
});
