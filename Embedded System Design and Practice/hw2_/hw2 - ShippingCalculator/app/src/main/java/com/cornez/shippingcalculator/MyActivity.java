package com.cornez.shippingcalculator;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MyActivity extends Activity {

    //DATA MODEL FOR SHIP ITEM
    private ShipItem shipItem;

    //VIEW OBJECTS FOR LAYOUT UI REFERENCE
    private EditText height;
    private EditText weight;
    private EditText age;

    private TextView alarm;
    private TextView mBMR;

    private Button btn1;

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
        mBMR = (TextView) findViewById(R.id.textView14);

        // add button
        btn1 = (Button) findViewById((R.id.button));

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
    }

    public void reset(View view) {
        // reset all data
        shipItem.Reset();
        btn1.setText("男性");
        height.setText("");
        weight.setText("");
        age.setText("");
        mBMR.setText("");
        alarm.setText("");
    }

    public void compute(View view) {
        mBMR.setText("");
        alarm.setText("");
        if (height.length() == 0 || weight.length() == 0 || age.length() == 0) alarm.setText("請完整輸入!!!!!!!");
        else
        {
            double h = Integer.parseInt(height.getText().toString());
            double w = Double.parseDouble(weight.getText().toString());
            int a = Integer.parseInt(age.getText().toString());

            if (h > 0 && w > 0 && a > 0)
            {
                shipItem.Compute(h, w, a);
                mBMR.setText("BMR: " + Math.round(shipItem.getmBMR()*10.0)/10.0);

            }
            else alarm.setText("請完整輸入!!!!!!!");
        }


    }
}
