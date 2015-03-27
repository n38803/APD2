package com.fullsail.android.smartbudget;
/**
 * Shaun Thompson - ADP2
 */

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fullsail.android.smartbudget.dataclass.Income;
import com.fullsail.android.smartbudget.fragments.ExpenseListviewFragment;
import com.fullsail.android.smartbudget.fragments.IncomeListviewFragment;

import java.util.ArrayList;


public class MainActivity extends Activity {



    final String TAG = "MAIN_ACTIVITY";
    public static final int ADDREQUEST = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        updateDisplay();


        Button income   = (Button) findViewById(R.id.iButton);

        // create onClickListeners for each button w/execution of corresponding methods
        income.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        Intent incomeIntent = new Intent(MainActivity.this, IncomeActivity.class);
                        incomeIntent.putExtra("Income", "From_MainActivity");
                        //startActivity(incomeIntent);
                        startActivityForResult(incomeIntent, ADDREQUEST);

                        Log.i(TAG, "Income Button Clicked");

                    }
                }
        );




        Button expense   = (Button) findViewById(R.id.eButton);

        // create onClickListeners for each button w/execution of corresponding methods
        expense.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        Intent expIntent = new Intent(MainActivity.this, ExpenseActivity.class);
                        //addIntent.putExtra("contactName", mContactDataList.get());
                        expIntent.putExtra("Expenses", "From_MainActivity");
                        startActivityForResult(expIntent, ADDREQUEST);
                        //startActivity(addIntent);

                        Log.i(TAG, "Expense Button Clicked");

                    }
                }
        );








    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {

            case (ADDREQUEST) : {
                if (resultCode == Activity.RESULT_OK) {

                    updateDisplay();
                }
                break;
            }

        }
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

        return super.onOptionsItemSelected(item);
    }

    public void updateDisplay() {

        // TODO - create dynamic update of spending power (milestone3)
        TextView sp = (TextView) findViewById(R.id.spendingPower);
        float totalSP = (IncomeListviewFragment.mainIncome - ExpenseListviewFragment.mainExpenses);
        Log.e(TAG,
                "Income: "              + IncomeListviewFragment.mainIncome +
                        " // Expenses: "        + ExpenseListviewFragment.mainExpenses +
                        " // Spending Power: "  + totalSP
        );
        sp.setText("$" + String.format("%.2f", totalSP));


    }

}
