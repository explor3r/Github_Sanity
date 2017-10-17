package com.example.tomdong.sanity;

/**
 * Created by tomdong on 10/14/17.
 */
public class Category_card {
    private String CatoryType;
    private String BudgetType;

    public Category_card(String CatoryType) {
        //this.BudgetType = BudgetType;
        this.CatoryType = CatoryType;
    }

    public String GetBudgetType() {
        return BudgetType;
    }

    public String GetCatoryType() {
        return CatoryType;
    }

    public void setCatoryType(String CatoryType) {
        this.CatoryType = CatoryType;
    }

    public void setBudgetType(String BudgetType) {
        this.BudgetType = BudgetType;
    }
}