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
import android.widget.Toast;

import com.fullsail.android.smartbudget.dataclass.Expenses;
import com.fullsail.android.smartbudget.dataclass.Income;
import com.fullsail.android.smartbudget.fragments.ExpenseListviewFragment;
import com.fullsail.android.smartbudget.fragments.IncomeListviewFragment;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class MainActivity extends Activity {



    final String TAG = "MAIN_ACTIVITY";
    public static final int ADDREQUEST = 0;

    public float totalSP;
    public float totalIncome;
    public float totalExpenses;

    private final String spFile = "SmartBudget_SpendingPower.txt";
    public ArrayList<Float> spObject;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        readSP();
        //updateDisplay();


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

                    String request = data.getStringExtra("Fragment");

                    Log.e(TAG, request);

                    // Logic detection in order to update Spending Power accordingly
                    if (request.equals("Income")){
                        totalIncome = IncomeListviewFragment.mainIncome;
                    }
                    else if (request.equals("Expense")){
                        totalExpenses = ExpenseListviewFragment.mainExpenses;
                    }

                    updateDisplay();
                    writeSP();
                }
                break;
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);




        return false;
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

        totalSP         = (totalIncome - totalExpenses);
        Log.e(TAG,
                "Income: "              + IncomeListviewFragment.mainIncome +
                        " // Expenses: "        + ExpenseListviewFragment.mainExpenses +
                        " // Spending Power: "  + totalSP
        );
        sp.setText("$" + String.format("%.2f", totalSP));

        spObject = new ArrayList<>();
        spObject.add(totalSP);
        spObject.add(totalIncome);
        spObject.add(totalExpenses);


    }


    // Creates local storage file
    public void writeSP() {

        try {
            FileOutputStream fos = openFileOutput(spFile, this.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(spObject);
            Log.i(TAG, "Spending Power Saved Successfully");
            oos.close();

        } catch (Exception e) {
            Log.e(TAG, "SP Save Unsuccessful");
        }



    }

    private void readSP() {


        try {
            FileInputStream fin = openFileInput(spFile);
            ObjectInputStream oin = new ObjectInputStream(fin);

            spObject = (ArrayList<Float>) oin.readObject();
            oin.close();

        } catch (Exception e) {
            Log.e(TAG, "There are no files to pull");

            Toast.makeText(this, "No data Saved - Track your income & expenses!", Toast.LENGTH_LONG).show();

            updateDisplay();
        }

        totalSP         = spObject.get(0);
        totalIncome     = spObject.get(1);
        totalExpenses   = spObject.get(2);

        TextView sp = (TextView) findViewById(R.id.spendingPower);
        sp.setText("$" + String.format("%.2f", totalSP));
    }

}
