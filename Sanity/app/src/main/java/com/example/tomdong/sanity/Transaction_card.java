package com.example.tomdong.sanity;

/**
 * Created by tomdong on 10/15/17.
 */

/**
 * Created by tomdong on 10/15/17.
 */

public class Transaction_card {
    private String CategoryType;
    private String time;
    private String Note;
    private String amount;

    public Transaction_card(String CategoryType, String time, String amount, String Note) {
        //this.BudgetType = BudgetType;
        this.CategoryType = CategoryType;
        this.time = time;
        this.Note = Note;
        this.amount = amount;
    }

    public String GetCategoryType() {
        return CategoryType;
    }

    public String GetTime() {
        return time;
    }

    public String GetNote() {
        return Note;
    }

    public String GetAmount() {
        return amount;
    }


}