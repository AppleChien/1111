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

import java.util.Timer;
import java.util.TimerTask;


public class MyActivity extends Activity {
    private TextView Bingo;
    private TextView Wrong;
    private TextView Error;
    private Button reset;
    private LinearLayout mContainer;
    private Button[] buttons = new Button[49];
    private Character[] alphabet = new Character[26];
    private Character[] value = new Character[52];
    private boolean[] on = new boolean[36];
    private boolean click = false;
    private int[] ID = new int[2];
    private int bingo = 0,error = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        Bingo = (TextView)findViewById(R.id.bingo);
        Wrong = (TextView)findViewById(R.id.wrong);
        Error = (TextView)findViewById(R.id.error);
        reset = (Button) findViewById(R.id.reset);
        reset.setOnClickListener(resetListener);

        init(6);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        GridLayout gridLayout = (GridLayout)findViewById(R.id.root) ;
        gridLayout.setColumnCount(6);           // 設定GridLayout有幾行
        gridLayout.setRowCount(6);              // 設定GridLayout有幾列
        for(int i =0; i < 36 ; i++) {
            GridLayout.LayoutParams R = new GridLayout.LayoutParams();
            R.rowSpec = GridLayout.spec(i/6,1f);
            R.columnSpec = GridLayout.spec(i%6,1f);
            R.width = 0;
            R.height = 0;
            buttons[i] = new Button(this) ;
            buttons[i].setLayoutParams(R);
            buttons[i].setId(i);              // 設定Button的ID
            buttons[i].setOnClickListener(btnListener);
            gridLayout.addView(buttons[i]); // 按照格子的順序依序放近GridLayout*/
        }
        // or slightly better
        // buttons = new ArrayList<Button>(BUTTON_IDS.length);
    }

    private void init(int size) {
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
        for(i = 0;i < alpNums * 2;i++)
            swap(value,i,(int)(Math.random() * 36));

        ID[0] = 36;
        ID[1] = 36;
    }

    protected View createButton(int index){
        final Button btn=new Button(this);
        btn.setId(index);
        Log.d("da","id is" + btn.getId());
        btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        return btn;
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
            init(6);
            bingo = error = 0;
            int i;
            for(i = 0;i<36;i++){
                on[i] = false;
                buttons[i].setText(" ");
                Bingo.setText("完成 : " + bingo);
                Wrong.setText("錯誤 : " + error);
                Error.setText(" ");
            }
        }
    };
    private Runnable resetButton(){
        buttons[ID[0]].setText(" ");
        buttons[ID[1]].setText(" ");
        ID[0] = 36;
        ID[1] = 36;
        return null;
    }
    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onStop(){
        super.onStop();
    }

    @Override
    protected void onRestart(){
        super.onRestart();
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
