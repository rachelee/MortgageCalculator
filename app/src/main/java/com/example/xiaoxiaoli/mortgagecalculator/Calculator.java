package com.example.xiaoxiaoli.mortgagecalculator;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by xiaoxiaoli on 9/20/15.
 */
public class Calculator {
    private double i;//monthly interest rate
    private double n;//total payments
    private double p;//principle amount
    private double t;//monthly property tax
    private double m; //monthly mortgage payment
    public Calculator(String hv, String dp, String ir,
                      String pt, String te){

        double homeValue = Double.parseDouble(hv);
        double downPayment = Double.parseDouble(dp);
        double interestRate = Double.parseDouble(ir);
        double propertyTaxRate = Double.parseDouble(pt);
        double term = Double.parseDouble(te);

        //calculate monthly interest rate
        i = (interestRate/100)/12;
        //calculate total payments
        n = term*12;
        //calculate principle amount
        p = homeValue-downPayment;
        //calculate monthly property tax
        t = (homeValue*propertyTaxRate/100)/12;
        //use M=P[i(1+i)^n]/[(1+i)^n -1]+property to calculate monthly mortgage payment
        m = p*(i*Math.pow((1+i),n))/(Math.pow((1+i),n)-1);


    }
    //calculate the total monthly payment(mortgage payment+property tax payment)
    public String getMonthlyPayment(){
        return String.format("%.2f", m+t);
    }
    //calculate total interest paid
    public String getInterestPaid(){
        return String.format("%.2f", (m*n)-p);
    }
    //calculate total property tax paid
    public String getTaxPaid(){
        return String.format("%.2f", (t*n));
    }
    //calculate payoff date
    public String getPayOffDate(){
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.MONTH, (int)n-1);
        Date date = calendar.getTime();
        String format = "MMM, yyyy";
        SimpleDateFormat df = new SimpleDateFormat(format);
        return df.format(date);
    }
}
