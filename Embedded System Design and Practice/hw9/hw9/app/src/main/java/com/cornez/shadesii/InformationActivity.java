package com.cornez.shadesii;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class InformationActivity extends Activity {


    Timer timer;
    Integer time = 0;

    public static final String EXTRA_URL = "url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Need to check if Activity has been switched to landscape mode
        // If yes, finished and go back to the start Activity
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            finish();
            return;
        }
        setContentView(R.layout.information_fragment);
//時間在幾毫秒過後開始以多少毫秒執行


        //SHOW THE UP BUTTON IN THE ACTION BAR
        getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        String informationValue = intent.getStringExtra("Information");

        TextView info = (TextView) findViewById (R.id.textView1);
        info.setText(informationValue + "\n\nBrowsing Time: " + time.toString() + "sec.");

        timer = new Timer();//時間函示初始化
        //這邊開始跑時間執行緒
        final TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    time++;//時間倒數
                    info.setText(informationValue + "\n\nBrowsing Time: " + time.toString() + "sec.");
                });
            }
        };
        timer.schedule(task, 1000, 1000);
    }

}
