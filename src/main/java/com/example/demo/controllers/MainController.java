package com.example.demo.controllers;

import com.example.demo.repository.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@RequestMapping("/")
public class MainController {

    private final MessageRepo messageRepo;

    @Value("${spring.profiles.active}") // запрашиваем проперти из окружения
    private String profile;

    @Autowired
    public MainController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal OAuth2User user) { //в параметрах мы ожидаем модель, чтобы заполнить её в контроллере
        HashMap<Object, Object> data = new HashMap<>();
        if (user != null) { // спрячет глобальную переменную от неавторизованного пользователя
            data.put("usName", user.getAttribute("name")); // из OAuth2User берем только имя, чтоб не отправлять всю инфу из profile соц.сети на фронт
            data.put("messages", messageRepo.findAll());
        }
        model.addAttribute("frontendData", data);
        model.addAttribute("isDevMode", "dev".equals(profile));
        return "index"; // т.к. это обычный контроллер - мы возвращаем имя шаблона.
    }
}
