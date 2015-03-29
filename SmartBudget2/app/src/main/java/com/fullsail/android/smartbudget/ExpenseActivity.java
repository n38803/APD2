package com.fullsail.android.smartbudget;
/**
 * Shaun Thompson - ADP2
 */
import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fullsail.android.smartbudget.dataclass.Expenses;
import com.fullsail.android.smartbudget.fragments.ExpenseListviewFragment;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class ExpenseActivity extends Activity implements ExpenseListviewFragment.ExpenseListener {


    final String TAG = "EXPENSE_ACTIVITY";
    private final String saveFile = "SmartBudget_Expenses.txt";


    public static final int ADDREQUEST = 1;

    public static ArrayList<Expenses> mExpenseList;


    private Context mContext;

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context mContext) {
        this.mContext = mContext;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);


        // check for instance state & create both fragments
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.expenseContainer, new ExpenseListviewFragment())
                    .commit();
        }

        readFile();

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
        if (id == R.id.action_add) {


            // Create Intent to retrieve added information
            Intent addIntent = new Intent(ExpenseActivity.this, AddActivity.class);
            addIntent.putExtra("Add", "From_Expense");
            startActivityForResult(addIntent, ADDREQUEST);


            Log.i(TAG, "Add Button Clicked");
        }
        return super.onOptionsItemSelected(item);




    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // RETRIEVE INFO FROM FORM RESULTS

        if(requestCode == ADDREQUEST && resultCode == RESULT_OK){
            String mTitle = data.getStringExtra("expenseTitle");
            String mAmount = data.getStringExtra("expenseAmount");
            String action = data.getStringExtra("action");

            float convertAmount = Float.parseFloat(mAmount);

            mExpenseList.add(new Expenses(mTitle, convertAmount));


            ExpenseListviewFragment nf = (ExpenseListviewFragment) getFragmentManager().findFragmentById(R.id.expenseContainer);
            nf.updateListData();

            writeFile();



            if (action.equals("add")){
                Toast.makeText(this, mTitle + " added to Expense List.", Toast.LENGTH_LONG).show();

            }
        }

    }


    // -[ INTERFACE METHODS --------------------------------------------

    @Override
    public void viewExpense(int position){

        // TODO - Implement View Option ???
        Log.i(TAG, "Clicked Position " + position);
    /*
        // Declare Intent
        Intent detailIntent = new Intent(this, DetailsActivity.class);

        // pass position from list into intent by using constant from detail activity
        // detailIntent.putExtra("APP", true);
        detailIntent.putExtra(DetailsActivity.EXTRA_ITEM, mIncomeList.get(position));


        // start detail activity by passing intent we wish to load
        startActivity(detailIntent);
    */
    }

    @Override
    public ArrayList<Expenses> getExpenses() {
        return mExpenseList;
    }


    // -[ STORAGE METHODS ----------------------------------------------

    // Creates local storage file
    public void writeFile() {

        try {
            FileOutputStream fos = openFileOutput(saveFile, this.MODE_PRIVATE);


            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(mExpenseList);
            Log.i(TAG, "Expense Object Saved Successfully");
            oos.close();

        } catch (Exception e) {
            Log.e(TAG, "Expense Object Save Unsuccessful");
        }


    }

    private void readFile() {


        try {
            FileInputStream fin = openFileInput(saveFile);
            ObjectInputStream oin = new ObjectInputStream(fin);
            mExpenseList = (ArrayList<Expenses>) oin.readObject();
            oin.close();

        } catch (Exception e) {
            Log.e(TAG, "There are no files to pull");

            Toast.makeText(this, "No data Saved - Static information Populated.", Toast.LENGTH_LONG).show();

            // static population of data
            mExpenseList = new ArrayList<Expenses>();
            mExpenseList.add(new Expenses("Tim borrowed money", 100));
            mExpenseList.add(new Expenses("Cable", 200));
            mExpenseList.add(new Expenses("Car Payment", 300));


            writeFile();
        }
    }

    // -[ OPERATIONAL METHODS ----------------------------------------------

}