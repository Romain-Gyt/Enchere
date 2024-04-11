package fr.eni.enchere.exception;

public class RegisterCode {
    public static final String USER_IS_NULL = "le formulaire est vide";
    public static final String PSEUDO_IS_EMPTY = "le pseudo est vide";
    public static final String PSEUDO_LENGTH = "le pseudo doit contenir entre 3 et 20 caractères";
    public static final String PSEUDO_ALREADY_EXIST = "le pseudo existe déjà";
    public static final String EMAIL_IS_EMPTY = "l'email est vide";
    public static final String EMAIL_FORMAT = "l'email n'est pas valide";
    public static final String EMAIL_ALREADY_EXIST = "l'email existe déjà";
    public static final String PASSWORD_IS_EMPTY = "le mot de passe est vide";
    public static final String PASSWORD_LENGTH = "le mot de passe doit contenir entre 8 et 30 caractères";
    public static final String CURRENT_PASSWORD_UNKNOWN = "Le mot de passe actuel n'est pas reconnu";
    public static final String PASSWORD_CONFIRMATION_IS_EMPTY = "la confirmation du mot de passe est vide";
    public static final String PASSWORD_CONFIRMATION = "les mots de passe ne correspondent pas";
    public static final String FIRST_NAME_IS_EMPTY = "le prénom est vide";
    public static final String FIRST_NAME_LENGTH = "le prénom doit contenir entre 1 et 30 caractères";
    public static final String LAST_NAME_IS_EMPTY = "le nom est vide";
    public static final String LAST_NAME_LENGTH = "le nom doit contenir entre 1 et 30 caractères";
    public static final String STREET_IS_EMPTY = "la rue est vide";
    public static final String STREET_LENGTH = "la rue doit contenir entre 1 et 30 caractères";
    public static final String POSTAL_CODE_IS_EMPTY = "le code postal est vide";
    public static final String POSTAL_CODE_LENGTH = "le code postal doit contenir 5 caractères";
    public static final String CITY_IS_EMPTY = "la ville est vide";
    public static final String CITY_LENGTH = "la ville doit contenir entre 1 et 30 caractères";
    public static final String PHONE_NUMBER_IS_EMPTY = "le numéro de téléphone est vide";
    public static final String PHONE_NUMBER_LENGTH = "le numéro de téléphone doit contenir 10 caractères";
    public static final String PHONE_NUMBER_FORMAT = "le numéro de téléphone n'est pas valide";

    public static final String REGISTER_ERROR = "une erreur est survenue lors de l'inscription";
}
