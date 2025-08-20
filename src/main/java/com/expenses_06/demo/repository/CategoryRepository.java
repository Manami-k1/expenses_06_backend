package com.expenses_06.demo.repository;

import com.expenses_06.demo.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // 何も追加しなくてOK
}
