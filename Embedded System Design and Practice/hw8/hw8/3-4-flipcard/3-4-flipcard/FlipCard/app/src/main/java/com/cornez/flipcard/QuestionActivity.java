package com.cornez.flipcard;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.InputFilter;


import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;


public class QuestionActivity extends Activity {
    //DATA MODEL FOR SHIP ITEM
    private ShipItem num;

    //VIEW OBJECTS FOR LAYOUT UI REFERENCE
    private EditText Weight;
    private EditText Height;
    private EditText Age;
    private TextView bg;
    private Spinner sp;
    double weight;
    double height;
    int age;
    boolean bbgg;
    int state;
    int position=0;

    private TextView text,text2;

    private Button day;
    private Button intro;
    ListView listView;

    String name[];//={"Bob","Mary"};
    String sexual[];//={"M","F"};
    String hei[];//={"170","160"};
    String wei[];//={"70","45"};
    int ag[];//={50,18};
    int sta[];//={0,2};
    private Button load;
    private Button save;
    private Button del;
    private EditText nam;
    boolean cover=false;
    boolean check=false;
    DBHelper DB;

    Bundle bundle;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Finish all the acitivities of the application
        if(getIntent().getBooleanExtra("EXIT", false)){//??????EXIT?????????A
            finish();
            return;
        }

        setContentView(R.layout.question);

        //CREATE THE DATA MODEL FOR STORING THE ITEM TO BE SHIPPED
        num = new ShipItem();
        DB = new DBHelper(this);

        //TASK 3: ESTABLISH THE REFERENCES TO INPUT WEIGHT ELEMENT
        Height = (EditText) findViewById(R.id.editText1);
        Weight = (EditText) findViewById(R.id.editText2);
        Age = (EditText) findViewById(R.id.editText3);
        bg = (TextView) findViewById(R.id.bg);
        text = (TextView) findViewById(R.id.text);
        text2 =(TextView) findViewById(R.id.text2);
        nam = (EditText) findViewById(R.id.editText4);

        String[] set={"????????????","???????????? 1-3 ???","???????????? 3-5 ???","???????????? 6-7 ???","????????????????????????"};
        sp =(Spinner)findViewById(R.id.spinner1);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.set, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,set);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(spnOnItemSelected);

        if(getIntent().getExtras()!=null) {
            bundle = getIntent().getExtras();
            num.set(bundle.getDouble("h"), bundle.getDouble("w"), bundle.getInt("age"), bundle.getBoolean("bg"),bundle.getInt("mode"));
            Weight.setText(num.getWeight().toString());
            Height.setText(num.getHeight().toString());
            Age.setText(num.getAge().toString());
            String nnn = bundle.getString("n");
            nam.setText(nnn);
            sp.setSelection(num.getmode()-1, false);

        }
        if(num.getbg())
            bg.setText("??????");//??????1
        else
            bg.setText("??????");//??????2

        //TASK 4: REGISTER THE LISTENER EVENT FOR WEIGHT INPUT
        //Weight.addTextChangedListener(weightTextWatcher);
        Weight.addTextChangedListener(new DecimalInputTextWatcher(Weight, 5, 1));
        Height.addTextChangedListener(new DecimalInputTextWatcher(Height, 5, 1));
        Weight.addTextChangedListener(weightTextWatcher);
        Height.addTextChangedListener(weightTextWatcher);
        Age.addTextChangedListener(weightTextWatcher);
        bg.addTextChangedListener(weightTextWatcher);

        day = (Button) findViewById(R.id.day);
        day.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent showQuestion = new Intent(QuestionActivity.this, AnswerActivity.class);
                bundle = new Bundle();
                bundle.putString("n",nam.getText().toString());
                bundle.putDouble("w",num.getWeight());
                bundle.putDouble("h",num.getHeight());
                bundle.putInt("age",num.getAge());
                bundle.putInt("mode",num.getmode());
                bundle.putBoolean("bg",num.getbg());
                showQuestion.putExtras(bundle);
                startActivity(showQuestion);
                overridePendingTransition(R.anim.answer_in, R.anim.question_out);
                if(getIntent().getExtras()!=null)
                    finish();
            }


        });

        intro = (Button) findViewById(R.id.intro);
        intro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent showQuestion = new Intent(QuestionActivity.this, IntroActivity.class);
                bundle = new Bundle();
                bundle.putString("n",nam.getText().toString());
                bundle.putDouble("w",num.getWeight());
                bundle.putDouble("h",num.getHeight());
                bundle.putInt("age",num.getAge());
                bundle.putInt("mode",num.getmode());
                bundle.putBoolean("bg",num.getbg());
                showQuestion.putExtras(bundle);
                startActivity(showQuestion);
                overridePendingTransition(R.anim.question_in, R.anim.answer_out);
                if(getIntent().getExtras()!=null)
                    finish();
            }
        });



        listView = findViewById(R.id.listview);

        //????????????Adapter

        Cursor c1 = DB.getdata();
        if(c1.getCount()==0){
            text2.setText("????????????????????????");
            Toast.makeText(QuestionActivity.this, "No Entry Exists", Toast.LENGTH_SHORT).show();

        }
        else
        {
            text2.setText("Select the user data to load");
            name = new String[c1.getCount()];
            sexual = new String[c1.getCount()];
            hei = new String[c1.getCount()];
            wei = new String[c1.getCount()];
            ag = new int[c1.getCount()];
            sta = new int[c1.getCount()];
            c1.moveToFirst();
            for(int i=0;i<c1.getCount();i++){
                name[i] = c1.getString(0);
                sexual[i] = c1.getString(1);
                hei[i] = c1.getString(2);
                wei[i] = c1.getString(3);
                ag[i] = Integer.valueOf(c1.getString(4)).intValue();
                sta[i] = Integer.valueOf(c1.getString(5)).intValue();
                c1.moveToNext();
            }


            MyAdapter myAdapter = new MyAdapter(this,name,sexual,hei,wei,ag,sta);
            listView.setAdapter(myAdapter);
            listView.setOnItemClickListener(spnOnItemClick);
        }


        load = (Button) findViewById(R.id.load);
        load.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Cursor c1 = DB.getdata();
                if(c1.getCount()==0){
                    ;
                }
                else{
                    Cursor c = DB.getdata();
                    String n1TXT=name[position];
                    String n2TXT=sexual[position];
                    String n3TXT=hei[position];
                    String n4TXT=wei[position];
                    int n5TXT=ag[position];
                    int n6TXT=sta[position];

                    Height.setText(n3TXT);
                    Weight.setText(n4TXT);

                    //text.setText("/"+n2TXT+"/");
                    //n2TXT.compareToIgnoreCase()
                    if(n2TXT.compareTo("M")==0){
                        bg.setText("??????");
                        num.setbg(true);}
                    else{
                        bg.setText("??????");
                        num.setbg(false);}
                    nam.setText(n1TXT);

                    Age.setText(String.valueOf(n5TXT));
                    text.setText("");
                    sp.setSelection(n6TXT, false);

                    num.set(Double.parseDouble(Height.getText().toString()),Double.parseDouble(Weight.getText().toString()), Integer.parseInt(Age.getText().toString()),num.getbg(),sp.getSelectedItemPosition()+1);
                }
            }
        });

        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(cover){
                    cover=false;
                    text.setText("");
                    ;//????????????
                }
                else{
                    String name_textip=nam.getText().toString().trim();
                    String name_textinfo =nam.getText().toString();
                    String Height_textip=Height.getText().toString().trim();
                    String Height_textinfo =Height.getText().toString();
                    String Weight_textip=Weight.getText().toString().trim();
                    String Weight_textinfo =Weight.getText().toString();
                    String Age_textip=Age.getText().toString().trim();
                    String Age_textinfo =Age.getText().toString();
                    if(null == name_textinfo || "".equals(name_textinfo)||name_textip.isEmpty()||
                            null == Age_textinfo || "".equals(Age_textinfo)||Age_textip.isEmpty()||
                            null == Weight_textinfo || "".equals(Weight_textinfo)||Weight_textip.isEmpty()||
                            null == Height_textinfo || "".equals(Height_textinfo)||Height_textip.isEmpty())
                    {
                        text.setText("???????????????!");
                    }//??????Info?????????????????????//??????IP?????????????????????
                    else{
                        text.setText("");
                        //if(???????????????){
                        //text.setText(" ????????????????????????????       Save=??????   Del=??????");
                        //cover=true;
                        // }
                        String nameTXT = nam.getText().toString();
                        String sexualTXT;
                        if(num.getbg()==true)
                        {
                            sexualTXT = "M";
                        }
                        else
                        {
                            sexualTXT = "F";
                        }
                        //String sexualTXT = num.getbg();//sexual.getText().toString();
                        String heiTXT = Height.getText().toString();
                        String weiTXT = Weight.getText().toString();
                        String ageTXT = Age.getText().toString();
                        String staTXT = String.valueOf(sp.getSelectedItemPosition());

                        Boolean checkinsertdata = DB.insertuserdata(nameTXT, sexualTXT, heiTXT,weiTXT,ageTXT,staTXT);
                        if(checkinsertdata==true)
                        {
                            Toast.makeText(QuestionActivity.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                            Cursor c = DB.getdata();
                            if(c.getCount()==0){
                                text2.setText("????????????????????????");
                                //Toast.makeText(QuestionActivity.this, "No Entry Exists", Toast.LENGTH_SHORT).show();

                            }
                            else {
                                text2.setText("Select the user data to load");
                            }
                            name = new String[c.getCount()];
                            sexual = new String[c.getCount()];
                            hei = new String[c.getCount()];
                            wei = new String[c.getCount()];
                            ag = new int[c.getCount()];
                            sta = new int[c.getCount()];
                            c.moveToFirst();
                            for(int i=0;i<c.getCount();i++){
                                name[i] = c.getString(0);
                                sexual[i] = c.getString(1);
                                hei[i] = c.getString(2);
                                wei[i] = c.getString(3);
                                ag[i] = Integer.valueOf(c.getString(4)).intValue();
                                sta[i] = Integer.valueOf(c.getString(5)).intValue();
                                c.moveToNext();
                            }
                            MyAdapter myAdapter = new MyAdapter(QuestionActivity.this,name,sexual,hei,wei,ag,sta);
                            listView.setAdapter(myAdapter);
                            listView.setOnItemClickListener(spnOnItemClick);
                        }
                        else
                        {
                            Toast.makeText(QuestionActivity.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
                            getQuestion();
                        }
                    }

                }
            }
        });

        del = (Button) findViewById(R.id.del);
        del.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                getdelQuestion();

            }
        });



    }

    private AdapterView.OnItemClickListener spnOnItemClick
            = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view,
                                int pos, long id) {
            try {
                position=pos;//??????????????????????????????
            }catch (NumberFormatException e){
                ;
            }

        }

        public void onNothingSelected(AdapterView<?> parent) {

        }
    };



    class MyAdapter extends ArrayAdapter<String>{

        Context context;
        String rname[];
        String rsexual[];
        String rhei[];
        String rwei[];
        int rag[];
        int rsta[];

        MyAdapter (Context context,String name[],String sexual[],String hei[],String wei[],int ag[],int sta[]){
            super(context,R.layout.row,R.id.name,name);
            this.context = context;
            this.rname = name;
            this.rsexual = sexual;
            this.rhei = hei;
            this.rwei = wei;
            this.rag = ag;
            this.rsta = sta;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = layoutInflater.inflate(R.layout.row,parent,false);
            TextView name = row.findViewById(R.id.name);
            TextView sexual = row.findViewById(R.id.sexual);
            TextView hei = row.findViewById(R.id.hei);
            TextView wei = row.findViewById(R.id.wei);
            TextView ag = row.findViewById(R.id.ag);
            TextView sta = row.findViewById(R.id.sta);

            name.setText(rname[position]);
            sexual.setText(rsexual[position]);
            hei.setText(rhei[position]);
            wei.setText(rwei[position]);
            ag.setText( String.valueOf(rag[position]));
            sta.setText( String.valueOf(rsta[position]));

            return row;
        }
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //??????????????????????????????????????????????????????????????????????????????

        outState.putDouble("w",num.getWeight());
        outState.putDouble("h",num.getHeight());
        outState.putInt("age",num.getAge());
        outState.putInt("mode",num.getmode());
        outState.putBoolean("bg",num.getbg());
        outState.putString("n",nam.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // ?????????????????????????????????????????????????????????????????????
        if(savedInstanceState != null) {
            int age = savedInstanceState.getInt("age");
            int mode = savedInstanceState.getInt("mode");
            double w = savedInstanceState.getDouble("w");
            double h = savedInstanceState.getDouble("h");
            boolean bbg = savedInstanceState.getBoolean("bg");
            String nnn=savedInstanceState.getString("n");
            num.set(h,w,age,bbg,mode);
            if(num.getbg())
                bg.setText("??????");//??????1
            else
                bg.setText("??????");//??????2
            nam.setText(nnn);
        }
    }

    private AdapterView.OnItemSelectedListener spnOnItemSelected
            = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {
            try {
                //num.mode(sp.getSelectedItemPosition());
                num.set(Double.parseDouble(Height.getText().toString()),Double.parseDouble(Weight.getText().toString()), Integer.parseInt(Age.getText().toString()),num.getbg(),sp.getSelectedItemPosition()+1);
            }catch (NumberFormatException e){
                num.set(0,0, 0,num.getbg(),1);
            }
            //comp();

        }

        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    //private TextWatcher weightTextWatcher = new TextWatcher() {
    public class DecimalInputTextWatcher implements TextWatcher{
        //THE INPUT ELEMENT IS ATTACHED TO AN EDITABLE,
        //THEREFORE THESE METHODS ARE CALLED WHEN THE TEXT IS CHANGED

        private static final int DEFAULT_DECIMAL_DIGITS = 2;//?????? ???????????????  2 ???

        private EditText editText;
        private int decimalDigits;// ???????????????
        private int integerDigits;// ???????????????

        public DecimalInputTextWatcher(EditText editText) {
            this.editText = editText;
            this.decimalDigits = DEFAULT_DECIMAL_DIGITS;
        }

        public DecimalInputTextWatcher(EditText editText, int decimalDigits) {
            this.editText = editText;
            if (decimalDigits <= 0)
                throw new RuntimeException("decimalDigits must > 0");
            this.decimalDigits = decimalDigits;
        }

        public DecimalInputTextWatcher(EditText editText, int integerDigits, int decimalDigits) {
            this.editText = editText;
            if (integerDigits <= 0)
                throw new RuntimeException("integerDigits must > 0");
            if (decimalDigits <= 0)
                throw new RuntimeException("decimalDigits must > 0");
            this.decimalDigits = decimalDigits;
            this.integerDigits = integerDigits;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String s = editable.toString();
            editText.removeTextChangedListener(this);

            if (s.contains(".")) {
                if (integerDigits > 0) {
                    editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(integerDigits + decimalDigits + 1)});
                }
                if (s.length() - 1 - s.indexOf(".") > decimalDigits) {
                    s = s.substring(0,
                            s.indexOf(".") + decimalDigits + 1);
                    editable.replace(0, editable.length(), s.trim());//??????????????????????????????
                }
            } else {
                if (integerDigits > 0) {
                    editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(integerDigits + 1)});
                    if (s.length() > integerDigits) {
                        s = s.substring(0, integerDigits);
                        editable.replace(0, editable.length(), s.trim());
                    }
                }

            }
            if (s.trim().equals(".")) {//?????????????????????????????????0
                s = "0" + s;
                editable.replace(0, editable.length(), s.trim());
            }
            if (s.startsWith("0") && s.trim().length() > 1) {//??????0????????????????????????0
                if (!s.substring(1, 2).equals(".")) {
                    editable.replace(0, editable.length(), "0");
                }
            }
            editText.addTextChangedListener(this);
        }
    };


    private TextWatcher weightTextWatcher = new TextWatcher() {
        //THE INPUT ELEMENT IS ATTACHED TO AN EDITABLE,
        //THEREFORE THESE METHODS ARE CALLED WHEN THE TEXT IS CHANGED

        public void onTextChanged(CharSequence s,
                                  int start, int before, int count){
            //CATCH AN EXCEPTION WHEN THE INPUT IS NOT A NUMBER
            try {
                //num.mode(sp.getSelectedItemPosition());
                num.set(Double.parseDouble(Height.getText().toString()),Double.parseDouble(Weight.getText().toString()), Integer.parseInt(Age.getText().toString()),num.getbg(),sp.getSelectedItemPosition()+1);
            }catch (NumberFormatException e){
                num.set(0,0, 0,num.getbg(),1);
            }
            //comp();
        }
        public void afterTextChanged(Editable s) {}
        public void beforeTextChanged(CharSequence s,
                                      int start, int count, int after){}
    };


    public void reset(View view){
        num.setReset();
        if(num.getbg())
            bg.setText("??????");
        else
            bg.setText("??????");
        nam.setText(null);
        Height.setText(null);
        Weight.setText(null);
        Age.setText(null);
        text.setText("");
        sp.setSelection(0, false);
    }

    public void bg(View view){
        if(num.str())
            bg.setText("??????");//??????
        else
            bg.setText("??????");//??????
    }

    public void getQuestion(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("????????????");
        builder1.setMessage("????????????????????????????????????");

        builder1.setPositiveButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //check =true;
                String nameTXT = nam.getText().toString();
                String sexualTXT;
                if(num.getbg()==true)
                {
                     sexualTXT = "M";
                }
                else
                {
                     sexualTXT = "F";
                }
                //String sexualTXT = num.getbg();//sexual.getText().toString();
                String heiTXT = Height.getText().toString();
                String weiTXT = Weight.getText().toString();
                String ageTXT = Age.getText().toString();
                String staTXT = String.valueOf(sp.getSelectedItemPosition());

                Boolean checkupdatedata = DB.updateuserdata(nameTXT, sexualTXT, heiTXT,weiTXT,ageTXT,staTXT);
                if(checkupdatedata==true)
                {
                    Toast.makeText(QuestionActivity.this, "Entry Updated", Toast.LENGTH_SHORT).show();
                    Cursor c = DB.getdata();
                    if(c.getCount()==0){
                        text2.setText("????????????????????????");
                        //Toast.makeText(QuestionActivity.this, "No Entry Exists", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        text2.setText("Select the user data to load");
                    }
                    name = new String[c.getCount()];
                    sexual = new String[c.getCount()];
                    hei = new String[c.getCount()];
                    wei = new String[c.getCount()];
                    ag = new int[c.getCount()];
                    sta = new int[c.getCount()];
                    c.moveToFirst();
                    for(int i=0;i<c.getCount();i++){
                        name[i] = c.getString(0);
                        sexual[i] = c.getString(1);
                        hei[i] = c.getString(2);
                        wei[i] = c.getString(3);
                        ag[i] = Integer.valueOf(c.getString(4)).intValue();
                        sta[i] = Integer.valueOf(c.getString(5)).intValue();
                        c.moveToNext();
                    }
                    MyAdapter myAdapter = new MyAdapter(QuestionActivity.this,name,sexual,hei,wei,ag,sta);
                    listView.setAdapter(myAdapter);
                    listView.setOnItemClickListener(spnOnItemClick);
                }
                else
                    Toast.makeText(QuestionActivity.this, "New Entry Not Updated", Toast.LENGTH_SHORT).show();

            }
        });
        builder1.setNegativeButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //???????????????????????????
                //check =false;

            }
        });

        builder1.setCancelable(true);
        builder1.show();
    }

    public void getdelQuestion(){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle("????????????");
        builder1.setMessage("??????????????????");

        builder1.setPositiveButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nameTXT = null;//=nam.getText().toString();
                listView.setOnItemClickListener(spnOnItemClick);
                Cursor c = DB.getdata();
                nameTXT=name[position];
                //String nameTXT =nam.getText().toString();
                Boolean checkudeletedata = DB.deletedata(nameTXT);

                if(checkudeletedata==true)
                {
                    Toast.makeText(QuestionActivity.this, "Entry Deleted", Toast.LENGTH_SHORT).show();

                    if(c.getCount()==0){
                        text2.setText("????????????????????????");
                        //Toast.makeText(QuestionActivity.this, "No Entry Exists", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        text2.setText("Select the user data to load");
                    }
                    name = new String[c.getCount()];
                    sexual = new String[c.getCount()];
                    hei = new String[c.getCount()];
                    wei = new String[c.getCount()];
                    ag = new int[c.getCount()];
                    sta = new int[c.getCount()];
                    c.moveToFirst();
                    for(int i=0;i<c.getCount();i++){
                        name[i] = c.getString(0);
                        sexual[i] = c.getString(1);
                        hei[i] = c.getString(2);
                        wei[i] = c.getString(3);
                        ag[i] = Integer.valueOf(c.getString(4)).intValue();
                        sta[i] = Integer.valueOf(c.getString(5)).intValue();
                        c.moveToNext();
                    }
                    MyAdapter myAdapter = new MyAdapter(QuestionActivity.this,name,sexual,hei,wei,ag,sta);
                    listView.setAdapter(myAdapter);
                    listView.setOnItemClickListener(spnOnItemClick);
                }
                else
                    Toast.makeText(QuestionActivity.this, "Entry Not Deleted", Toast.LENGTH_SHORT).show();

            }
        });
        builder1.setNegativeButton("??????", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //???????????????????????????
                //check =false;

            }
        });

        builder1.setCancelable(true);
        builder1.show();
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
