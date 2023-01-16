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
import android.widget.SeekBar;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


public class MyActivity extends Activity {
    private SeekBar mSeekBar;
    private Button Resume;
    private Button Restart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myactivity);
        Resume = (Button)findViewById(R.id.resume);
        Restart = (Button)findViewById(R.id.restart);
        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        Resume.setOnClickListener(ResumeListener);
        Restart.setOnClickListener(RestartListener);
        mSeekBar.setOnSeekBarChangeListener(seekBarOnSeekBarChange);

    }


    private SeekBar.OnSeekBarChangeListener seekBarOnSeekBarChange = new SeekBar.OnSeekBarChangeListener()
    {
        @Override
        public void onStopTrackingTouch(SeekBar seekBar)
        {
            //停止拖曳時觸發事件
            GameActivity.btnsize=seekBar.getProgress();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar)
        {
            //開始拖曳時觸發事件
            //num=seekBar.getProgress();
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
        {
            //拖曳途中觸發事件，回傳參數 progress 告知目前拖曳數值
            //num=seekBar.getProgress();
        }
    };

    private Button.OnClickListener RestartListener = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            //init(num);
            Intent i = new Intent(getApplicationContext(), GameActivity.class);
            startActivity(i);
            //bingo = error = 0;
        }
    };

    private Button.OnClickListener ResumeListener = new Button.OnClickListener(){
        @Override
        public void onClick(View v) {
            GameActivity.iscontinue = true;
            Intent i = new Intent(getApplicationContext(), GameActivity.class);
            startActivity(i);

        }
    };

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
