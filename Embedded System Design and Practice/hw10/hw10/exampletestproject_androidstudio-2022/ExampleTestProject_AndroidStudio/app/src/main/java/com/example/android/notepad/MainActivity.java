package com.example.android.notepad;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    //MODEL
    private Counter count;
    private Counter count1;
    private Counter count2;

    //VIEW
    private TextView display;
    private TextView display7;
    private TextView display8;
    private TextView display9;
    private TextView display10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        count = new Counter();
        count1 = new Counter();
        count2 = new Counter();
        display = (TextView) findViewById(R.id.textView);
        display7 = (TextView) findViewById(R.id.textView7);
        display8 = (TextView) findViewById(R.id.textView8);
        display9 = (TextView) findViewById(R.id.textView9);
        display10 = (TextView) findViewById(R.id.textView10);
    }


    public void countTap(View view){
        count.addCount();
        count1.same(count.getCount());
        count2.same(count.getCount());
        display.setText(count.getCount().toString());
        display7.setText(count.getCount().toString());
    }

    public void countsubTap(View view){
        count.subCount();
        count1.same(count.getCount());
        count2.same(count.getCount());
        display.setText(count.getCount().toString());
        display7.setText(count.getCount().toString());
    }
    public void countcoTap(View view){
        count.onclick();
        count1.onclick();
        count2.onclick();
        display8.setText(count1.getF(count1.getCount()).toString());
        display9.setText(count2.getP(count2.getCount()).toString());
        //count1.sub(count2.getCount());
        display10.setText(count1.sub(count2.getCount()).toString());
        count.lock(1);
        count1.lock(1);
        count2.lock(1);
    }

    public void res(View view){
        count.same(0);
        count.lock(2);
        count1.lock(2);
        count2.lock(2);
        count1.same(0);
        count2.same(0);
        display.setText(count.getCount().toString());
        display7.setText(count.getCount().toString());
        display8.setText(count1.getF(count1.getCount()).toString());
        display9.setText(count2.getP(count2.getCount()).toString());
        display10.setText(count1.sub(count2.getCount()).toString());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu;
        // this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
}
