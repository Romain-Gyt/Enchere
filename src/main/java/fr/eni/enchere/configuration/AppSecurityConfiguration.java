package fr.eni.enchere.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
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

@Configuration
@EnableWebSecurity
public class AppSecurityConfiguration  {


    @Autowired
    private  DataSource dataSource;
    @Autowired
    private UserDetailsService userDetailsService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(auth -> {

            auth
                    .requestMatchers(HttpMethod.GET,"display_profil").authenticated()
                    .requestMatchers(HttpMethod.POST,"display_profil").authenticated();

            auth.requestMatchers("/").permitAll();
            auth.requestMatchers("/css/*").permitAll();
            auth.requestMatchers("/images/*").permitAll();
            auth.anyRequest().authenticated();
        });

        http.formLogin(form -> {
            form.loginPage("/login").permitAll();
            form.defaultSuccessUrl("/session");
            form.failureUrl("/login?error=true");
        });

        http
                .rememberMe()
                .rememberMeParameter("remember_me")
                .tokenValiditySeconds(86400)
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(60*60*24); // 24 hours

        http.logout(logout ->
                logout
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/")
                        .permitAll()
        );
        return http.build();
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        String sqlUsername = "SELECT username, password, 1 FROM users WHERE username = ?;";
        String sqlAuthorities = "SELECT username, ROLE FROM users INNER JOIN ROLES ON administrator = IS_ADMIN WHERE username = ?";
        jdbcUserDetailsManager.setUsersByUsernameQuery(sqlUsername);
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(sqlAuthorities);

        return jdbcUserDetailsManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Configure BCryptPasswordEncoder
    }
}
