package com.example.weather_api_test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class threeday extends AppCompatActivity {

    TextView cityname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threeday);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        cityname=findViewById(R.id.cityname);
        try {
            Intent inn=getIntent();
            Bundle b=inn.getExtras();
            getWeather(b.get("city_name").toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void getWeather(String city_name) throws IOException, JSONException {
        cityname.setText(city_name);
        try {
            String stg="";
            stg= "https://api.weatherbit.io/v2.0/forecast/hourly?city="+city_name+"&country=IN&key=423ace47dbff48938e2f870897a4dce6";

            URL url = new URL(stg);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // success
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                JSONObject jobj = (new JSONObject(response.toString())).getJSONArray("data").getJSONObject(0);
                String dateTime = jobj.getString("timestamp_local").toString();
                String time=dateTime.substring(11,13);
                int ct=Integer.parseInt(time);
                String textView1="",textView2="",textView3="";
                int j=0;
                for(int i=ct;i<24;i++){
                    JSONObject jobj1 = (new JSONObject(response.toString())).getJSONArray("data").getJSONObject(j);
                    j++;
                    String temp= jobj1.getString("temp").toString();
                    String wtype=(new JSONObject(jobj1.getString("weather"))).getString("description");
                    String localTime= jobj1.getString("timestamp_local").toString();
                    textView1 = textView1 +"   " + getTime(localTime) + "\n" + "   " + temp +"° C\n" + equalSize(wtype) + "\n\n\n";

                }
                TextView tv=findViewById(R.id.tv);
                tv.setText(textView1);
                for(int i=0;i<24;i++){
                    JSONObject jobj1 = (new JSONObject(response.toString())).getJSONArray("data").getJSONObject(j);
                    j++;
                    String temp= jobj1.getString("temp").toString();
                    String wtype=(new JSONObject(jobj1.getString("weather"))).getString("description");
                    String localTime= jobj1.getString("timestamp_local").toString();
                    textView2 = textView2 +"   " + getTime(localTime) + "\n" + "   " + temp +"° C\n" + equalSize(wtype) + "\n\n\n";
                }
                TextView tv2=findViewById(R.id.tv2);
                tv2.setText(textView2);
                for(int i=0;i<=ct;i++){
                    JSONObject jobj1 = (new JSONObject(response.toString())).getJSONArray("data").getJSONObject(j);
                    j++;
                    String temp= jobj1.getString("temp").toString();
                    String wtype=(new JSONObject(jobj1.getString("weather"))).getString("description");
                    String localTime= jobj1.getString("timestamp_local").toString();
                    textView3 = textView3 +"   " + getTime(localTime) + "\n" + "   " + temp +"° C\n" + equalSize(wtype) + "\n\n\n";
                }
                TextView tv3=findViewById(R.id.tv3);
                tv3.setText(textView3);

            }
            else {
                Toast.makeText(this, "Error:"+city_name, Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){
            Toast.makeText(this, "Toast"+e+city_name, Toast.LENGTH_SHORT).show();
        }


    }
    public String getTime(String s){
        String time;
        String ampm = "AM" ;
        s=s.substring(11,13);
        int t=Integer.parseInt(s);
        if(t>12){
            t-=12;
            ampm="PM";
        }
        if(t==12)
            ampm="PM";
        if(t==0)
            t=12;

        time= t + " "+ ampm;
        return time;
    }
    public String equalSize(String s){
        String large= "Thunderstorm with heavy drizzle";
        int length = large.length();
        String padded = String.format("%-30s", s);
        return padded;
    }

}