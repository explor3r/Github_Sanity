package Model;

/**
 * Created by zhongchu on 10/12/17.
 */

public class Transaction implements java.io.Serializable {

    private double mAmount;
    private long mCategoryId;
    private long mTransactionId;
    private String mNotes = "";
    private int mYear;
    private int mMonth;
    private int mDay;

    public double getmAmount() {
        return mAmount;
    }

    public long getmCategoryId() {
        return mCategoryId;
    }

    public long getmTransactionId() {
        return mTransactionId;
    }

    public String getmNotes() {
        return mNotes;
    }

    public void setmAmount(double mAmount) {
        this.mAmount = mAmount;
    }

    public void setmCategoryId(long mCategoryId) {
        this.mCategoryId = mCategoryId;
    }

    public void setmTransactionId(long mTransactionId) {
        this.mTransactionId = mTransactionId;
    }

    public void setmNotes(String mNotes) {
        this.mNotes = mNotes;
    }

    public void setmYear(int mYear) {
        this.mYear = mYear;
    }

    public void setmMonth(int mMonth) {
        this.mMonth = mMonth;
    }

    public void setmDay(int mDay) {
        this.mDay = mDay;
    }

    public int getmYear() {
        return mYear;
    }

    public int getmMonth() {
        return mMonth;
    }

    public int getmDay() {
        return mDay;
    }

    public Transaction(double amount, long categoryId, String notes, int year, int month, int day) {
        mYear = year;
        mMonth = month;
        mDay = day;
        mAmount = amount;
        mCategoryId = categoryId;
        mTransactionId = System.currentTimeMillis();
        mNotes = notes;
    }

    public Transaction() {

    }
}
