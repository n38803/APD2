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

public class ExpensesAdapter extends BaseAdapter {

    private static final long ID_CONSTANT = 0x01000000;

    Context mContext;
    ArrayList<Expenses> mExpenseItems;

    public ExpensesAdapter(Context context, ArrayList<Expenses> items) {
        mContext = context;
        mExpenseItems = items;
    }


    @Override
    public int getCount() {
        return mExpenseItems.size();
    }

    @Override
    public Expenses getItem(int position) {
        return mExpenseItems.get(position);
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

        Expenses expense = getItem(position);

        TextView expenseTitle = (TextView) convertView.findViewById(R.id.title);
        expenseTitle.setText(expense.getTitle());

        TextView expenseAmount = (TextView) convertView.findViewById(R.id.amount);
        String amount = Float.toString(expense.getAmount());
        expenseAmount.setText(amount);

        return convertView;
    }
}
