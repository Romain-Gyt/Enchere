$(document).ready(function() {
    /***************** Initialisation *****************/

    /********** EVENEMENTS **********/




    // Lorsque le bouton "Supprimer" est cliqué
    $(document).on("click", "#delete_btn", function(event) {
        // Empêcher le comportement par défaut du bouton
        event.preventDefault();
        var id = $("#user_id").val();
        // Demander une confirmation avant de supprimer le profil
        if (confirm("Voulez-vous vraiment supprimer votre profil ?")) {
            $.ajax(
                {
                    type: "GET",
                    url: "/profil/delete",
                    data:id,
                    success: function() {
                        // Rediriger l'utilisateur vers la page de connexion
                    },
                    error: function(data) {
                        alert("Erreur lors de la suppression du profil");
                    }
                }
            )
        }
    });
});
