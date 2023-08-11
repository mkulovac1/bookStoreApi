package api.library.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class LibrarySecurityConfig {

    private static final String[] SECURED_URL = {
            "/books/**"
    };

    private static final String[] UNSECURED_URL = {
            "/register/**",
            "/users/**",
            "/books/book/{id}",
            "/books/all",
            "/auth/**"
    };

    @Bean // vazno je da se ovo bean-uje jer ce se koristiti u UserRegistrationService, INJEKTOVANJE
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

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
                .httpBasic()
                .and()
                .build();
    }
}
