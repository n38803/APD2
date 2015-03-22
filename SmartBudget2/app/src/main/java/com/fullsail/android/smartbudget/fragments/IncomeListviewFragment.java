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
import com.fullsail.android.smartbudget.dataclass.Income;
import com.fullsail.android.smartbudget.dataclass.IncomeAdapter;

import java.util.ArrayList;

public class IncomeListviewFragment extends Fragment {

    final String TAG = "IncomeListViewFragment";

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

        // TODO - implement static data (milestone2)

        // TODO - update list dynamically to reflect local storage (milestone3)

        ListView incomeListView = (ListView) getView().findViewById(R.id.iListview);
        IncomeAdapter iAdapter = new IncomeAdapter(getActivity().getApplicationContext(), mListener.getIncome());
        incomeListView.setAdapter(iAdapter);

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
        ListView incomeList = (ListView) getView().findViewById(R.id.iListview);
        BaseAdapter incomeAdapter = (BaseAdapter) incomeList.getAdapter();
        incomeAdapter.notifyDataSetChanged();
    }
}

