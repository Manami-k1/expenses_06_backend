package com.expenses_06.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.expenses_06.demo.service.TransactionService;
import com.expenses_06.demo.dto.DailySummaryDto;
// import com.expenses_06.demo.dto.DailySummaryDto;
import com.expenses_06.demo.dto.SummaryDto;
import com.expenses_06.demo.dto.TransactionRequestDto;
import com.expenses_06.demo.dto.TransactionResponseDto;
import com.expenses_06.demo.model.Transaction;


@CrossOrigin(origins = "http://localhost:3000") 
@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService service;

    public TransactionController(TransactionService service) {
        this.service = service;
    }

    @GetMapping("/summary")
    public SummaryDto getSummary(@RequestParam int year, @RequestParam int month,
                                  @RequestParam(required = false) Integer day) {
        Long userId = 1L; // 仮に固定（あとで認証を使って動的にする）
        if (day != null) {
            return service.getDailySummary(userId, year, month, day);
        } else {
            return service.getMonthlySummary(userId, year, month);
        }
    }
    @PostMapping
    // public Transaction createTransaction(@RequestBody Transaction transaction) {
    //     return service.create(transaction);
    // }
    public Transaction createTransaction(@RequestBody TransactionRequestDto dto) {
        return service.createFromDto(dto);
    }
    @GetMapping("/daily")
    public List<TransactionResponseDto> getDailyTransactions(
        @RequestParam int year,
        @RequestParam int month,
        @RequestParam int day
    ) {
        Long userId = 1L; // 仮に固定
        return service.getTransactionsByDay(userId, year, month, day);
    }
//     @GetMapping("/summary/list")
// public List<DailySummaryDto> getMonthlyDailySummary(
//     @RequestParam int year,
//     @RequestParam int month) {
//     Long userId = 1L; // 仮置き
//     return service.getDailySummariesForMonth(userId, year, month);
// }
@GetMapping("/list")
public List<DailySummaryDto> getDailySummariesForMonth(
        @RequestParam int year,
        @RequestParam int month
) {
    Long userId = 1L; // 仮で固定
    return service.getDailySummariesForMonth(userId, year, month);
}
@PutMapping("/{id}")
public ResponseEntity<Transaction> updateTransaction(
    @PathVariable Long id,
    @RequestBody TransactionRequestDto dto
) {
    try {
        Transaction updated = service.updateTransaction(id, dto);
        return ResponseEntity.ok(updated);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().build(); // またはエラーメッセージを返す
    }
}
  @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long id) {
        service.deleteTransaction(id);
        return ResponseEntity.noContent().build();  // 204 No Content を返す
    }
}
