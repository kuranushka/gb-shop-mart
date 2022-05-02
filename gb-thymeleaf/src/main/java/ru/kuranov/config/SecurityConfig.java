package ru.kuranov.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(4);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(
                (requests) -> {
                    requests.antMatchers("/app/products/registration").permitAll();
                    requests.antMatchers("/login").permitAll();
                    requests.antMatchers("/logout").permitAll();
                    requests.antMatchers("/app/products/addtocart").hasAnyAuthority("ADMIN", "USER", "MANAGER");
                    requests.antMatchers("/app/users").hasAuthority("ADMIN");
                    requests.antMatchers("/app/products").permitAll();
                    requests.antMatchers("/app/products/**").permitAll();
                    requests.antMatchers("/app/products/cart").permitAll();
                }
        );

        http.authorizeRequests((requests) -> ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl) requests.anyRequest()).authenticated());
        http
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/user-login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/app/products");

        http
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/app/products");
        http.httpBasic();
    }
}

