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

import com.fullsail.android.smartbudget.IncomeActivity;
import com.fullsail.android.smartbudget.R;
import com.fullsail.android.smartbudget.dataclass.Expenses;
import com.fullsail.android.smartbudget.dataclass.Income;
import com.fullsail.android.smartbudget.dataclass.IncomeAdapter;

import java.util.ArrayList;

public class IncomeListviewFragment extends Fragment {

    final String TAG = "IncomeListViewFragment";

    float thisIncome;
    float totalIncome;

    public static float mainIncome;

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

        // update income quantity for main textview
        Intent intent = getActivity().getIntent();
        intent.putExtra("Fragment", "Income");
        getActivity().setResult(getActivity().RESULT_OK, intent);



        ListView incomeListView = (ListView) getView().findViewById(R.id.iListview);
        IncomeAdapter iAdapter = new IncomeAdapter(getActivity().getApplicationContext(), mListener.getIncome());
        incomeListView.setAdapter(iAdapter);

        updateListData();


        incomeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

                        mListener.getIncome().remove(position);
                        dialog.cancel();
                        ((IncomeActivity)getActivity()).writeFile();
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
        // update list
        ListView incomeList = (ListView) getView().findViewById(R.id.iListview);
        BaseAdapter incomeAdapter = (BaseAdapter) incomeList.getAdapter();
        incomeAdapter.notifyDataSetChanged();

        int incomeSize = IncomeActivity.mIncomeList.size();
        Log.i(TAG, "List Size: " + incomeSize);

        thisIncome = 0;
        totalIncome = 0;

        // calculate total of income
        for (int i = 0;i<incomeSize;i++){
            thisIncome = IncomeActivity.mIncomeList.get(i).getAmount();
            totalIncome = (totalIncome+thisIncome);
            mainIncome = totalIncome;

        }


        // update textview
        TextView incomeTotal = (TextView) getView().findViewById((R.id.totalIncome));
        incomeTotal.setText("$" + String.format("%.2f", totalIncome));


    }
}

