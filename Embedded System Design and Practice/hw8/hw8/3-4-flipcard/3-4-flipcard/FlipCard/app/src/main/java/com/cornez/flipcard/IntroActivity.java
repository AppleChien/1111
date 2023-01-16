package com.cornez.flipcard;

import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.os.Handler;
import android.os.SystemClock;


public class IntroActivity extends Activity implements View.OnClickListener {
    private Button day;
    private Button info;
    private Button exit;
    Bundle bundle;
    private ShipItem num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data);

        num = new ShipItem();
        bundle = getIntent().getExtras();
        num.set(bundle.getDouble("h"),bundle.getDouble("w"),bundle.getInt("age"),bundle.getBoolean("bg"),bundle.getInt("mode"));
        String nameee =bundle.getString("n");
        day = (Button) findViewById(R.id.day);
        day.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent showQuestion = new Intent(IntroActivity.this, AnswerActivity.class);
                bundle = new Bundle();
                bundle.putString("n",nameee);
                bundle.putDouble("w",num.getWeight());
                bundle.putDouble("h",num.getHeight());
                bundle.putInt("age",num.getAge());
                bundle.putBoolean("bg",num.getbg());
                bundle.putInt("mode",num.getmode());

                showQuestion.putExtras(bundle);
                startActivity(showQuestion);
                overridePendingTransition(R.anim.question_in, R.anim.answer_out);
            }
        });

        info = (Button) findViewById(R.id.info);
        info.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent showQuestion = new Intent(IntroActivity.this, QuestionActivity.class);
                bundle = new Bundle();
                bundle.putString("n",nameee);
                bundle.putDouble("w",num.getWeight());
                bundle.putDouble("h",num.getHeight());
                bundle.putInt("age",num.getAge());
                bundle.putBoolean("bg",num.getbg());
                bundle.putInt("mode",num.getmode());

                showQuestion.putExtras(bundle);
                startActivity(showQuestion);
                overridePendingTransition(R.anim.answer_in, R.anim.question_out);
            }
        });

        exit = (Button) findViewById(R.id.exit);
        exit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //finish();
                //android.os.Process.killProcess(android.os.Process.myPid());
                Intent showQuestion = new Intent(IntroActivity.this, QuestionActivity.class);
                showQuestion.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                showQuestion.putExtra("EXIT", true);
                startActivity(showQuestion);
                //System.exit(0);
            }
        });



    }



    @Override
    public void onClick(View v) {
       ;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.my, menu);
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

