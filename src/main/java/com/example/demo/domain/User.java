package com.example.demo.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table (name = "SarafanUser")
// маняем название, т.к. нельзя использовать зарезервированные системой имена
// в названиях таблиц и столбцов в базах данных
@Data
public class User {
    @Id
    private String id;
    private String name;
    private String email;
    private String locale;
    private LocalDateTime lastVisit;
}
