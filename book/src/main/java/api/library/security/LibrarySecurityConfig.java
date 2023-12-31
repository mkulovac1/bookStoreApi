package api.library.security;

import api.library.security.jwt.JWTAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class LibrarySecurityConfig {

    private static final String[] SECURED_URL = {
            // no urls here cause we are testing with postman
    };

    private static final String[] UNSECURED_URL = {
            "/register/**",
            "/users/**",
            "/books/book/{id}",
            "/books/all",
            "/auth/**",
            "/roles/**",
            "/books/**"
    };

    private final UserRegistrationDetailsService userDetailsService;
    private final JWTAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /* return http.cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/register/**")
                .permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/users/**", "/books/**")
                .hasAnyAuthority("ADMIN") // "USER" NE IDE
                .and()
                .formLogin()
                .and()
                .build();
         */

        return http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(UNSECURED_URL)
                .permitAll()
                .and()
                .authorizeHttpRequests()
                .requestMatchers(SECURED_URL)
                .hasAnyAuthority("ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()// dodano
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

                /* .httpBasic()
                .and()
                .build(); */
    }

    @Bean // important to use bean annotation cause you inject it in other classes
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}
