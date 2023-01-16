package com.cornez.shippingcalculator;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class MyActivity extends Activity {

    //DATA MODEL FOR SHIP ITEM
    static public ShipItem shipItem;
    static public int ex;
    static public double[] TDEE = {1.2,1.375,1.5,1.725,1.9};
    static public double bmr,tdee;

    //VIEW OBJECTS FOR LAYOUT UI REFERENCE
    static public EditText height;
    static public EditText weight;
    static public EditText age;

    static public TextView alarm;
    static public TextView mBMR;

    static public Button btn1;
    static public Button appin;
    static public Button ff;
    static public Spinner sp;

    static public Button lluu1;
    static public Button ssuu1;
    static public Button lluu2;
    static public Button ssuu2;
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
        setContentView(R.layout.activity_my_2);

        //CREATE THE DATA MODEL FOR STORING THE ITEM TO BE SHIPPED
        shipItem = new ShipItem();

        //TASK 3: ESTABLISH THE REFERENCES TO INPUT WEIGHT ELEMENT
        height = (EditText) findViewById(R.id.editTextTextPersonName);
        weight = (EditText) findViewById(R.id.editTextTextPersonName2);
        age = (EditText) findViewById(R.id.editTextTextPersonName3);

        //TASK 3: ESTABLISH THE REFERENCES TO OUTPUT ELEMENTS
        alarm = (TextView) findViewById(R.id.textView11);


        // add button
        btn1 = (Button) findViewById((R.id.button));
        appin = (Button) findViewById((R.id.appintroduce));
        ff = (Button) findViewById((R.id.fat));

        lluu1 = (Button) findViewById((R.id.lu1));
        ssuu1 = (Button) findViewById((R.id.su1));
        lluu2 = (Button) findViewById((R.id.lu2));
        ssuu2 = (Button) findViewById((R.id.su2));

        String[] act = {"沒啥運動","每周運動1-3天","每周運動3-5天","每周運動6-7天","無時無刻都在運動"};
        sp =  (Spinner) findViewById(R.id.spinner) ;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,act);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(spnOnItemSelected);
        appin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent showQuestion = new Intent(MyActivity.this, LastActivity.class);
                startActivity(showQuestion);
            }
        });
        ff.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent showQuestion = new Intent(MyActivity.this, SecondActivity.class);
                startActivity(showQuestion);
            }
        });

        weight.addTextChangedListener(weightTextWatcher);
        height.addTextChangedListener(heightTextWatcher);
        age.addTextChangedListener(ageTextWatcher);

    }

    public void reset(View view) {
        // reset all data
        shipItem.Reset();
        btn1.setText("男性");
        height.setText("");
        weight.setText("");
        age.setText("");
        alarm.setText("");
    }
    public void SU1(View view){
        SharedPreferences pref = getSharedPreferences("User1", MODE_PRIVATE);
        if(height.length() == 0 || weight.length() == 0 || age.length() == 0){
            alarm.setText("請輸入完整資料~~");
            return;
        }
        alarm.setText("");
        pref.edit()
                .putBoolean("saved",false)
                .putString("height",height.getText().toString())
                .putString("weight",weight.getText().toString())
                .putString("age", age.getText().toString())
                .putInt("mode",sp.getSelectedItemPosition())
                .putBoolean("gender",shipItem.getGender())
                .commit();
    }
    public void SU2(View view){
        SharedPreferences pref = getSharedPreferences("User2", MODE_PRIVATE);
        if(height.length() == 0 || weight.length() == 0 || age.length() == 0){
            alarm.setText("請輸入完整資料~~");
            return;
        }
        alarm.setText("");
        pref.edit()
                .putBoolean("saved",true)
                .putString("height",height.getText().toString())
                .putString("weight",weight.getText().toString())
                .putString("age", age.getText().toString())
                .putInt("mode",sp.getSelectedItemPosition())
                .putBoolean("gender",shipItem.getGender())
                .commit();
    }

    public void LU1(View view){
        SharedPreferences pref = getSharedPreferences("User1", MODE_PRIVATE);

        boolean save = pref.getBoolean("saved",false);
        if(save){
            alarm.setText("未有使用者資料");
            return;
        }
        height.setText(pref.getString("height",""));
        weight.setText(pref.getString("weight",""));
        age.setText(pref.getString("age",""));
        sp.setSelection(pref.getInt("mode",0));
        if(pref.getBoolean("gender",false) != shipItem.getGender())
            gender(view);
    }
    public void LU2(View view){
        SharedPreferences pref = getSharedPreferences("User2", MODE_PRIVATE);

        boolean save = pref.getBoolean("saved",false);
        if(!save){
            alarm.setText("未有使用者資料");
            return;
        }
        height.setText(pref.getString("height",""));
        weight.setText(pref.getString("weight",""));
        age.setText(pref.getString("age",""));
        sp.setSelection(pref.getInt("mode",0));
        if(pref.getBoolean("gender",false) != shipItem.getGender())
            gender(view);
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

    public void gender(View view) {
        // change gender
        shipItem.ChangeGender();
        // print new gender
        if(shipItem.getGender()) btn1.setText("男性");
        else btn1.setText("女性");
        alarm.setText("");

        compute();
    }




    public void compute() {
        if (height.length() == 0 || weight.length() == 0 || age.length() == 0) alarm.setText("請完整輸入!!!!!!!");
        else
        {
            double h = Double.parseDouble(height.getText().toString());
            double w = Double.parseDouble(weight.getText().toString());
            int a = Integer.parseInt(age.getText().toString());

            if (h > 0 && w > 0 && a > 0)
            {
                shipItem.Compute(h, w, a);
                bmr = Math.round(shipItem.getmBMR()*10.0)/10.0;
                tdee = (int)(shipItem.getmBMR() * TDEE[ex]);

            }
        }

    }
}
