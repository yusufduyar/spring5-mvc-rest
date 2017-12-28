package com.springfw.spring5mvcrest.repositories;

import com.springfw.spring5mvcrest.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);
}
