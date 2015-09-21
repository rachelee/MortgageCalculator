package com.example.xiaoxiaoli.mortgagecalculator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class DisplayMessageActivity extends AppCompatActivity {
    private String homeValue;
    private String downPayment;
    private String interestRate;
    private String propertyTaxRate;
    private String term;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content view layout
        setContentView(R.layout.activity_display_message);
        // Get the message from the intent
        Intent intent = getIntent();

        //get home value
        homeValue = intent.getStringExtra("home_value");

        //get down payment
        downPayment = intent.getStringExtra("down_payment");

        //get interest rate
        interestRate = intent.getStringExtra("interest_rate");

        //get property tax rate
        propertyTaxRate = intent.getStringExtra("property_tax_rate");

        //get term
        term = intent.getStringExtra("term");


        //display calculation result
        displayResult();
        Button btn = (Button) findViewById(R.id.re_calculate);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate(v);
            }
        });

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
    private void displayResult(){

        Calculator calculator = new Calculator(homeValue, downPayment, interestRate,
                propertyTaxRate, term);

        //set monthly payment
        EditText text1 = (EditText)findViewById(R.id.monthly_payment_result);
        text1.setText(calculator.getMonthlyPayment());
        text1.setTextColor(Color.BLACK);
        text1.setEnabled(false);
        //set interest paid
        EditText text2 = (EditText)findViewById(R.id.interest_paid_result);
        text2.setText(calculator.getInterestPaid());
        text2.setTextColor(Color.BLACK);
        text2.setEnabled(false);
        //set tax paid
        EditText text3 = (EditText)findViewById(R.id.tax_paid_result);
        text3.setText(calculator.getTaxPaid());
        text3.setTextColor(Color.BLACK);
        text3.setEnabled(false);
        //set payoff date
        EditText text6 = (EditText)findViewById(R.id.payoff_date_result);
        text6.setText(calculator.getPayOffDate());
        text6.setTextColor(Color.BLACK);
        text6.setEnabled(false);

        //set interest rate
        EditText text4 = (EditText)findViewById(R.id.edit_interest_rate);
        text4.setText(interestRate, TextView.BufferType.EDITABLE);

        //set property tax rate
        EditText text5 = (EditText)findViewById(R.id.edit_property_tax);
        text5.setText(propertyTaxRate, TextView.BufferType.EDITABLE);

        //set term
        Spinner spinner = (Spinner)findViewById(R.id.edit_spinner_terms);
        for(int i=0;i<spinner.getCount();i++){
            if(spinner.getItemAtPosition(i).equals(term)){
                spinner.setSelection(i);
                break;
            }
        }


    }

    public void calculate(View v){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        Spinner inputTerm = (Spinner) findViewById(R.id.edit_spinner_terms);
        term = inputTerm.getSelectedItem().toString();
        EditText inputInterestRate = (EditText)findViewById(R.id.edit_interest_rate);
        interestRate = inputInterestRate.getText().toString();
        EditText inputPropertyTaxRate = (EditText)findViewById(R.id.edit_property_tax);
        propertyTaxRate = inputPropertyTaxRate.getText().toString();

        displayResult();
    }



}
