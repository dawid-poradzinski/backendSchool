package rootekstudio.com.zsebackend.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class WebSecurityConfig {

     private JWTRequestFilter jwtRequestFilter;

    public WebSecurityConfig(JWTRequestFilter jwtRequestFilter) {
        this.jwtRequestFilter = jwtRequestFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().disable();
        http.addFilterBefore(jwtRequestFilter, AuthorizationFilter.class);
        http.authorizeHttpRequests()
        .requestMatchers("/auth/**").permitAll()
        // TODO before creating user. change after to hasAnyAuthority
        // .requestMatchers("/auth/create").hasAnyAuthority("ROLE_ADMIN", "ROLE_WORKER")
        .requestMatchers("/post**").permitAll()
        // .requestMatchers("/post/delete**").hasAnyAuthority("ROLE_ADMIN", "ROLE_WORKER")
        // .requestMatchers("/post/add").hasAnyAuthority("ROLE_ADMIN", "ROLE_WORKER")
        .requestMatchers("/element**").permitAll()
        // .requestMatchers("/element/add").hasAnyAuthority("ROLE_ADMIN", "ROLE_WORKER")
        // .requestMatchers("/element/delete**").hasAnyAuthority("ROLE_ADMIN", "ROLE_WORKER")
        .anyRequest().authenticated();
        return http.build();
    }
}
