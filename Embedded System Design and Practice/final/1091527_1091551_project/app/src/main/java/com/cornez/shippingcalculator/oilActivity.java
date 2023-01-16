package com.cornez.shippingcalculator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class oilActivity extends Activity {

    //DATA MODEL FOR SHIP ITEM
    static public ShipItem shipItem;
    static public int ex;
    static public double[] TDEE = {1.2,1.375,1.5,1.725,1.9};

    //VIEW OBJECTS FOR LAYOUT UI REFERENCE

    static public TextView eeveryshop;

    static public Button rreturnmain;
    static public Button ccoupon;
    static public ListView stations;
    private MyAdapter ada;

    private String[] name = {"台灣中油 茄苳站","台塑加盟 西歐中山站","台灣中油 內壢站","台灣中油 中壢祥益站(加盟)",
            "中油龍田站","台灣中油 中壢中原站(加盟)","中油直營 中壢工業區站","統一精工速邁樂中壢三站(自助加油)","台灣中油 白鷺站"};
    private String[] add = {"24.977715803111682, 121.26986999588415",
                            "24.97647795679287, 121.26628919974783",
                            "24.96284532979065, 121.25421525868617",
                            "24.960696308450835, 121.24626078998183",
                            "24.957729049791634, 121.25080348882057",
                            "24.957046974073368, 121.24908779830531",
                            "24.966174201959838, 121.23820783529209",
                            "24.957681246317588, 121.25975000161075",
                            "24.960404907802356, 121.2754083982746"
    };

    class MyAdapter extends ArrayAdapter<CharSequence>{

        Context context;
        String Name[];
        String Location[];

        MyAdapter (Context context,String n[],String[] l){
            super(context,R.layout.oilstation,R.id.oils,n);
            this.context = context;
            this.Name = n;
            this.Location = l;
        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View row = layoutInflater.inflate(R.layout.oilstation,parent,false);
            TextView name = row.findViewById(R.id.oils);

            name.setText(Name[position]);

            return row;
        }

        public void goTo(int pos){
            Uri uri = Uri.parse("geo:"+Location[pos]);
            Intent it = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(it);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.oil);
        //CREATE THE DATA MODEL FOR STORING THE ITEM TO BE SHIPPED
        shipItem = new ShipItem();
        //TASK 3: ESTABLISH THE REFERENCES TO OUTPUT ELEMENTS
        //eeveryshop = (TextView) findViewById(R.id.everyshop);

        ada = new MyAdapter(this,name,add);
        stations = (ListView) findViewById(R.id.list);
        // add button
        stations.setAdapter(ada);
        stations.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                ada.goTo(pos);
            }
        });
        rreturnmain = (Button) findViewById((R.id.returnmain));
        ccoupon = (Button) findViewById((R.id.coupon));

        rreturnmain.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent showQuestion = new Intent(oilActivity.this, MyActivity.class);
                startActivity(showQuestion);
            }
        });

        ccoupon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                /*Intent showQuestion = new Intent(oilActivity.this, couponActivity.class);
                startActivity(showQuestion);*/

                Intent it = new Intent(oilActivity.this, couponActivity.class);
                startActivity(it);
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();
        overridePendingTransition(R.anim.cal_out, R.anim.calc_in);
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

    public void compute() {

    }
}
