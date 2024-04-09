$(document).ready(function() {

    /********** EVENEMENTS **********/
    // Lorsque le bouton "Modifier" est cliqué
    $("#modify_btn").click(function(event) {
        // Vérifier si le type actuel du bouton est "button"
        if ($(this).attr("type") === "button") {
            // Empêcher le comportement par défaut du bouton
            event.preventDefault();

            // Modifier le type du bouton en "submit"
            $(this).attr("type", "submit");
            $(this).text("Enregistrer");

            // Modifier l'action du formulaire pour le rendre soumis
            $("#form_profil").attr("action", "/profil/details");

            // Rendre tous les champs du formulaire modifiables
            $("#form_profil input").removeAttr("disabled");
        }
    });
});
