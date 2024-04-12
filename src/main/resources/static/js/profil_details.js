$(document).ready(function() {
    /***************** Initialisation *****************/

    /********** EVENEMENTS **********/




    // Lorsque le bouton "Supprimer" est cliqué
    $(document).on("click", "#delete_btn", function(event) {
        // Empêcher le comportement par défaut du bouton
        event.preventDefault();
        let id = $("#user_id").val();
        console.log(id);
        // Demander une confirmation avant de supprimer le profil
        if (confirm("Voulez-vous vraiment supprimer votre profil ?")) {
            $.ajax(
                {
                    type: "GET",
                    url: "/profil/delete",
                    data:{'user_id' :id },
                    success: function() {
                       alert("Profil supprimé avec succès");
                        window.location.href = "/logout";
                    },
                    error: function(data) {
                        alert("Erreur lors de la suppression du profil");
                    }
                }
            )
        }
    });
});
