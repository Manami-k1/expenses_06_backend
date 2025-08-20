package com.expenses_06.demo.controller;

import com.expenses_06.demo.model.Category;
import com.expenses_06.demo.service.CategoryService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}/has-transactions")
    public ResponseEntity<Boolean> hasRelatedTransactions(@PathVariable Long id) {
        boolean hasTransactions = categoryService.hasTransactions(id);
        return ResponseEntity.ok(hasTransactions);
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category saved = categoryService.saveCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

  @PutMapping("/{id}")
public ResponseEntity<Category> updateCategory(
    @PathVariable Long id,
    @RequestBody Category category
) {
    try {
        Category updated = categoryService.updateCategory(id, category);
        return ResponseEntity.ok(updated);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().build();
    }
}


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();  // 204 No Content を返す
    }
}
