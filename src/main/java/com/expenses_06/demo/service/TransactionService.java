package com.expenses_06.demo.service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.expenses_06.demo.model.Category;
import com.expenses_06.demo.model.Transaction;
import com.expenses_06.demo.repository.CategoryRepository;
import com.expenses_06.demo.repository.TransactionRepository;
import com.expenses_06.demo.dto.CategoryDto;
import com.expenses_06.demo.dto.DailySummaryDto;
import com.expenses_06.demo.dto.SummaryDto;
import com.expenses_06.demo.dto.TransactionRequestDto;
import com.expenses_06.demo.dto.TransactionResponseDto;

@Service
public class TransactionService {

    private final TransactionRepository repository;
    private final CategoryRepository categoryRepository;

    public TransactionService(TransactionRepository repository, CategoryRepository categoryRepository) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
    }

    public Transaction create(Transaction transaction) {
        return repository.save(transaction);
    }

    public void deleteTransaction(Long transactionId) {
        repository.deleteById(transactionId);
    }

    public Transaction createFromDto(TransactionRequestDto dto) {
        Transaction entity = new Transaction();
        entity.setUserId(1L); // 仮のユーザーID
        entity.setPrice(dto.getPrice());
        entity.setType(dto.getTransactionType());
        entity.setDate(dto.getDate());
        entity.setName(dto.getName());
        Long categoryId = Long.valueOf(dto.getCategoryId());  // int → Long に変換
        Category category = categoryRepository.findById(categoryId).orElse(null);  // ここでcategoryIdを使う
        entity.setCategory(category);
        return repository.save(entity);
    }

    public List<TransactionResponseDto> getTransactionsByDay(Long userId, int year, int month, int day) {
        LocalDate date = LocalDate.of(year, month, day);
        List<Transaction> transactions = repository.findByUserIdAndDateBetween(userId, date, date);
        
        return transactions.stream()
        .map(tx -> {
        CategoryDto categoryDto = null;
        if (tx.getCategory() != null) {
            categoryDto = new CategoryDto(
                 tx.getCategory().getId(),
                tx.getCategory().getName(),
                tx.getCategory().getColor()
            );
        }

        return new TransactionResponseDto(
            tx.getId(),
            tx.getName(),
            tx.getPrice(),
            categoryDto,
            tx.getType(),
            tx.getDate()
        );
    })
    .collect(Collectors.toList());


}

    public SummaryDto getMonthlySummary(Long userId, int year, int month) {
        LocalDate start = YearMonth.of(year, month).atDay(1);
        LocalDate end = YearMonth.of(year, month).atEndOfMonth();
        List<Transaction> transactions = repository.findByUserIdAndDateBetween(userId, start, end);

        return new SummaryDto(transactions);
    }

    public SummaryDto getDailySummary(Long userId, int year, int month, int day) {
        LocalDate date = LocalDate.of(year, month, day);
        List<Transaction> transactions = repository.findByUserIdAndDateBetween(userId, date, date);

        return new SummaryDto(transactions);
    }
   public List<DailySummaryDto> getDailySummariesForMonth(Long userId, int year, int month) {
    int days = YearMonth.of(year, month).lengthOfMonth();
    List<DailySummaryDto> list = new ArrayList<>();

for (int d = 1; d <= days; d++) {
    LocalDate date = LocalDate.of(year, month, d);
    int inc = repository.sumAmountByUserIdAndDateAndType(userId, date, date, "income");
    int exp = repository.sumAmountByUserIdAndDateAndType(userId, date, date, "expense");
    list.add(new DailySummaryDto(d, inc, exp));
}

    return list;
}
public Transaction updateTransaction(Long id, TransactionRequestDto dto) {
    Transaction existing = repository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Transaction not found"));

    existing.setName(dto.getName());                // ← メモ・タイトル
    existing.setPrice(dto.getPrice());              // 金額
    existing.setType(dto.getTransactionType());     // "income" or "expense"
    existing.setDate(dto.getDate());                // 日付

    // カテゴリをIDで取得して設定
    Long categoryId = Long.valueOf(dto.getCategoryId());
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new IllegalArgumentException("Category not found"));
    existing.setCategory(category);

    return repository.save(existing);
}


}


