package com.fullsail.android.smartbudget.dataclass;
/**
 * Shaun Thompson - ADP2
 */

        import java.io.Serializable;

public class Income implements Serializable {

    private static final long serialVersionUID = 517116325584636891L;

    private String mTitle;
    private float mAmount;

    public Income (String _title, float _amount) {
        mTitle = _title;
        mAmount = _amount;
    }

    public String getTitle() {
        return mTitle;
    }

    public float getAmount() {
        return mAmount;
    }

}