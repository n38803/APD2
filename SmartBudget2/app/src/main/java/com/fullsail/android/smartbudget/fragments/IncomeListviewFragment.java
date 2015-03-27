package com.fullsail.android.smartbudget.fragments;
/**
 * Shaun Thompson - ADP2
 */
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fullsail.android.smartbudget.IncomeActivity;
import com.fullsail.android.smartbudget.R;
import com.fullsail.android.smartbudget.dataclass.Expenses;
import com.fullsail.android.smartbudget.dataclass.Income;
import com.fullsail.android.smartbudget.dataclass.IncomeAdapter;

import java.util.ArrayList;

public class IncomeListviewFragment extends Fragment {

    final String TAG = "IncomeListViewFragment";

    float thisIncome;
    public float totalIncome;

    private ArrayList<Income> mIncomeList;

    private IncomeListener mListener;

    public interface IncomeListener{
        public void viewIncome(int position);
        public ArrayList<Income> getIncome();
    }

    public IncomeListviewFragment() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(activity instanceof IncomeListener) {
            mListener = (IncomeListener) activity;
        } else {
            throw new IllegalArgumentException("Containing activity must implement DetailListener interface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ilistview, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        ListView incomeListView = (ListView) getView().findViewById(R.id.iListview);
        IncomeAdapter iAdapter = new IncomeAdapter(getActivity().getApplicationContext(), mListener.getIncome());
        incomeListView.setAdapter(iAdapter);

        updateListData();

        // TODO - ADD DELETE ITEM or VIEW ITEM OPTION?
        /*
        articleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.viewIncome(position);
            }
        });
        */












    }

    public void updateListData(){
        // update list
        ListView incomeList = (ListView) getView().findViewById(R.id.iListview);
        BaseAdapter incomeAdapter = (BaseAdapter) incomeList.getAdapter();
        incomeAdapter.notifyDataSetChanged();

        int incomeSize = IncomeActivity.mIncomeList.size();
        Log.i(TAG, "List Size: " + incomeSize);

        thisIncome = 0;
        totalIncome = 0;

        // calculate total of income
        for (int i = 1;i<incomeSize;i++){
            thisIncome = IncomeActivity.mIncomeList.get(i).getAmount();
            totalIncome = (totalIncome+thisIncome);

        }


        // TODO - FORMAT STRING SO FLOAT DOES NOT EXCEED 2 DECIMALS
        // update textview
        TextView incomeTotal = (TextView) getView().findViewById((R.id.totalIncome));
        String displayIncome = Float.toString(totalIncome);
        incomeTotal.setText("$" + displayIncome);
    }
}

