package com.cornez.tapbuttoncounter;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {

    //MODEL
    private Counter count;
    private Integer[] fib0 = {0,1};
    private Integer[] pad0 = {1,1,1};
    private List<Integer> Fib = new ArrayList<Integer>(Arrays.asList(fib0));
    private List<Integer> Pad = new ArrayList<Integer>(Arrays.asList(pad0));
    //VIEW
    private TextView FP,Ans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        for(int i = 2;i<21;i++)
            Fib.add(Fib.get(i - 2)+ Fib.get(i - 1));
        for(int i = 3;i<21;i++)
            Pad.add(Pad.get(i - 2)+ Pad.get(i - 3));
        count = new Counter();
        FP = (TextView) findViewById(R.id.FP);
        Ans = (TextView) findViewById(R.id.Ans);
        FP.setText("Fibonacci Num. of " + count.getCount().toString() + " = " + Fib.get(count.getCount())+"\n" +
                "Padovan Num. of " + count.getCount().toString() + " = " + Pad.get(count.getCount()));

        Ans.setText("Fibonacci - Padovan = -1");
    }

    public void Inc(View view){
        count.addCount();
        FP.setText("Fibonacci Num. of " + count.getCount().toString() + " = " + Fib.get(count.getCount())+"\n" +
                "Padovan Num. of " + count.getCount().toString() + " = " + Pad.get(count.getCount()));
    }

    public void Dec(View view){
        count.subCount();
        FP.setText("Fibonacci Num. of " + count.getCount().toString() + " = " + Fib.get(count.getCount())+"\n" +
                "Padovan Num. of " + count.getCount().toString() + " = " + Pad.get(count.getCount()));
    }
    public void Comp(View view){
        Ans.setText("Fibonacci - Padovan = " + (Fib.get(count.getCount())-Pad.get(count.getCount())));
    }
    public void Reset(View view){
        count.setCount(0);
        FP.setText("Fibonacci Num. of " + count.getCount().toString() + " = " + Fib.get(count.getCount())+"\n" +
                "Padovan Num. of " + count.getCount().toString() + " = " + Pad.get(count.getCount()));

        Ans.setText("Fibonacci - Padovan = -1");
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
