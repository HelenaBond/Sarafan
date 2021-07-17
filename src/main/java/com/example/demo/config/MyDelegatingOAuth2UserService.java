package com.example.demo.config;

import com.example.demo.domain.User;
import com.example.demo.repository.UserDetailsRepo;
import lombok.Data;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DelegatingOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.time.LocalDateTime;
import java.util.List;

public class MyDelegatingOAuth2UserService extends DelegatingOAuth2UserService {

    private UserDetailsRepo userDetailsRepo;

    @Autowired
    public void setUserDetailsRepo(UserDetailsRepo userDetailsRepo) {
        this.userDetailsRepo = userDetailsRepo;
    }

    public MyDelegatingOAuth2UserService(List list) {
        super(list);
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        String id = oAuth2User.getAttribute("sub") == null ? oAuth2User.getAttribute("id").toString() : oAuth2User.getAttribute("sub");
        User user = userDetailsRepo.findById(id).orElseGet(() -> {
            User newUser = new User(); // не нарушаем solid т.к. логика метода заключается в создании нового юзера и работы над ним
            newUser.setId(id);
            newUser.setName(oAuth2User.getAttribute("name"));
            newUser.setEmail(oAuth2User.getAttribute("email"));
            newUser.setLocale(oAuth2User.getAttribute("locate"));
            return newUser;
                });
        user.setLastVisit(LocalDateTime.now());
        userDetailsRepo.save(user);
        return oAuth2User;
    }
}
