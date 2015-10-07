package com.example.xiaoxiaoli.mortgagecalculator;

/**
 * Created by xiaoxiaoli on 9/20/15.
 */
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class FragmentInput extends Fragment {
    private Spinner term;
    OnButtonClickListener mListener;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //add item to action bar
        setHasOptionsMenu(true);
    }
//    @Override
//    public void onCreateOptionsMenu(
//            Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.input_fragment_actions, menu);
//    }
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //Inflate the layout for this fragment
//        if(container == null)
//            return null;
        return inflater.inflate(
                R.layout.input_fragment, container, false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            // Other case statements...

            default:
                return super.onOptionsItemSelected(item);
        }

    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.v("ListFragment", "onActivityCreated().");
        Log.v("ListsavedInstanceState", savedInstanceState == null ? "true" : "false");

        //Generate list View from ArrayList
        addListenerOnButton();

    }
    // Container Activity must implement this interface
    public interface OnButtonClickListener {
        public void onButtonClick(String hv, String dp, String ir, String pt, String te);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnButtonClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnClickListener");
        }
    }
    public void addListenerOnButton() {
        Button btnCalculate = (Button) getView().findViewById(R.id.calculate);
        btnCalculate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //send the variables to host activity

                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                term = (Spinner) getView().findViewById(R.id.spinner_terms);
                EditText homeValue = (EditText) getView().findViewById(R.id.input_home_value);
                EditText downPayment = (EditText) getView().findViewById(R.id.input_down_payment);
                EditText interestRate = (EditText) getView().findViewById(R.id.input_interest_rate);
                EditText propertyTaxRate = (EditText) getView().findViewById(R.id.input_tax);

                if (validateField(homeValue, downPayment, interestRate, propertyTaxRate)) {
                    mListener.onButtonClick(homeValue.getText().toString(), downPayment.getText().toString(),
                            interestRate.getText().toString(), propertyTaxRate.getText().toString(),
                            term.getSelectedItem().toString());

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



}