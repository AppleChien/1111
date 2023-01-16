package com.cornez.shippingcalculator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;

public class SecondActivity extends Activity {

    //DATA MODEL FOR SHIP ITEM
    static public ShipItem shipItem;
    static public int ex;
    static public double[] TDEE = {1.2,1.375,1.5,1.725,1.9};

    //VIEW OBJECTS FOR LAYOUT UI REFERENCE
    static public EditText height;
    static public EditText weight;
    static public EditText age;

    static public TextView alarm;
    static public TextView mBMR;

    static public Button appin;
    static public Button ddata;

    private TextWatcher weightTextWatcher = new TextWatcher() {
        //THE INPUT ELEMENT IS ATTACHED TO AN EDITABLE,
        //THEREFORE THESE METHODS ARE CALLED WHEN THE TEXT IS CHANGED

        public void onTextChanged(CharSequence s,
                                  int start, int before, int count){
            compute();
        }
        public void afterTextChanged(Editable s) {}
        public void beforeTextChanged(CharSequence s,
                                      int start, int count, int after){}
    };

    private TextWatcher heightTextWatcher = new TextWatcher() {
        //THE INPUT ELEMENT IS ATTACHED TO AN EDITABLE,
        //THEREFORE THESE METHODS ARE CALLED WHEN THE TEXT IS CHANGED

        public void onTextChanged(CharSequence s,
                                  int start, int before, int count){
            compute();
        }
        public void afterTextChanged(Editable s) {}
        public void beforeTextChanged(CharSequence s,
                                      int start, int count, int after){}
    };
    private TextWatcher ageTextWatcher = new TextWatcher() {
        //THE INPUT ELEMENT IS ATTACHED TO AN EDITABLE,
        //THEREFORE THESE METHODS ARE CALLED WHEN THE TEXT IS CHANGED

        public void onTextChanged(CharSequence s,
                                  int start, int before, int count){
            compute();
        }
        public void afterTextChanged(Editable s) {}
        public void beforeTextChanged(CharSequence s,
                                      int start, int count, int after){}
    };
    private AdapterView.OnItemSelectedListener spnOnItemSelected
            = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            ex = pos;
            compute();
        }
        public void onNothingSelected(AdapterView<?> parent) {
            //
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);

        //CREATE THE DATA MODEL FOR STORING THE ITEM TO BE SHIPPED
        shipItem = new ShipItem();
        //TASK 3: ESTABLISH THE REFERENCES TO OUTPUT ELEMENTS
        mBMR = (TextView) findViewById(R.id.textView14);

        // add button

        ddata = (Button) findViewById((R.id.data));
        appin = (Button) findViewById((R.id.appintroduce));


        mBMR.setText("BMR: " + MyActivity.bmr+ "\nTDEE = " + MyActivity.tdee);
        ddata.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent showQuestion = new Intent(SecondActivity.this, MyActivity.class);
                startActivity(showQuestion);
            }
        });
        appin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent showQuestion = new Intent(SecondActivity.this, LastActivity.class);
                startActivity(showQuestion);
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.cal_out, R.anim.calc_in);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void compute() {

    }
}
