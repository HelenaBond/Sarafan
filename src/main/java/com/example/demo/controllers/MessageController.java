package com.example.demo.controllers;

import com.example.demo.domain.Message;
import com.example.demo.repository.MessageRepo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //возвращает пользователю не представление, а данные
@RequestMapping("message") //путь для всех мапингов до их собственных путей
public class MessageController {
    private final MessageRepo messageRepo;

    @Autowired
    public MessageController(MessageRepo messageRepo) {
        this.messageRepo = messageRepo;
    }

    @GetMapping
    public List<Message> list() {
        return messageRepo.findAll();
    }

    @GetMapping("{id}") //такой способ использования аннотации @PathVariable говорит спрингу вытаскивать сообщение из базы данных в модель.
    public Message getOne(@PathVariable("id") Message messageFromDb) {
        return messageFromDb;
    }

    @PostMapping
    public Message create(@RequestBody Message message) {
        return messageRepo.save(message);
    }

    @RequestMapping(value = "{id}", method=RequestMethod.PUT)
    public Message update(@PathVariable("id") Message messageFromDb, @RequestBody Message message) {
        BeanUtils.copyProperties(message, messageFromDb, "id");
        return messageRepo.save(messageFromDb);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Message message) {
        messageRepo.delete(message);
    }
}
