package com.cornez.activitylifecycle;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Intent;

import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends Activity{
    static public boolean iscontinue = false;
    static public TextView Bingo;
    static public TextView Wrong;
    static public TextView Error;
    static public Button reset;
    static public Button[] buttons = new Button[49];
    static public int btnsize = 6;
    static public int pre = 6;
    static public Character[] alphabet = new Character[26];
    static public Character[] value = new Character[52];
    static public boolean[] on = new boolean[49];
    static public boolean click = false;
    static public int[] ID = new int[2];
    static public int bingo = 0,error = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        Bingo = (TextView)findViewById(R.id.bingo);
        Wrong = (TextView)findViewById(R.id.wrong);
        Error = (TextView)findViewById(R.id.error);
        reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(resetListener);

        if(!iscontinue){
            init(btnsize);
            Bingo.setText("完成 : " + bingo);
            Wrong.setText("錯誤 : " + error);
        }
        else {
            cont(pre);
            iscontinue = false;
            Bingo.setText("完成 : " + bingo);
            Wrong.setText("錯誤 : " + error);
        }

    }

    private void cont(int size) {
        int i = 0;
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        GridLayout gridLayout = (GridLayout)findViewById(R.id.root) ;
        gridLayout.setColumnCount(size);           // 設定GridLayout有幾行
        gridLayout.setRowCount(size);              // 設定GridLayout有幾列
        for(i =0; i < size*size ; i++) {
            GridLayout.LayoutParams R = new GridLayout.LayoutParams();
            R.rowSpec = GridLayout.spec(i/size,1f);
            R.columnSpec = GridLayout.spec(i%size,1f);
            R.width = 0;
            R.height = 0;
            buttons[i] = new Button(this) ;
            buttons[i].setLayoutParams(R);
            buttons[i].setId(i);              // 設定Button的ID
            buttons[i].setOnClickListener(btnListener);
            if(on[i])
                buttons[i].setText(value[i].toString());
            gridLayout.addView(buttons[i]); // 按照格子的順序依序放近GridLayout*/
        }
    }
    private void init(int size) {
        bingo = error = 0;
        int i = 0;
        int alpNums = size * size / 2;
        for(i = 0;i < 26;i++)
            alphabet[i] = (char)('A' + i);
        for(i = 0;i < 26;i++)
            swap(alphabet,i, (int) (Math.random() * 26));
        for(i = 0;i < alpNums;i++){
            value[2 * i] = alphabet[i];
            value[2 * i + 1] = alphabet[i];
        }
        if(size % 2 == 1)
            value[size * size] = '*';
        for(i = 0;i < size * size;i++)
            swap(value,i,(int)(Math.random() * (size*size)));

        ID[0] = (size*size);
        ID[1] = (size*size);


        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        GridLayout gridLayout = (GridLayout)findViewById(R.id.root) ;
        gridLayout.setColumnCount(size);           // 設定GridLayout有幾行
        gridLayout.setRowCount(size);              // 設定GridLayout有幾列
        for(i =0; i < size*size ; i++) {
            on[i] = false;
            GridLayout.LayoutParams R = new GridLayout.LayoutParams();
            R.rowSpec = GridLayout.spec(i/size,1f);
            R.columnSpec = GridLayout.spec(i%size,1f);
            R.width = 0;
            R.height = 0;
            buttons[i] = new Button(this) ;
            buttons[i].setLayoutParams(R);
            buttons[i].setId(i);              // 設定Button的ID
            buttons[i].setOnClickListener(btnListener);
            gridLayout.addView(buttons[i]); // 按照格子的順序依序放近GridLayout*/
        }
        pre = GameActivity.btnsize;
    }

    private void swap(Character[] arr,int i, int j){
        Character temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    private Button.OnClickListener btnListener = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if(on[id] || ID[0] == id){
                Error.setText("按鍵無效!!!!!");
                return;
            }
            else if(!click){
                click = true;
                on[id] = true;
                ID[0] = id;
                buttons[id].setText(value[id].toString());
            }
            else{
                click = false;
                on[id] = true;
                ID[1] =id;
                buttons[id].setText(value[id].toString());
                if(value[ID[0]] == value[ID[1]]){
                    bingo++;
                    Bingo.setText("完成 : " + bingo);
                    Wrong.setText("錯誤 : " + error);
                    Error.setText(" ");
                }
                else{
                    error++;
                    Bingo.setText("完成 : " + bingo);
                    Wrong.setText("錯誤 : " + error);
                    Error.setText(" ");
                    Timer time = new Timer();
                    TimerTask wrong = new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(resetButton());
                            time.cancel();
                        }
                    };
                    on[ID[0]] = false;
                    on[ID[1]] = false;
                    time.schedule(wrong,2000);
                }

            }
        }
    };

    private Button.OnClickListener resetListener = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            init(btnsize);
            bingo = error = 0;
            int i;
            GridLayout gridLayout = (GridLayout)findViewById(R.id.root) ;
            gridLayout.removeAllViews();
            for(i = 0;i<btnsize * btnsize;i++){
                on[i] = false;
                buttons[i].setText(" ");
                Bingo.setText("完成 : " + bingo);
                Wrong.setText("錯誤 : " + error);
                Error.setText(" ");
            }
            init(btnsize);
        }
    };

    public void setting(View view){
        Intent i = new Intent(getApplicationContext(),MyActivity.class);
        startActivity(i);
    }
    private Runnable resetButton(){
        buttons[ID[0]].setText(" ");
        buttons[ID[1]].setText(" ");
        ID[0] = 36;
        ID[1] = 36;
        return null;
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
