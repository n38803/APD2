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

import com.fullsail.android.smartbudget.dataclass.Income;
import com.fullsail.android.smartbudget.fragments.IncomeListviewFragment;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class IncomeActivity extends Activity implements IncomeListviewFragment.IncomeListener {


    final String TAG = "INCOME_ACTIVITY";
    private final String saveFile = "SmartBudget_Income.txt";


    public static final int ADDREQUEST = 2;



    public static ArrayList<Income> mIncomeList;


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
        setContentView(R.layout.activity_income);


        // check for instance state & create both fragments
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.incomeContainer, new IncomeListviewFragment())
                    .commit();
        }

        readFile();



    }


    public void onClick(View v) {



        // Create Intent to retrieve added information
        Intent addIntent = new Intent(IncomeActivity.this, AddActivity.class);
        addIntent.putExtra("Add", "From_Income");
        startActivityForResult(addIntent, ADDREQUEST);


        Log.i(TAG, "Add Button Clicked");


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
            return true;
        }
        return super.onOptionsItemSelected(item);




    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // TODO - RETRIEVE INFO FROM FORM RESULTS

        if(requestCode == ADDREQUEST && resultCode == RESULT_OK){
            String mTitle = data.getStringExtra("incomeTitle");
            String mAmount = data.getStringExtra("incomeAmount");
            String action = data.getStringExtra("action");

            float convertAmount = Float.parseFloat(mAmount);

            mIncomeList.add(new Income(mTitle, convertAmount));


            IncomeListviewFragment nf = (IncomeListviewFragment) getFragmentManager().findFragmentById(R.id.incomeContainer);
            nf.updateListData();

            writeFile();



            if (action.equals("add")){
                Toast.makeText(this, mTitle + " added to Income List.", Toast.LENGTH_LONG).show();

            }
        }

    }


    // -[ INTERFACE METHODS --------------------------------------------

    @Override
    public void viewIncome(int position){

        // TODO - Implement View Option
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
    public ArrayList<Income> getIncome() {
        return mIncomeList;
    }


    // -[ STORAGE METHODS ----------------------------------------------

    // Creates local storage file
    private void writeFile() {

        try {
            FileOutputStream fos = openFileOutput(saveFile, this.MODE_PRIVATE);


            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(mIncomeList);
            Log.i(TAG, "Object Saved Successfully");
            oos.close();

        } catch (Exception e) {
            Log.e(TAG, "Save Unsuccessful");
        }


    }

    private void readFile() {


        try {
            FileInputStream fin = openFileInput(saveFile);
            ObjectInputStream oin = new ObjectInputStream(fin);
            mIncomeList = (ArrayList<Income>) oin.readObject();
            oin.close();

        } catch (Exception e) {
            Log.e(TAG, "There are no files to pull");

            Toast.makeText(this, "No data Saved - Static information Populated.", Toast.LENGTH_LONG).show();

            // static population of data
            mIncomeList = new ArrayList<Income>();
            mIncomeList.add(new Income("Tim owes money", 1000));
            mIncomeList.add(new Income("Paycheck", 2000));
            mIncomeList.add(new Income("Paycheck", 3000));


            writeFile();
        }
    }

    // -[ OPERATIONAL METHODS ----------------------------------------------

    public void openAdd(){

    }
}