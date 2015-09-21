package com.example.xiaoxiaoli.mortgagecalculator;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MyActivity extends AppCompatActivity {
    public final static String EXTRA_MESSAGE = "com.example.xiaoxiaoli.mortgagecaculator.MESSAGE";
    private Spinner term;
    private Button btnCalculate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // If your minSdkVersion is 11 or higher, instead use:
        // getActionBar().setDisplayHomeAsUpEnabled(true);
        addListenerOnButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_my, menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_reset:
                reset();
                return true;
            case R.id.action_settings:
                openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addListenerOnButton() {
        btnCalculate = (Button) findViewById(R.id.calculate);
        btnCalculate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                term = (Spinner) findViewById(R.id.spinner_terms);
                EditText homeValue = (EditText)findViewById(R.id.input_home_value);
                EditText downPayment = (EditText)findViewById(R.id.input_down_payment);
                EditText interestRate = (EditText)findViewById(R.id.input_interest_rate);
                EditText propertyTaxRate = (EditText)findViewById(R.id.input_tax);

                if(validateField(homeValue, downPayment, interestRate, propertyTaxRate)){

                    Intent intent = new Intent(v.getContext(), DisplayMessageActivity.class);
                    intent.putExtra("home_value", homeValue.getText().toString());
                    intent.putExtra("down_payment", downPayment.getText().toString());
                    intent.putExtra("interest_rate", interestRate.getText().toString());
                    intent.putExtra("property_tax_rate", propertyTaxRate.getText().toString());
                    intent.putExtra("term", term.getSelectedItem().toString());
                    startActivity(intent);
                }

            }

        });
    }
    private boolean validateField(EditText homeValue, EditText downPayment, EditText interestRate,
                               EditText propertyTaxRate){
        //flags for field validation
        boolean hvFilled = false;//home value filled
        boolean dpFilled = false;//down payment filled
        boolean irFilled = false;//interest rate filled
        boolean ptFilled = false;//property tax filled

        if(homeValue.getText().toString().trim().equals("")){
            homeValue.setError("Home value is required!");
        }
        else{
            hvFilled=true;
        }

        if(downPayment.getText().toString().trim().equals("")){
            downPayment.setError("Down payment is required!");
        }
        else{
            dpFilled=true;
        }

        if(interestRate.getText().toString().trim().equals("")){
            interestRate.setError("Interest rate is required!");
        }
        else{
            irFilled=true;
        }

        if(propertyTaxRate.getText().toString().trim().equals("")){
            propertyTaxRate.setError("Property tax is required!");
        }
        else{
            ptFilled=true;
        }
        if(hvFilled==true&&dpFilled==true&&irFilled==true&&ptFilled==true)
            return true;
        else return false;
    }
    private void reset(){
        EditText text1 = (EditText)findViewById(R.id.input_home_value);
        text1.setText("");
        EditText text2 = (EditText)findViewById(R.id.input_down_payment);
        text2.setText("");
        EditText text3 = (EditText)findViewById(R.id.input_interest_rate);
        text3.setText("");
        EditText text4 = (EditText)findViewById(R.id.input_tax);
        text4.setText("");
        Spinner spinner = (Spinner)findViewById(R.id.spinner_terms);
        spinner.setSelection (0);
    }

    private void openSettings(){
        startActivity(new Intent(Settings.ACTION_SETTINGS));
    }
}
