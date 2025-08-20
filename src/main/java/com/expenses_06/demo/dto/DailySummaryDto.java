// package com.expenses_06.demo.dto;

// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.NoArgsConstructor;

// @Getter
// @AllArgsConstructor
// @NoArgsConstructor
// public class DailySummaryDto {
//     private int day;      // 日（1〜31）
//     private int income;   // 収入合計
//     private int expense;  // 支出合計
// }

package com.expenses_06.demo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.NumberFormat;
import java.util.Locale;

@Getter
@NoArgsConstructor
public class DailySummaryDto {
    private int day;      // 日（1〜31）
    private String income;   // 収入合計（カンマ付き文字列）
    private String expense;  // 支出合計（カンマ付き文字列）

    public DailySummaryDto(int day, int income, int expense) {
        this.day = day;

        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.JAPAN);
        this.income = formatter.format(income);
        this.expense = formatter.format(expense);
    }
}
