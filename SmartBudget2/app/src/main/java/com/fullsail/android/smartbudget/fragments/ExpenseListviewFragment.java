package com.fullsail.android.smartbudget.fragments;
/**
 * Shaun Thompson - ADP2
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fullsail.android.smartbudget.ExpenseActivity;
import com.fullsail.android.smartbudget.IncomeActivity;
import com.fullsail.android.smartbudget.R;
import com.fullsail.android.smartbudget.dataclass.Expenses;
import com.fullsail.android.smartbudget.dataclass.ExpensesAdapter;
import com.fullsail.android.smartbudget.dataclass.Income;
import com.fullsail.android.smartbudget.dataclass.IncomeAdapter;

import java.util.ArrayList;

public class ExpenseListviewFragment extends Fragment {

    final String TAG = "IncomeListViewFragment";

    private ExpenseListener mListener;

    float thisExpense;
    float totalExpenses;
    public static float mainExpenses;

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
    public void onResume() {
        super.onResume();
        updateListData();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        // update income quantity for main textview
        Intent intent = getActivity().getIntent();
        intent.putExtra("Fragment", "Expense");
        getActivity().setResult(getActivity().RESULT_OK, intent);


        ListView expensesListView = (ListView) getView().findViewById(R.id.eListView);
        ExpensesAdapter iAdapter = new ExpensesAdapter(getActivity().getApplicationContext(), mListener.getExpenses());
        expensesListView.setAdapter(iAdapter);

        updateListData();

        expensesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder eBuilder = new AlertDialog.Builder(getActivity());
                eBuilder.setTitle("Alert");
                eBuilder.setMessage("Would you like to delete this item?");
                eBuilder.setCancelable(false);
                eBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                eBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        mListener.getExpenses().remove(position);
                        dialog.cancel();
                        ((ExpenseActivity)getActivity()).writeFile();
                        updateListData();
                    }
                });

                // CREATE DIALOG
                AlertDialog error = eBuilder.create();
                error.show();


            }
        });

    }

    public void updateListData(){
        //update list
        ListView expensesList = (ListView) getView().findViewById(R.id.eListView);
        BaseAdapter expensesAdapter = (BaseAdapter) expensesList.getAdapter();
        expensesAdapter.notifyDataSetChanged();

        int expensesSize = ExpenseActivity.mExpenseList.size();
        Log.i(TAG, "List Size: " + expensesSize);

        thisExpense = 0;
        totalExpenses = 0;



        // calculate total of income
        for (int i = 0;i<expensesSize;i++){
            thisExpense = ExpenseActivity.mExpenseList.get(i).getAmount();
            totalExpenses = (totalExpenses+thisExpense);
            mainExpenses = totalExpenses;

        }


        // update textview
        TextView expensesTotal = (TextView) getView().findViewById((R.id.totalExpenses));
        expensesTotal.setText("$" + String.format("%.2f", totalExpenses));
    }
}

