package com.example.xiaoxiaoli.mortgagecalculator;

/**
 * Created by xiaoxiaoli on 9/20/15.
 */
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class FragmentOutput extends Fragment {
    private String homeValue;
    private String downPayment;
    private String interestRate;
    private String propertyTaxRate;
    private String term;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //add item to action bar
        setHasOptionsMenu(true);

    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment

        return inflater.inflate(
                R.layout.output_fragment, container, false);
    }
//    public void onCreateOptionsMenu(
//            Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.input_fragment_actions, menu);
//    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.v("OutputFragment", "onActivityCreated()");
        if (savedInstanceState != null) {
            homeValue = savedInstanceState.getString("homeValue");
            downPayment = savedInstanceState.getString("downPayment");
            interestRate = savedInstanceState.getString("interestRate");
            propertyTaxRate = savedInstanceState.getString("propertyTaxRate");
            term = savedInstanceState.getString("term");

        }

        //display the saved instance state data
        display();

        //set listener on calculate button
        Button btn = (Button) getView().findViewById(R.id.re_calculate);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate(v);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("homeValue", homeValue);
        outState.putString("downPayment", downPayment);
        outState.putString("interestRate", interestRate);
        outState.putString("propertyTaxRate", propertyTaxRate);
        outState.putString("term", term);
    }

    public void displayResult(String homeValue, String downPayment, String interestRate,
                              String propertyTaxRate, String term) {
        this.homeValue=homeValue;
        this.downPayment=downPayment;
        this.interestRate=interestRate;
        this.propertyTaxRate=propertyTaxRate;
        this.term=term;
        display();
    }

    public void updateResult(String homeValue, String downPayment, String interestRate,
                              String propertyTaxRate, String term) {
        this.homeValue = homeValue;
        this.downPayment = downPayment;
        this.interestRate = interestRate;
        this.propertyTaxRate = propertyTaxRate;
        this.term = term;
    }
    private void display(){
        Calculator calculator = new Calculator(homeValue, downPayment, interestRate,
                propertyTaxRate, term);

        View v = getView();
        //set monthly payment
        EditText text1 = (EditText) v.findViewById(R.id.monthly_payment_result);
        text1.setText(calculator.getMonthlyPayment());
        text1.setTextColor(Color.BLACK);
        text1.setEnabled(false);
        //set interest paid
        EditText text2 = (EditText) v.findViewById(R.id.interest_paid_result);
        text2.setText(calculator.getInterestPaid());
        text2.setTextColor(Color.BLACK);
        text2.setEnabled(false);
        //set tax paid
        EditText text3 = (EditText) v.findViewById(R.id.tax_paid_result);
        text3.setText(calculator.getTaxPaid());
        text3.setTextColor(Color.BLACK);
        text3.setEnabled(false);
        //set payoff date
        EditText text6 = (EditText) v.findViewById(R.id.payoff_date_result);
        text6.setText(calculator.getPayOffDate());
        text6.setTextColor(Color.BLACK);
        text6.setEnabled(false);

        //set interest rate
        EditText text4 = (EditText) v.findViewById(R.id.edit_interest_rate);
        text4.setText(interestRate, TextView.BufferType.EDITABLE);

        //set property tax rate
        EditText text5 = (EditText) v.findViewById(R.id.edit_property_tax);
        text5.setText(propertyTaxRate, TextView.BufferType.EDITABLE);

        //set term
        Spinner spinner = (Spinner) v.findViewById(R.id.edit_spinner_terms);
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(term)) {
                spinner.setSelection(i);
                break;
            }
        }
    }
    public void calculate(View v){
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        Spinner inputTerm = (Spinner) getView().findViewById(R.id.edit_spinner_terms);
        EditText inputInterestRate = (EditText)getView().findViewById(R.id.edit_interest_rate);
        EditText inputPropertyTaxRate = (EditText)getView().findViewById(R.id.edit_property_tax);
        if(validateField(inputInterestRate, inputPropertyTaxRate)){
            term = inputTerm.getSelectedItem().toString();
            interestRate = inputInterestRate.getText().toString();
            propertyTaxRate = inputPropertyTaxRate.getText().toString();

            display();
        }

    }
    private boolean validateField(EditText interestRate, EditText propertyTaxRate){
        //flags for field validation
        boolean irFilled = false;//interest rate filled
        boolean ptFilled = false;//property tax filled

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
        if(irFilled==true&&ptFilled==true)
            return true;
        else return false;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            // Other case statements...
            case android.R.id.home:
                getFragmentManager().popBackStack();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }



}