package com.fullsail.android.smartbudget.dataclass;

/**
 * Created by shaunthompson on 3/21/15.
 */
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
    ArrayList<Income> mArticles;

    public IncomeAdapter(Context context, ArrayList<Income> contacts) {
        mContext = context;
        mArticles = contacts;
    }

    @Override
    public int getCount() {
        return mArticles.size();
    }

    @Override
    public Income getItem(int position) {
        return mArticles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return ID_CONSTANT + position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.income_item, parent, false);
        }

        Income article = getItem(position);
        TextView articleTitle = (TextView) convertView.findViewById(R.id.title);
        articleTitle.setText(article.getTitle());

        return convertView;
    }
}
