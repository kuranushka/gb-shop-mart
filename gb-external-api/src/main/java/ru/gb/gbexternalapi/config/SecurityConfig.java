package ru.gb.gbexternalapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_ENDPOINT = "/api/v1/auth/login";
    private static final String USER_ENDPOINT = "/api/v1/user";

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(
                (requests) -> {
                    requests.antMatchers("/product/all").permitAll();
                    requests.antMatchers("/product/{productId}}").permitAll();
                    requests.antMatchers(LOGIN_ENDPOINT).permitAll();
                    requests.antMatchers(USER_ENDPOINT).permitAll();
                    requests.anyRequest().authenticated();
                }
        );

        http.csrf().disable();
        http.httpBasic().disable();
    }


}

