$(document).ready(function() {

    $(document).on("click", "#btn-delete-admin", function(event) {
        // Empêcher le comportement par défaut du bouton
        event.preventDefault();
        let id = $("#user_id").val();
        console.log(id);
        // Demander une confirmation avant de supprimer le profil
        if (confirm("Voulez-vous vraiment supprimer votre profil ?")) {
            $.ajax(
                {
                    type: "GET",
                    url: "/admin/delete",
                    data:{'user_id' :id },
                    success: function() {
                        alert("Profil supprimé avec succès");
                        window.location.href = "/admin";
                    },
                    error: function(data) {
                        alert("Erreur lors de la suppression du profil");
                    }
                }
            )
        }
    });

    $(document).on("click", "#disabled-btn", function(event) {
        // Empêcher le comportement par défaut du bouton
        event.preventDefault();
        let id = $("#user_id").val();
        console.log(id);
        // Demander une confirmation avant de supprimer le profil
        if (confirm("Voulez-vous vraiment desactiver le profil ?")) {
            $.ajax(
                {
                    type: "GET",
                    url: "/admin/disable",
                    data:{'user_id' :id },
                    success: function() {
                        alert("Profil desactivé avec succès");
                        window.location.href = "/profil/" + id;
                    },
                    error: function(data) {
                        alert("Erreur lors de la desactivation du profil");
                    }
                }
            )
        }
    });

    $(document).on("click", "#enable-btn", function(event) {
        // Empêcher le comportement par défaut du bouton
        event.preventDefault();
        let id = $("#user_id").val();
        console.log(id);
        // Demander une confirmation avant de supprimer le profil
        if (confirm("Voulez-vous vraiment réactiver le profil ?")) {
            $.ajax(
                {
                    type: "GET",
                    url: "/admin/enable",
                    data:{'user_id' :id },
                    success: function() {
                        alert("Profil reactivé avec succès");
                        window.location.href = "/profil/" + id;
                    },
                    error: function(data) {
                        alert("Erreur lors de la reactivation du profil");
                    }
                }
            )
        }
    });
});