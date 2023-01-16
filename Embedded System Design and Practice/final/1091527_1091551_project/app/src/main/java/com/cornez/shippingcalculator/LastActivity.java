package com.cornez.shippingcalculator;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.OnMapsSdkInitializedCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Arrays;

public class LastActivity extends Activity implements OnMapReadyCallback,OnMapsSdkInitializedCallback {

    //DATA MODEL FOR SHIP ITEM
    static public ShipItem shipItem;
    //VIEW OBJECTS FOR LAYOUT UI REFERENCE
    private MapView mMapView;
    static public Button rretunmain;
    private int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private GoogleMap mMap;
    private final static String TAG = "MapsActivity";
    private FusedLocationProviderClient fusedLocationClient;
    private Location lastLocation;


    static public Spinner sp;

    private AdapterView.OnItemSelectedListener spnOnItemSelected
            = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int pos, long id) {

        }
        public void onNothingSelected(AdapterView<?> parent) {
            //
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapsInitializer.initialize(getApplicationContext(), MapsInitializer.Renderer.LATEST, this);
        setContentView(R.layout.last);
        //CREATE THE DATA MODEL FOR STORING THE ITEM TO BE SHIPPED
        shipItem = new ShipItem();
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(this);
        mMapView.setClickable(false);
        String[] act = {"分店"};



        // add button
        rretunmain = (Button) findViewById((R.id.returnmain));


        rretunmain.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent showQuestion = new Intent(LastActivity.this, MyActivity.class);
                startActivity(showQuestion);
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


    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        //MapsInitializer.initialize(this); // required for the CameraUpdateFactory to be initialized
        //googleMap.clear();
        LatLng sydney,hos;
        mMap = googleMap;
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // Add a marker in Sydney and move the camera
        // LatLng sydney = new LatLng(-34, 151);  原本的座標值是雪梨某處
        // 替換上美麗島站的座標
        mMap.addMarker(new MarkerOptions().position(new LatLng(24.97047776248784, 121.26342073982049)).title("元智大學"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(24.977715803111682, 121.26986999588415)).title("台灣中油 茄苳站"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(24.97647795679287, 121.26628919974783)).title("台塑加盟 西歐中山站"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(24.96284532979065, 121.25421525868617)).title("台灣中油 內壢站"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(24.960696308450835, 121.24626078998183)).title("台灣中油 中壢祥益站(加盟)"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(24.957729049791634, 121.25080348882057)).title("中油龍田站"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(24.957046974073368, 121.24908779830531)).title("台灣中油 中壢中原站(加盟)"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(24.966174201959838, 121.23820783529209)).title("中油直營 中壢工業區站"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(24.957681246317588, 121.25975000161075)).title("統一精工速邁樂中壢三站(自助加油)"));
        mMap.addMarker(new MarkerOptions().position(new LatLng(24.960404907802356, 121.2754083982746)).title("台灣中油 白鷺站"));


        mMap.setMyLocationEnabled(true); // 右上角的定位功能；這行會出現紅色底線，不過仍可正常編譯執行
        mMap.getUiSettings().setZoomControlsEnabled(true);  // 右下角的放大縮小功能
        mMap.getUiSettings().setCompassEnabled(true);       // 左上角的指南針，要兩指旋轉才會出現
        mMap.getUiSettings().setMapToolbarEnabled(true);    // 右下角的導覽及開啟 Google Map功能

        Log.d(TAG, "最高放大層級："+mMap.getMaxZoomLevel());
        Log.d(TAG, "最低放大層級："+mMap.getMinZoomLevel());

        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(24.97047776248784, 121.26342073982049)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(14));     // 放大地圖到 16 倍大
    }

    @Override
    public void onMapsSdkInitialized(@NonNull MapsInitializer.Renderer renderer) {
        switch (renderer) {
            case LATEST:
                Log.e("MapsDemo", "The latest version of the renderer is used.");
                break;
            case LEGACY:
                Log.e("MapsDemo", "The legacy version of the renderer is used.");
                break;
        }
    }
}
