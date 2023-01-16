package com.cornez.flipcard;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.InputFilter;


import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;


public class AnswerActivity extends Activity {
    //DATA MODEL FOR SHIP ITEM
    private ShipItem num;

    //VIEW OBJECTS FOR LAYOUT UI REFERENCE
    private EditText Weight;
    private EditText Height;
    private EditText Age;
    private TextView BMR;
    private TextView TDEE;
    private TextView bg;
    private Spinner sp;
    boolean user1;
    boolean user2;

    private Button info;
    private Button intro;
    Bundle bundle;
    private TextView BMRtext;//////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);

        //CREATE THE DATA MODEL FOR STORING THE ITEM TO BE SHIPPED
        num = new ShipItem();

        BMR = (TextView) findViewById(R.id.textView10);
        TDEE = (TextView) findViewById(R.id.textView12);

        bundle = getIntent().getExtras();
        num.set(bundle.getDouble("h"),bundle.getDouble("w"),bundle.getInt("age"),bundle.getBoolean("bg"),bundle.getInt("mode"));
        String nameee =bundle.getString("n");
        //BMRtext= (TextView) findViewById(R.id.textView9);
        //BMRtext.setText(nameee);

        info = (Button) findViewById(R.id.info);
        info.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent showQuestion = new Intent(AnswerActivity.this, QuestionActivity.class);
                bundle = new Bundle();
                bundle.putString("n",nameee.toString());
                bundle.putDouble("w",num.getWeight());
                bundle.putDouble("h",num.getHeight());
                bundle.putInt("age",num.getAge());
                bundle.putBoolean("bg",num.getbg());
                bundle.putInt("mode",num.getmode());
                bundle.putString("n",nameee);
                showQuestion.putExtras(bundle);
                startActivity(showQuestion);
                overridePendingTransition(R.anim.question_in, R.anim.answer_out);
            }
        });

        intro = (Button) findViewById(R.id.intro);
        intro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent showQuestion = new Intent(AnswerActivity.this, IntroActivity.class);
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

        comp();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //橫豎屏切換前調用，保存用戶想要保存的數據，以下是樣例

        ;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // 屏幕切換完畢後調用用戶存儲的數據，以下爲樣例：
        if(savedInstanceState != null) {
            ;
        }
    }



    public void comp(){//<我先用按鈕來時做看對不對，擬改監聽時可以改掉>View view
        String Height_textip=num.getHeight().toString().trim();//Height.getText().toString().trim()
        String Height_textinfo =num.getHeight().toString();
        String Weight_textip=num.getWeight().toString().trim();
        String Weight_textinfo =num.getWeight().toString();
        String Age_textip=num.getAge().toString().trim();
        String Age_textinfo =num.getAge().toString();
        if(null == Age_textinfo || "".equals(Age_textinfo)||Age_textip.isEmpty()||
                null == Weight_textinfo || "".equals(Weight_textinfo)||Weight_textip.isEmpty()||
                null == Height_textinfo || "".equals(Height_textinfo)||Height_textip.isEmpty())
        {
            //BMR.setText("請完整輸入！");
            //TDEE.setText("請完整輸入！");//<沒有這樣的話沒輸入然後按按鈕會爆炸，等你弄到監聽裡可以改掉>
        }//判斷Info輸入框是否為空//判斷IP輸入框是否為空
        ///////////////////////////////////////////////判斷是否輸入完全
        else{
            //num.mode(sp.getSelectedItemPosition());
            //num.set(Double.parseDouble(Height.getText().toString()),Double.parseDouble(Weight.getText().toString()), Integer.parseInt(Age.getText().toString()));
            BMR.setText(num.getBMR().toString());
            TDEE.setText(num.getTDEE().toString());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.question, menu);
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
