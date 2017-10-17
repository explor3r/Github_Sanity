package Model;

import java.io.Serializable;

/**
 * Created by EricWang on 10/15/17.
 */

public class Variable implements Serializable {
    private static Variable instance = null;
    private double mThreshold = 0.75;
    private int mFrequency = 1;
    private String mUserID = "";

    public static Variable GetInstance() {
        if (instance == null) {
            instance = new Variable();
        }
        return instance;
    }

    /**
     * Getter and Setter
     */
    public double getmThreshold() {
        return mThreshold;
    }

    public void setmThreshold(double mThreshold) {
        this.mThreshold = mThreshold;
    }

    public int getmFrequency() {
        return mFrequency;
    }

    public void setmFrequency(int mFrequency) {
        this.mFrequency = mFrequency;
    }

    public String getmUserID() {
        return mUserID;
    }

    public void setmUserID(String mUserID) {
        this.mUserID = mUserID;
    }


}
