package fr.eni.enchere.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

/**
 * Classe de configuration de sécurité pour l'application.
 * Cette classe est utilisée pour configurer les détails de l'authentification et de l'autorisation pour l'application.
 */
@Configuration
@EnableWebSecurity
public class AppSecurityConfiguration  {



    @Autowired
    private  DataSource dataSource; //Source de données pour la persistance des jetons de rappel
    @Autowired
    private UserDetailsService userDetailsService; //Service pour la gestion des utilisateurs


    /**
     * Configure la chaîne de filtres de sécurité pour l'application.
     * Cette méthode définit les règles d'autorisation pour différentes URL dans l'application,
     * configure la page de connexion et la page de succès par défaut, et configure la fonctionnalité "se souvenir de moi".
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(auth -> {
            auth
                    .requestMatchers(HttpMethod.GET,"/register").permitAll()
                    .requestMatchers(HttpMethod.POST,"/register").permitAll()
                    .requestMatchers(HttpMethod.GET,"/profil").authenticated()
                    .requestMatchers(HttpMethod.POST,"/profil").authenticated()
                    .requestMatchers(HttpMethod.GET,"/profil/{id}").authenticated()
                    .requestMatchers(HttpMethod.GET,"/profil/details").authenticated()
                    .requestMatchers(HttpMethod.POST,"/profil/details").authenticated()
                    .requestMatchers(HttpMethod.GET,"/profil/delete").authenticated()
                    .requestMatchers(HttpMethod.GET,"/category").authenticated()
                    .requestMatchers(HttpMethod.GET,"/search").permitAll();


            auth.requestMatchers("/").permitAll();
            auth.requestMatchers("/css/*").permitAll();
            auth.requestMatchers("/images/*").permitAll();
            auth.requestMatchers("/js/*").permitAll();
            auth.anyRequest().authenticated();

        });
        http.formLogin(form -> {
            form.loginPage("/login").permitAll();
            form.defaultSuccessUrl("/session");
            form.failureUrl("/login?error=true");
        });

//        gestion du de la méthode se souvenir de moi
        http
                .rememberMe(rememberMe -> rememberMe
                        .tokenRepository(persistentTokenRepository())
                        .tokenValiditySeconds(86400)
                        .rememberMeParameter("remember_me")
                );

        http.logout(logout ->
                logout
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/")
                        .permitAll()
        );

        http.sessionManagement(sessionManagement ->
                sessionManagement
                        .invalidSessionUrl("/logout")
                        .sessionFixation(sessionFixation -> sessionFixation.none())
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                        .maximumSessions(1)
                        .expiredUrl("/")
        );

        return http.build();
    }

    /**
     * Crée un référentiel pour stocker les tokens de "se souvenir de moi".
     * Ce référentiel est utilisé pour stocker les tokens entre les sessions.
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

    /**
     * Configure le gestionnaire d'authentification global pour l'application.
     * Cette méthode définit le service qui sera utilisé pour charger les détails de l'utilisateur lors de l'authentification,
     * et l'encodeur de mot de passe qui sera utilisé pour comparer le mot de passe fourni par l'utilisateur avec le mot de passe stocké.
     */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * Crée un gestionnaire de détails d'utilisateur pour l'application.
     * Cette méthode configure le gestionnaire pour utiliser JDBC pour charger les détails de l'utilisateur à partir de la base de données,
     * et charge les rôles attribués à l'utilisateur.
     */
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        String sqlUsername = "SELECT username, password, 1 FROM users WHERE username = ?;";
        String sqlAuthorities = "SELECT username, ROLE FROM users INNER JOIN ROLES ON administrator = IS_ADMIN WHERE username = ?";
        jdbcUserDetailsManager.setUsersByUsernameQuery(sqlUsername);
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(sqlAuthorities);

        return jdbcUserDetailsManager;
    }

    /**
     * Crée un encodeur de mot de passe pour l'application.
     * Cette méthode retourne une instance de BCryptPasswordEncoder, qui est une implémentation d'encodeur de mot de passe qui utilise le hachage BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}