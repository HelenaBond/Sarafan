package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import java.util.Arrays;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(ar -> ar
                    .antMatchers("/", "/login**", "/js/**", "/error**").permitAll() // сюда можно зайти без аутентификации
                    .anyRequest().authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable) //отключаем csrf чтобы не углублятся в защиту от атак на фронте
                .oauth2Login(ln -> ln
                    .userInfoEndpoint(p -> p //аутентификация через Oauth2 протокол.
                    .userService(this.myOAuth2UserService())
                    )
                )
                .logout(l -> l
                        .logoutSuccessUrl("/").permitAll()
                )
                .oauth2Login(ln -> ln
                    .userInfoEndpoint(p ->p //аутентификация через OpenId протокол.
                    .oidcUserService(this.myOidcUserService()))
                );
    }

    @Bean
    public MyDelegatingOAuth2UserService myOAuth2UserService() {
        return  new MyDelegatingOAuth2UserService(Arrays.asList(new DefaultOAuth2UserService()));
    }

    @Bean
    public MyDelegatingOAuth2UserService myOidcUserService() {
        return new MyDelegatingOAuth2UserService(Arrays.asList(new OidcUserService()));
    }
}
