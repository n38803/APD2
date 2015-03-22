package com.fullsail.android.smartbudget;
/**
 * Shaun Thompson - ADP2
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.fullsail.android.smartbudget.dataclass.Income;

import java.util.ArrayList;


public class MainActivity extends Activity {

    final String TAG = "MAIN_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // TODO - create custom income button & set intents (milestone2)

        Button income   = (Button) findViewById(R.id.iButton);

        // create onClickListeners for each button w/execution of corresponding methods
        income.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        Intent incomeIntent = new Intent(MainActivity.this, IncomeActivity.class);
                        //addIntent.putExtra("contactName", mContactDataList.get());
                        incomeIntent.putExtra("Add", "From_MainActivity");
                        //startActivityForResult(addIntent, ADDREQUEST);
                        startActivity(incomeIntent);

                        Log.i(TAG, "Income Button Clicked");

                    }
                }
        );




        // TODO - create custom expense button & set intents (milestone2)

        Button expense   = (Button) findViewById(R.id.eButton);

        // create onClickListeners for each button w/execution of corresponding methods
        expense.setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {

                        Intent addIntent = new Intent(MainActivity.this, ExpenseActivity.class);
                        //addIntent.putExtra("contactName", mContactDataList.get());
                        addIntent.putExtra("Add", "From_MainActivity");
                        //startActivityForResult(addIntent, ADDREQUEST);
                        startActivity(addIntent);

                        Log.i(TAG, "Expense Button Clicked");

                    }
                }
        );

        // TODO - create custom logo (milestone2)


        // TODO - create dynamic update of spending power (milestone3)


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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
