package com.example.demo.controllers;

import com.example.demo.exceptions.NotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController //возвращает пользователю не представление, а данные
@RequestMapping("message") //путь для всех мапингов до их собственных путей
public class MessageController {
    // заглушка базы данных для отладки кода
    private List<Map<String, String>> messages = new ArrayList<Map<String, String>>() {{
        add(new HashMap<String, String>() {{put("id", "1"); put("text", "First");}});
        add(new HashMap<String, String>() {{put("id", "2"); put("text", "Second");}});
        add(new HashMap<String, String>() {{put("id", "3"); put("text", "Third");}});
    }};
    private int counter = 4;

    @GetMapping
    public List<Map<String, String>> list() {
        return messages;
    }

    @GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable String id) {
        return getMessage(id); //меняем ошибку 500 на 404
    }

    private Map<String, String> getMessage(String id) {
        return messages.stream()
                .filter(m -> m.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Map<String, String> create(@RequestBody Map<String, String> message) {
        message.put("id", String.valueOf(counter++));
        messages.add(message);
        return message;
    }

    @RequestMapping(value = "{id}", method=RequestMethod.PUT)
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> message) {
        Map<String, String> messageFromDb = getMessage(id);
        messageFromDb.putAll(message);
        messageFromDb.put("id", id);
        return messageFromDb;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable String id) {
        messages.remove(getMessage(id));
    }
}
