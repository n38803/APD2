package com.fullsail.android.smartbudget.fragments;
/**
 * Shaun Thompson - ADP2
 */
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fullsail.android.smartbudget.R;
import com.fullsail.android.smartbudget.dataclass.Income;

import java.util.ArrayList;


public class FormFragment extends Fragment {

    public static final String ACTION_ADD_ARTICLE = "com.fullsail.android.smartbudget.ACTION_ADD_ITEM";

    public TextView inputTitle;
    public TextView inputAmount;

    private final String TAG = "ADD_ACTIVITY_FRAGMENT";

    private ArrayList<Income> mIncomeList;

    public String mTitle;
    public String mAmount;

    public FormFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_form, container, false);


        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        inputTitle = (TextView) getActivity().findViewById(R.id.titleInput);
        inputAmount = (TextView) getActivity().findViewById(R.id.amountInput);

        // assign references
        Button save     = (Button) getActivity().findViewById(R.id.submit);
        Button cancel   = (Button) getActivity().findViewById(R.id.cancel);

        // create onClickListeners for each button w/execution of corresponding methods
        save.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        AlertDialog.Builder eBuilder = new AlertDialog.Builder(getActivity());

                        if (inputTitle.getText().toString().trim().length() == 0 || inputAmount.getText().toString().trim().length() == 0){
                            Log.e(TAG, "INPUTS CANNOT BE BLANK!");


                            eBuilder.setTitle("ERROR!");
                            eBuilder.setMessage("Inputs cannot be left blank.");
                            eBuilder.setCancelable(true);

                            // CREATE DIALOG
                            AlertDialog error = eBuilder.create();
                            error.show();


                        }
                        else {
                            float convertAmount = Float.parseFloat(inputAmount.getText().toString());
                            if(convertAmount > 0)
                            {
                                // call save method
                                onSave();
                            }
                            else
                            {
                                Log.e(TAG, "AMOUNT CANNOT BE 0!");

                                eBuilder.setTitle("ERROR!");
                                eBuilder.setMessage("Input amount cannot be 0.");
                                eBuilder.setCancelable(true);

                                // CREATE DIALOG
                                AlertDialog error = eBuilder.create();
                                error.show();
                            }

                        }


                    }
                }
        );

        cancel.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        // call save method
                        onCancel();

                    }
                }
        );

    }

    public void onCancel(){
        // Grabs intent from main activity
        Intent addIntent = getActivity().getIntent();

        clearDisplay();
        getActivity().finish();


    }




    public void onSave(){

        inputTitle = (TextView) getActivity().findViewById(R.id.titleInput);
        inputAmount = (TextView) getActivity().findViewById(R.id.amountInput);

        // assign input to string variables
        mTitle = inputTitle.getText().toString();
        mAmount = inputAmount.getText().toString();

        // Grabs intent from main activity
        Intent aIntent = getActivity().getIntent();

        // assign intent extra to string
        String iRequest = aIntent.getExtras().getString("Add");

        // conditional to determine which intent launched activity
        if(iRequest.equals("From_Income"))
        {
            Log.d(TAG, "Sending information to Main Activity");

            aIntent.putExtra("incomeTitle", mTitle);
            aIntent.putExtra("incomeAmount", mAmount);
            aIntent.putExtra("action", "add");
            getActivity().setResult(getActivity().RESULT_OK, aIntent);
        }
        else if(iRequest.equals("From_Expense"))
        {
            Log.d(TAG, "Sending information to Main Activity");

            aIntent.putExtra("expenseTitle", mTitle);
            aIntent.putExtra("expenseAmount", mAmount);
            aIntent.putExtra("action", "add");
            getActivity().setResult(getActivity().RESULT_OK, aIntent);
        }
        else {
            Log.e(TAG, "SOMETHING WENT WRONG!");

        }




        clearDisplay();
        getActivity().finish();



    }

    // reset inputs
    private void clearDisplay(){

        inputTitle = (TextView) getActivity().findViewById(R.id.titleInput);
        inputAmount = (TextView) getActivity().findViewById(R.id.amountInput);

        inputTitle.setText("");
        inputAmount.setText("");
    }
}
