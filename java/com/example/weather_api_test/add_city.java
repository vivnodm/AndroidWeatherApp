package com.example.weather_api_test;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
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
import java.util.TimeZone;

public class add_city extends AppCompatActivity {
    EditText pincode,city_name;
    LocationManager locationManager;
    String latitude, longitude;
    Boolean gps=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_city);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        pincode=findViewById(R.id.pincode);
        city_name=findViewById(R.id.city_name);
    }
    public void getWeather(View view) throws IOException, JSONException {
        String stg="";
        if (gps){
            stg="https://api.weatherbit.io/v2.0//current?lat="+latitude+"&lon="+longitude+"&key=423ace47dbff48938e2f870897a4dce6";
        }
        else if(!pincode.getText().toString().equals(""))
             stg= "https://api.weatherbit.io/v2.0//current?postal_code={"+pincode.getText()+"}&key=423ace47dbff48938e2f870897a4dce6";
        else if (!city_name.getText().toString().equals("")){
            stg= "https://api.weatherbit.io/v2.0//current?city="+city_name.getText()+"&country=IN&key=423ace47dbff48938e2f870897a4dce6";
        }


        URL url = new URL(stg);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        int responseCode = con.getResponseCode();
        System.out.println("GET Response Code :: " + responseCode);
        if (responseCode == HttpURLConnection.HTTP_OK) { // success
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            JSONObject jobj = (new JSONObject(response.toString())).getJSONArray("data").getJSONObject(0);
            String city_name=jobj.getString("city_name").toString();
            String temperature=jobj.getString("temp")+"° C";
            String aqi=jobj.getString("aqi").toString();
            String wtype=(new JSONObject(jobj.getString("weather"))).getString("description");
            String sunrise=getUTCTime(jobj.getString("sunrise"))+" AM";
            String sunset=getUTCTime(jobj.getString("sunset"))+" PM";
            String humidity=jobj.getString("rh").toString()+" %";
            String pressure=jobj.getString("pres").toString()+"mB";
            String windspeed=jobj.getString("wind_spd").toString()+" km/h";
            String realfeel=jobj.getString("app_temp").toString()+"° C";
            Intent intent=getIntent();
            intent.putExtra("city_name",city_name);
            intent.putExtra("temperature",temperature);
            intent.putExtra("aqi",aqi);
            intent.putExtra("wtype",wtype);
            intent.putExtra("sunrise",sunrise);
            intent.putExtra("sunset",sunset);
            intent.putExtra("humidity",humidity);
            intent.putExtra("pressure",pressure);
            intent.putExtra("windspeed",windspeed);
            intent.putExtra("realfeel",realfeel);

            setResult(RESULT_OK, intent);
            Toast.makeText(this,"City Added",Toast.LENGTH_LONG).show();
            finish();
        }
        else{
            Toast.makeText(this,"Could not find location",Toast.LENGTH_LONG).show();
        }
    }
    private String getUTCTime(String time){
        String hour=time.substring(0,2);
        String minute=time.substring(3);
        Integer hr=Integer.parseInt(hour)+5;
        Integer min=Integer.parseInt(minute)+30;
        if(min>60){
            hr+=1;
            min-=60;
        }
        hr=hr%12;
        String h=hr.toString();
        String m=min.toString();
        if(hr.toString().length()==1){
            h="0"+hr.toString();
        }
        if(min.toString().length()==1){
            m="0"+m.toString();
        }
        return h+":"+m;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void usegps(View view) throws IOException, JSONException {
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS();
        } else {
            getLocation();
        }
    }
    private void OnGPS(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void getLocation() throws IOException, JSONException {
        if ((ActivityCompat.checkSelfPermission(add_city.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) && (ActivityCompat.checkSelfPermission(add_city.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else{

            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(locationGPS != null){
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
                gps=true;
                Toast.makeText(this,"Your Location: Latitude: " + latitude + "Longitude: " + longitude,Toast.LENGTH_LONG).show();
                getWeather(new View(this));
            }
            else{
                Toast.makeText(this, "Unable to find Location", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
