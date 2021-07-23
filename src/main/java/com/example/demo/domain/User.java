package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table (name = "sarafan_user")
// маняем название, т.к. нельзя использовать зарезервированные системой имена
// в названиях таблиц и столбцов в базах данных
@Data
public class User { // пока что не нужно implements Serializable т.к. пока что пользуемся контейнером OAuth2User
    @Id
    private String id;
    private String name;
    private String email;
    private String locale;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastVisit;
}
