package com.fullsail.android.smartbudget.fragments;
/**
 * Shaun Thompson - ADP2
 */
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.fullsail.android.smartbudget.R;
import com.fullsail.android.smartbudget.dataclass.Expenses;
import com.fullsail.android.smartbudget.dataclass.ExpensesAdapter;
import com.fullsail.android.smartbudget.dataclass.Income;
import com.fullsail.android.smartbudget.dataclass.IncomeAdapter;

import java.util.ArrayList;

public class ExpenseListviewFragment extends Fragment {

    final String TAG = "IncomeListViewFragment";

    private ExpenseListener mListener;

    public interface ExpenseListener{
        public void viewExpense(int position);
        public ArrayList<Expenses> getExpenses();
    }

    public ExpenseListviewFragment() {

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if(activity instanceof ExpenseListener) {
            mListener = (ExpenseListener) activity;
        } else {
            throw new IllegalArgumentException("Containing activity must implement DetailListener interface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_elistview, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO - implement static data (milestone2)

        // TODO - update list dynamically to reflect local storage (milestone3)

        ListView expensesListView = (ListView) getView().findViewById(R.id.eListView);
        ExpensesAdapter iAdapter = new ExpensesAdapter(getActivity().getApplicationContext(), mListener.getExpenses());
        expensesListView.setAdapter(iAdapter);

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
        ListView expensesList = (ListView) getView().findViewById(R.id.eListView);
        BaseAdapter expensesAdapter = (BaseAdapter) expensesList.getAdapter();
        expensesAdapter.notifyDataSetChanged();
    }
}

