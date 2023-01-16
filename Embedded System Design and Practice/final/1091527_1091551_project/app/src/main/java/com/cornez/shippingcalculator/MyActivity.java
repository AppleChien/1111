package com.cornez.shippingcalculator;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.Arrays;

public class MyActivity extends Activity {

    //DATA MODEL FOR SHIP ITEM
    static public bug bug_test = new bug("https://www.cpc.com.tw/GetOilPriceJson.aspx?type=TodayOilPriceString");
    static public String[] prices;
    static public int ex;

    //VIEW OBJECTS FOR LAYOUT UI REFERENCE


    static public TextView price_92;
    static public TextView price_95;
    static public TextView price_98;
    static public TextView price_cha;

    static public Button mmoney;
    static public Button mmap;
    static public Button sshop;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;


    Runnable runnable = new Thread(){
        public void run(){
            try{
                runOnUiThread(() -> {
                    /*price_92.setText(bug_test.getText()[0]);
                    price_95.setText(bug_test.getText()[1]);
                    price_98.setText(bug_test.getText()[2]);
                    price_cha.setText(bug_test.getText()[3]);*/
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
    Thread thread = new Thread(runnable);

    private AdapterView.OnItemSelectedListener spnOnItemSelected
            = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {

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
        price_92 = (TextView)findViewById(R.id.price_92);
        price_95 = (TextView)findViewById(R.id.price_95);
        price_98 = (TextView)findViewById(R.id.price_98);
        price_cha = (TextView)findViewById(R.id.price_cha);

        // add button
        mmoney = (Button) findViewById((R.id.money));
        mmap = (Button) findViewById((R.id.map));
        sshop = (Button) findViewById((R.id.shop));

        mmoney.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent showQuestion = new Intent(MyActivity.this, SecondActivity.class);
                startActivity(showQuestion);
            }
        });
        mmap.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent showQuestion = new Intent(MyActivity.this, LastActivity.class);
                startActivity(showQuestion);
            }
        });
        sshop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent showQuestion = new Intent(MyActivity.this, oilActivity.class);
                startActivity(showQuestion);
            }
        });
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        prices = bug_test.getText();
        if(prices != null)
            Log.e("price","found");
        else
            Log.e("prices","notFound");
        price_92.setText(prices[0] + "元");
        price_95.setText(prices[1] + "元");
        price_98.setText(prices[2] + "元");
        price_cha.setText(prices[3] + "元");


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }

    }


    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.cal_out, R.anim.calc_in);

        //crawl();
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



}
