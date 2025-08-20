// package com.expenses_06.demo.dto;

// import java.text.NumberFormat;
// import java.time.LocalDate;
// import java.util.Locale;

// import lombok.AllArgsConstructor;
// import lombok.Getter;

// @Getter
// @AllArgsConstructor
// public class TransactionResponseDto {
//     private Long id;
//     private String name;
//     private int price;
//      public String getFormattedPrice() {
//         NumberFormat formatter = NumberFormat.getNumberInstance(Locale.JAPAN);
//         return formatter.format(price);
//     }
//     // private Integer categoryId;  // int -> Integer に変更
//     // private String categoryName;
//     private CategoryDto category; 
//     private String transactionType;
//     private LocalDate date;
// }

package com.expenses_06.demo.dto;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TransactionResponseDto {
    private Long id;
    private String name;

    private int price; // 元のフィールドはそのまま保持

    @JsonIgnore
    public int getPrice() {
        return price;  // JSONには使わないようにする
    }

    @JsonProperty("price")
    public String getFormattedPrice() {
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.JAPAN);
        return formatter.format(price);
    }

    private CategoryDto category; 
    private String transactionType;
    private LocalDate date;
}
