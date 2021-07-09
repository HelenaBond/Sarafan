package com.example.demo.domain;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table
@Data
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String text;
}
