package com.fullsail.android.smartbudget.dataclass;

/**
 * Created by shaunthompson on 3/21/15.
 */
import android.app.Activity;
import android.content.Context;
import com.fullsail.android.smartbudget.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class IncomeAdapter extends BaseAdapter {

    private static final long ID_CONSTANT = 0x01000000;

    Context mContext;
    ArrayList<Income> mIncomeItems;

    public IncomeAdapter(Context context, ArrayList<Income> items) {
        mContext = context;
        mIncomeItems = items;
    }


    @Override
    public int getCount() {
        return mIncomeItems.size();
    }

    @Override
    public Income getItem(int position) {
        return mIncomeItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ID_CONSTANT + position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        }

        Income income = getItem(position);

        TextView incomeTitle = (TextView) convertView.findViewById(R.id.title);
        incomeTitle.setText(income.getTitle());

        TextView incomeAmount = (TextView) convertView.findViewById(R.id.amount);
        String amount = Float.toString(income.getAmount());
        incomeAmount.setText(amount);

        return convertView;
    }
}
