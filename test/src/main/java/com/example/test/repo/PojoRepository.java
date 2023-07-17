package com.example.test.repo;

import com.example.test.domain.Pojo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA,
 * объектно-реляционное отображение модели предметной области в базе данных
 */
public interface PojoRepository extends JpaRepository <Pojo, Long> {

}
