package com.expenses_06.demo.service;

import com.expenses_06.demo.model.Category;
import com.expenses_06.demo.repository.CategoryRepository;
import com.expenses_06.demo.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final TransactionRepository transactionRepository;

    private final CategoryRepository categoryRepository;

    private static final Long DEFAULT_CATEGORY_ID = 1L;

    public CategoryService(CategoryRepository categoryRepository, TransactionRepository transactionRepository) {
        this.categoryRepository = categoryRepository;
        this.transactionRepository = transactionRepository;
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void deleteCategory(Long categoryId) {
          if (categoryId.equals(DEFAULT_CATEGORY_ID)) {
            throw new IllegalArgumentException("デフォルトカテゴリは削除できません。");
        }

        // 2. 削除対象カテゴリが存在するか確認
        Category categoryToDelete = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new IllegalArgumentException("カテゴリが存在しません。"));

        // 3. 関連する取引がある場合、カテゴリをデフォルトに置き換える
        transactionRepository.updateCategoryToDefault(categoryId, DEFAULT_CATEGORY_ID);

        // 4. カテゴリを削除
        categoryRepository.delete(categoryToDelete);
    }

    public boolean hasTransactions(Long categoryId) {
        return transactionRepository.existsByCategoryId(categoryId);
    }

    public Category updateCategory(Long id, Category updatedCategory) {
    Category existingCategory = categoryRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("カテゴリが存在しません"));

    existingCategory.setName(updatedCategory.getName());
    existingCategory.setColor(updatedCategory.getColor());

    return categoryRepository.save(existingCategory);
}

}
