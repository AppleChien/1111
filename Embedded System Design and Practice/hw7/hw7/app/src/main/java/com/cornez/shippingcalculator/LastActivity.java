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

public class LastActivity extends Activity {

    //DATA MODEL FOR SHIP ITEM
    static public ShipItem shipItem;
    //VIEW OBJECTS FOR LAYOUT UI REFERENCE

    static public Button btn3;
    static public Button ddata;
    static public Button ff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.last);

        //CREATE THE DATA MODEL FOR STORING THE ITEM TO BE SHIPPED
        shipItem = new ShipItem();


        // add button
        btn3 = (Button) findViewById((R.id.button3));
        ddata = (Button) findViewById((R.id.data));
        ff = (Button) findViewById((R.id.fat));

        ddata.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent showQuestion = new Intent(LastActivity.this, MyActivity.class);
                startActivity(showQuestion);
            }
        });
        ff.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent showQuestion = new Intent(LastActivity.this, SecondActivity.class);
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


    public void datalisten() {

    }

    public void fatlisten() {

    }

    public void exit(View view) {
        // reset all data
        shipItem.Reset();

    }


}
