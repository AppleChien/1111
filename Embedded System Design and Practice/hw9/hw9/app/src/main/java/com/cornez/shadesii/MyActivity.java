package com.cornez.shadesii;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReference;


public class MyActivity extends Activity implements
        MyListFragment.OnItemSelectedListener {

    Timer timer;
    Integer time = 0;
    boolean[] can = {false,false};
    AtomicReference<Integer> pretime = new AtomicReference<>(0);
    TimerTask task;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

    }

    @Override
    public void onColorItemSelected(String link) {
        time = 0;
        //CHECK IF FRAGMENT2 EXISTS IN THIS LAYOUT
        InformationFragment fragment2 = (InformationFragment) getFragmentManager()
                .findFragmentById(R.id.fragment2);
        //A TWO PANE CONFIGURATION
        if (fragment2 != null && fragment2.isInLayout()) {
            fragment2.setText(link);

            timer = new Timer();//時間函示初始化
            //這邊開始跑時間執行緒
            if(task != null){
                task.cancel();
                task = null;
            }
            task = new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(() -> {
                        time++;//時間倒數
                        pretime.set(time);
                        fragment2.setText(link + "\n\nBrowsing Time: " + time.toString() + "sec.");
                    });
                }
            };
            timer.schedule(task, 1000, 1000);

        }
        //A SINGLE-PANE CONFIGURATION -
        //  IF FRAGMENT 2 DOES NOT EXIST IN THIS LAYOUT, THEN ACTIVATE THE NEXT ACTIVITY
        else {
            Intent intent = new Intent (this, InformationActivity.class);
            intent.putExtra("Information", link);
            startActivity (intent);
        }
    }

}
