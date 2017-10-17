package Model;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by EricWang on 10/12/17.
 */

public class Category implements java.io.Serializable {
    /**
     * Getter and Setter
     */
    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public Long getmID() {
        return mID;
    }

    public void setmID(Long mID) {
        this.mID = mID;
    }

    public Long getmBudgetID() {
        return mBudgetID;
    }

    public void setmBudgetID(Long mBudgetID) {
        this.mBudgetID = mBudgetID;
    }

    public double getmAmount() {
        return mAmount;
    }

    public void setmAmount(double mAmount) {
        this.mAmount = mAmount;
    }

    public double getmCurrentAmount() {
        return mCurrentAmount;
    }

    public void setmCurrentAmount(double mCurrentAmount) {
        this.mCurrentAmount = mCurrentAmount;
    }

    public List<Long> getmTransactionIDs() {
        return mTransactionIDs;
    }

    public void setmTransactionIDs(List<Long> mTransactionIDs) {
        this.mTransactionIDs = mTransactionIDs;
    }

    /**
     * Member Variable
     */
    private String mName;
    private Long mID;
    private Long mBudgetID;
    private double mAmount; // the upper bound of user setting amount
    private double mCurrentAmount; // current spending amount
    private List<Long> mTransactionIDs; // stores all transaction ids that realted to this category


    /**
     * Constructor
     */
    public Category() {
        mTransactionIDs = new ArrayList<>();
    }

    public Category(String name, Long budgetID) {
        mName = name;
        mAmount = 0.00;
        mCurrentAmount = 0.00;
        mBudgetID = budgetID;
        mTransactionIDs = new ArrayList<>();
    }

    public Category(String name, double amount, Long budgetID) {
        mName = name;
        mAmount = amount;
        mCurrentAmount = 0;
        mBudgetID = budgetID;
        mTransactionIDs = new ArrayList<>();
    }

    public Category(String name, double amount, double currentAmount, Long budgetID) {
        mName = name;
        mAmount = amount;
        mCurrentAmount = currentAmount;
        mBudgetID = budgetID;
        mTransactionIDs = new ArrayList<>();
    }

    /**
     * Public method
     */


    public void RemoveTransaction(Long transID, double amount) {
        mTransactionIDs.remove(transID);
        mCurrentAmount -= amount;
    }

    /**
     * Add a transaction id to the arrayList
     *
     * @param id
     */
    public void AddTransaction(Long id, double amount) {
        mTransactionIDs.add(id);
        mCurrentAmount += amount;
    }

    public Category Reset() {
        mName = "";
        mBudgetID = 0L;
        mAmount = 0.0;
        mCurrentAmount = 0.0;
        mTransactionIDs.clear();
        return this;
    }


}
