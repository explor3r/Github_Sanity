package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yifan on 10/12 012.
 */

public class Budget implements Serializable {
    private long mBudgetId = 0;
    private String mName = "";
    //private int mDueDate = 1;
    //private boolean mIsWeek = true;
    private long mDueTime = 0;
    private int mPeriod = 0;
    private double mAmount = 0.0;
    private double mPrevAmount = 0.0;
    private double mTotalAmount = 0.0;
    private List<Long> mCatIds = new ArrayList<>();

    /**
     * Default Constructor
     */
    public Budget() {

    }

    /**
     * Constructor - Initialize a Budget
     *
     * @param dueTime due time
     * @param period  budget period
     * @param catIds  category Ids
     */
    public Budget(String name, long dueTime, int period, List<Long> catIds) {
        mBudgetId = System.currentTimeMillis();
        mName = name;
        mDueTime = dueTime;
        mPeriod = period;
        mCatIds = catIds;
        UpdateAmountLimit();
    }


    //<editor-fold desc="Budget Update">
    /* =============== Budget Update =============== */

    /**
     * Update current balance of the budget
     * Should be triggered whenever a transaction/category change happends
     */
    public void UpdateTotalAmount() {
        double totalAmount = 0.0;
        for (long catId : mCatIds)
            totalAmount += CategoryModel.GetInstance().GetCategoryById(catId).getmAmount();
        mTotalAmount = totalAmount;
    }

    /**
     * Reset budget if it's time (should be triggered somewhere)
     */
    public void ResetBudget() {
        // If new period
        UpdateTotalAmount();
        /*Calendar rightNow = Calendar.getInstance();
        if (mIsWeek)
        {
            if (mDueDate < rightNow.get(Calendar.DAY_OF_MONTH)) {
                mPrevAmount = mTotalAmount;
            }
            else
            {

            }
        }
        else{

        }*/
        if (mDueTime <= System.currentTimeMillis() / 1000) {
            mPrevAmount = mTotalAmount;
            mDueTime += 86400 * mPeriod;
        }
    }
    //</editor-fold>


    //<editor-fold desc="Category Related">
    /* =============== Category Related =============== */

    /**
     * add a category to this budget
     *
     * @param catId a category ID
     */
    public void AddCatId(long catId) {
        mCatIds.add(catId);
    }

    /**
     * remove a category from this budget
     *
     * @param catId a category ID
     */
    public void RemoveCatId(long catId) {
        mCatIds.remove(catId);
    }
    //</editor-fold>


    //<editor-fold desc="Getters and Setters">
    /* =============== Getters and Setters =============== */


    /**
     * Getter
     *
     * @return mAmount
     */
    public double GetAmountLimit() {
        UpdateAmountLimit();
        return mAmount;
    }

    public void UpdateAmountLimit() {
        double tempAmount = 0.0;
        for (Long catId : mCatIds)
            tempAmount += CategoryModel.GetInstance().GetCategoryById(catId).getmAmount();
        mAmount = tempAmount;
    }

    /**
     * return current budget balance
     *
     * @return <b>double</b> budget balance
     */
    public double GetCurrAmount() {
        return mTotalAmount - mPrevAmount;
    }

    /**
     * Getter
     *
     * @return mName
     */
    public String getmName() {
        return mName;
    }

    /**
     * Setter
     *
     * @param mName
     */
    public void setmName(String mName) {
        this.mName = mName;
    }

    /**
     * Getter
     *
     * @return mBudgetId
     */
    public long getmBudgetId() {
        return mBudgetId;
    }

    /**
     * Getter
     *
     * @return mDueTime
     */
    public long getmDueTime() {
        return mDueTime;
    }

    /**
     * Setter
     *
     * @param mDueTime
     */
    public void setmDueTime(long mDueTime) {
        this.mDueTime = mDueTime;
    }

    /**
     * Getter
     *
     * @return mPeriod
     */
    public int getmPeriod() {
        return mPeriod;
    }

    /**
     * Setter
     *
     * @param mPeriod
     */
    public void setmPeriod(int mPeriod) {
        this.mPeriod = mPeriod;
    }

    /**
     * Getter
     *
     * @return mCatIds
     */
    public List<Long> getmCatIds() {
        return mCatIds;
    }

    /**
     * Setter
     *
     * @param mCatIds
     */
    public void setmCatIds(List<Long> mCatIds) {
        this.mCatIds = mCatIds;
    }

    /**
     * Getter
     *
     * @return mPrevAmount
     */
    public double getmPrevAmount() {
        return mPrevAmount;
    }

    /**
     * Setter
     *
     * @param mPrevAmount
     */
    public void setmPrevAmount(double mPrevAmount) {
        this.mPrevAmount = mPrevAmount;
    }
    //</editor-fold>
}
