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
        .requestMatchers("/auth/create").hasAnyAuthority("ROLE_ADMIN")
        .requestMatchers("/auth/admin/**").hasAnyAuthority("ROLE_ADMIN")
        .requestMatchers("/auth/me/***").hasAnyAuthority("ROLE_USER","ROLE_ADMIN","ROLE_WORKER")
        .requestMatchers("/auth/**").permitAll()
        .requestMatchers("/post/delete/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_WORKER")
        .requestMatchers("/post/add").hasAnyAuthority("ROLE_ADMIN", "ROLE_WORKER")
        .requestMatchers("/post/change").hasAnyAuthority("ROLE_ADMIN", "ROLE_WORKER")
        .requestMatchers("/post/**").permitAll()
        .requestMatchers("/element/add").hasAnyAuthority("ROLE_ADMIN", "ROLE_WORKER")
        .requestMatchers("/element/delete**").hasAnyAuthority("ROLE_ADMIN", "ROLE_WORKER")
        .requestMatchers("/element/**").permitAll()
        .requestMatchers("/page/gete/**").permitAll()
        .requestMatchers("/page/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_WORKER")
        .requestMatchers("upload-dir/**").permitAll()
        .anyRequest().authenticated();
        return http.build();
    }
}
