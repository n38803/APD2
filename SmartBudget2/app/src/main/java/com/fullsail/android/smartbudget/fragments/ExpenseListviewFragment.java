package com.fullsail.android.smartbudget.fragments;
/**
 * Shaun Thompson - ADP2
 */
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fullsail.android.smartbudget.R;

public class ExpenseListviewFragment extends Fragment {

    public ExpenseListviewFragment() {

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

    }
}
