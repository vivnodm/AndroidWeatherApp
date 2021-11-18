package com.example.weather_api_test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView rView;
    MycustomAdapter myAdapter;
    TextView wecometv;
    FloatingActionButton addcity;
    ArrayList<WeatherModel> values = new ArrayList<>();
    private static final int SECOND_ACTIVITY_REQUEST_CODE = 0;
    SnapHelperOnebyOne linearSnap;
    private static int count=0,position=0;
    Button removeCity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rView = findViewById(R.id.recyclev);
        rView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        values = new ArrayList<>();
        //values.add(new WeatherModel("Empty","empty"));
        myAdapter = new MycustomAdapter(values);
        rView.setAdapter(myAdapter);
        rView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        linearSnap = new SnapHelperOnebyOne();
        linearSnap.attachToRecyclerView(rView);
        addcity = findViewById(R.id.addCity);
        removeCity=findViewById(R.id.removeCity);
        removeCity.setOnClickListener(this);
        wecometv=findViewById(R.id.welcometv);
        addcity.setOnClickListener(this);
        rView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE){
                    position = ((LinearLayoutManager)rView.getLayoutManager()).findFirstVisibleItemPosition();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.addCity) {
            Intent intent = new Intent(this, add_city.class);
            startActivityForResult(intent, SECOND_ACTIVITY_REQUEST_CODE);
            wecometv.setVisibility(View.GONE);
        }
        else if(v.getId()==R.id.removeCity){
            if(values.size()>0) {
                values.remove(position);
                myAdapter.notifyItemRemoved(position);
                if(values.size()==0)
                    wecometv.setVisibility(View.VISIBLE);
                if (count > 0)
                    count--;
            }
            else{
                wecometv.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String city_name = data.getStringExtra("city_name");
                String temperature = data.getStringExtra("temperature");
                String aqi=data.getStringExtra("aqi");
                String wtype=data.getStringExtra("wtype");
                String sunrise=data.getStringExtra("sunrise");
                String sunset=data.getStringExtra("sunset");
                String humidity=data.getStringExtra("humidity");
                String pressure=data.getStringExtra("pressure");
                String windspeed=data.getStringExtra("windspeed");
                String realfeel=data.getStringExtra("realfeel");

                values.add(new WeatherModel(city_name,temperature,aqi,wtype,sunrise,sunset,humidity,pressure,windspeed,realfeel));
                myAdapter.notifyItemInserted(count++);
            }
        }
    }


    public void three_day_weather(View view) {
        Intent i = new Intent(this,threeday.class);
        String city_name=values.get(position).getCity_name();
        i.putExtra("city_name",city_name);
        startActivity(i);
    }
}