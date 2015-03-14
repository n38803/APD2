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

public class ExpenseFormFragment extends Fragment {

    public ExpenseFormFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_eform, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        // TODO - connect views & store in custom object (milestone2)

        // TODO - update custom object to save to local storage (milestone3)

    }
}
