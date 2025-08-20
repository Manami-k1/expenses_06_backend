package com.expenses_06.demo.model;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Transaction {
    @Id @GeneratedValue
    private Long id;

    private String name;         // ← 追加
    // private int categoryId;   

    @ManyToOne(fetch = FetchType.LAZY)   // 遅延読み込み推奨（必要に応じてEAGER）
    @JoinColumn(name = "category_id")   // テーブルの外部キーのカラム名
    private Category category;

    private Long userId;
    private LocalDate date;
    private int price;
    private String type; 
}