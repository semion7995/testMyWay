package com.example.test.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Объект предметной области, сущность репозитория
 * для компактности использована библиотека lombok
 */
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Pojo {
    @Id
    @GeneratedValue
    private Long id;
    private String count;

    public Pojo(String count) {
        this.count = count;
    }
}
