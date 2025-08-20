package com.expenses_06.demo.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionRequestDto {
    @Min(value = 0, message = "金額は0以上である必要があります")
    private int price;
    private String transactionType;
    private String name;
    private int categoryId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    public void setName(String name) {
        this.name = (name == null || name.trim().isEmpty()) ? "-" : name;
    }
}