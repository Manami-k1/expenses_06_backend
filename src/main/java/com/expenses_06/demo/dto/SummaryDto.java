// // package com.expenses_06.demo.dto;

// // public class SummaryDto {
    
// // }

// package com.expenses_06.demo.dto;

// import java.text.NumberFormat;
// import java.util.List;
// import java.util.Locale;

// import com.expenses_06.demo.model.Transaction;

// public class SummaryDto {
//     // private int totalAmount;

//     // public SummaryDto(List<Transaction> transactions) {
//     //     this.totalAmount = transactions.stream()
//     //                                    .mapToInt(Transaction::getAmount)
//     //                                    .sum();
//     // }
//     private int income;
//     private int expense;
//     private int net;
//         public SummaryDto(List<Transaction> transactions) {
//         this.income = transactions.stream()
//             .filter(t -> "income".equalsIgnoreCase(t.getType()))
//             .mapToInt(Transaction::getPrice)
//             .sum();

//         this.expense = transactions.stream()
//             .filter(t -> "expense".equalsIgnoreCase(t.getType()))
//             .mapToInt(Transaction::getPrice)
//             .sum();

//         this.net = income - expense;
//     }

//     // public int getTotalAmount() {
//     //     return totalAmount;
//     // }
//     public int getIncome() {
//         return income;
//     }

//     public int getExpense() {
//         return expense;
//     }

//     public int getNet() {
//         return net;
//     }

//     public String getFormattedIncome() {
//         NumberFormat formatter = NumberFormat.getNumberInstance(Locale.JAPAN);
//         return formatter.format(income);
//     }

//     public String getFormattedExpense() {
//         NumberFormat formatter = NumberFormat.getNumberInstance(Locale.JAPAN);
//         return formatter.format(expense);
//     }

//     public String getFormattedNet() {
//         NumberFormat formatter = NumberFormat.getNumberInstance(Locale.JAPAN);
//         return formatter.format(net);
//     }
// }

package com.expenses_06.demo.dto;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import com.expenses_06.demo.model.Transaction;

public class SummaryDto {
    private String income;
    private String expense;
    private String net;

    public SummaryDto(List<Transaction> transactions) {
        int incomeVal = transactions.stream()
            .filter(t -> "income".equalsIgnoreCase(t.getType()))
            .mapToInt(Transaction::getPrice)
            .sum();

        int expenseVal = transactions.stream()
            .filter(t -> "expense".equalsIgnoreCase(t.getType()))
            .mapToInt(Transaction::getPrice)
            .sum();

        int netVal = incomeVal - expenseVal;

        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.JAPAN);
        this.income = formatter.format(incomeVal);
        this.expense = formatter.format(expenseVal);
        this.net = formatter.format(netVal);
    }

    public String getIncome() {
        return income;
    }

    public String getExpense() {
        return expense;
    }

    public String getNet() {
        return net;
    }
}
