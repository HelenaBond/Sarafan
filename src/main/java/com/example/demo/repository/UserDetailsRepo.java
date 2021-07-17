package com.example.demo.repository;

import com.example.demo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepo extends JpaRepository<User, String> {
    // обязательно указываем дженерики. Они дадут знать другим классам что наш интерфейс
    // обслуживает класс User с айдишником типа String. Иначе другие классы будут ждать Object
}
