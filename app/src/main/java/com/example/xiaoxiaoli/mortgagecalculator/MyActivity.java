package com.example.xiaoxiaoli.mortgagecalculator;


import android.app.FragmentManager;
import android.app.FragmentManager.OnBackStackChangedListener;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.xiaoxiaoli.mortgagecalculator.FragmentInput.OnButtonClickListener;


public class MyActivity extends AppCompatActivity implements OnButtonClickListener {
    boolean outputPage = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //add up action on action bar

        getFragmentManager().addOnBackStackChangedListener(new OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                shouldDisplayHomeUp();
            }
        });

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_my);

        if(savedInstanceState == null) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            FragmentInput fgInput = new FragmentInput();
            ft.add(R.id.fragment_input, fgInput, "InputFragment");
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            ft.commit();
        }

        if(findViewById(R.id.fragment_output) != null){
            outputPage = true;
            getFragmentManager().popBackStack();

            FragmentOutput opFragment = (FragmentOutput) getFragmentManager().findFragmentById(R.id.fragment_output);
            if(opFragment == null){
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                opFragment = new FragmentOutput();
                opFragment.updateResult("100000", "20000", "3.75", "1.25", "15");
                ft.replace(R.id.fragment_output, opFragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                ft.commit();
            }
        }

    }

    @Override
    public void onButtonClick(String homeValue, String downPayment, String interestRate,
                              String taxRate, String term) {
        Log.v("MyActivity", "home value: " + homeValue);

        if(outputPage){
            FragmentOutput fgOutput = (FragmentOutput)
                    getFragmentManager().findFragmentById(R.id.fragment_output);
            fgOutput.displayResult(homeValue, downPayment, interestRate, taxRate, term);
        }
        else{
            FragmentOutput fgOutput = new FragmentOutput();
            fgOutput.updateResult(homeValue, downPayment, interestRate, taxRate, term);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_input, fgOutput);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(null);
            getFragmentManager().getBackStackEntryCount();
            ft.commit();
        }
    }
    public void shouldDisplayHomeUp(){
        //Enable Up button only  if there are entries in the back stack
        boolean canback = getFragmentManager().getBackStackEntryCount()>0;
        getSupportActionBar().setDisplayHomeAsUpEnabled(canback);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.input_fragment_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            // Other case statements...
            case R.id.action_reset:
                reset();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    private void reset(){
        if(findViewById(R.id.input_home_value) != null){
            EditText text1 = (EditText)findViewById(R.id.input_home_value);
            text1.setText("");
            EditText text2 = (EditText)findViewById(R.id.input_down_payment);
            text2.setText("");
            EditText text3 = (EditText)findViewById(R.id.input_interest_rate);
            text3.setText("");
            EditText text4 = (EditText)findViewById(R.id.input_tax);
            text4.setText("");
            Spinner spinner1 = (Spinner)findViewById(R.id.spinner_terms);
            spinner1.setSelection (0);
        }

        if(findViewById(R.id.monthly_payment_result) != null){
            EditText text5 = (EditText)findViewById(R.id.monthly_payment_result);
            text5.setText("");
            EditText text6 = (EditText)findViewById(R.id.edit_interest_rate);
            text6.setText("");
            EditText text7 = (EditText)findViewById(R.id.edit_property_tax);
            text7.setText("");
            EditText text8 = (EditText)findViewById(R.id.payoff_date_result);
            text8.setText("");
            EditText text9 = (EditText)findViewById(R.id.interest_paid_result);
            text9.setText("");
            EditText text10 = (EditText)findViewById(R.id.tax_paid_result);
            text10.setText("");
            Spinner spinner2 = (Spinner)findViewById(R.id.edit_spinner_terms);
            spinner2.setSelection (0);
        }
    }





}
