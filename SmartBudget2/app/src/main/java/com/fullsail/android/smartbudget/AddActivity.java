package com.fullsail.android.smartbudget;

/**
 * Shaun Thompson - ADP2
 */

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.fullsail.android.smartbudget.dataclass.Income;
import com.fullsail.android.smartbudget.fragments.FormFragment;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class AddActivity extends Activity {

    public static final String ACTION_ADD_ARTICLE = "fullsail.com.mdf3_w3.ACTION_ADD_ARTICLE";

    public TextView inputTitle;
    public TextView inputAmount;

    private final String TAG = "ADD ACTIVITY";

    private ArrayList<Income> mIncomeList;

    public String mTitle;
    public String mAmount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // Check to see whether or not there is a saved instance of the fragment
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.addContainer, new FormFragment())
                    .commit();
        }







    }

    public void onCancel(View v){
        // Grabs intent from main activity
        Intent addIntent = getIntent();

        clearDisplay();
        finish();


    }




    public void onSave(View v){

        // assign references
        inputTitle = (TextView) findViewById(R.id.titleInput);
        inputAmount = (TextView) findViewById(R.id.amountInput);

        // assign input to string variables
        mTitle = inputTitle.getText().toString();
        mAmount = inputAmount.getText().toString();

        // Grabs intent from main activity
        Intent aIntent = getIntent();

        // assign intent extra to string
        String iRequest = aIntent.getExtras().getString("Add");

        // conditional to determine which intent launched activity
        if(iRequest.equals("From_Income"))
        {
            Log.d(TAG, "Sending information to Main Activity");

            aIntent.putExtra("incomeTitle", mTitle);
            aIntent.putExtra("incomeAmount", mAmount);
            aIntent.putExtra("action", "add");
            setResult(RESULT_OK, aIntent);
        }
        else if(iRequest.equals("From_Expense"))
        {
            Log.d(TAG, "Sending information to Main Activity");

            aIntent.putExtra("expenseTitle", mTitle);
            aIntent.putExtra("expenseAmount", mAmount);
            aIntent.putExtra("action", "add");
            setResult(RESULT_OK, aIntent);
        }




        clearDisplay();
        finish();



    }

    // reset inputs
    private void clearDisplay(){
        inputTitle = (TextView) findViewById(R.id.titleInput);
        inputAmount = (TextView) findViewById(R.id.amountInput);

        inputTitle.setText("");
        inputAmount.setText("");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}