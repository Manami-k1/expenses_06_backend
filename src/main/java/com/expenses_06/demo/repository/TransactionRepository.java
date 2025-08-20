package com.expenses_06.demo.repository;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.expenses_06.demo.model.Transaction;

// @Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByUserIdAndDateBetween(Long userId, LocalDate start, LocalDate end);
//  @Query("SELECT COALESCE(SUM(t.price), 0) FROM Transaction t WHERE t.user.id = :userId AND t.date = :date AND t.transactionType = :type")
//     int sumAmountByUserIdAndDateAndType(
//         @Param("userId") Long userId,
//         @Param("date") LocalDate date,
//         @Param("type") String type);
@Query("SELECT COALESCE(SUM(t.price), 0) FROM Transaction t WHERE t.userId = :userId AND t.date BETWEEN :startDate AND :endDate AND t.type = :type")
int sumAmountByUserIdAndDateAndType(
    @Param("userId") Long userId,
    @Param("startDate") LocalDate startDate,
    @Param("endDate") LocalDate endDate,
    @Param("type") String type);

        boolean existsByCategoryId(Long categoryId);

    // ✅ カテゴリをデフォルトに置き換える
    @Modifying
    @Transactional
    @Query("UPDATE Transaction t SET t.category.id = :defaultCategoryId WHERE t.category.id = :categoryId")
    void updateCategoryToDefault(@Param("categoryId") Long categoryId, @Param("defaultCategoryId") Long defaultCategoryId);
}

