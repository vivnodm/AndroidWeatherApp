package com.example.weather_api_test;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MycustomAdapter extends RecyclerView.Adapter<MycustomAdapter.MyHolder> {
    private List<WeatherModel> data;
    public MycustomAdapter(List<WeatherModel> infos){
        this.data=infos;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowItem= LayoutInflater.from(parent.getContext()).inflate(R.layout.city_weather_info,parent,false);
        return new MyHolder(rowItem);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.city_name.setText(this.data.get(position).getCity_name());
        holder.temperature.setText(this.data.get(position).getTemperature());
        holder.aqi.setText(this.data.get(position).getAqi());
        holder.wtype.setText(this.data.get(position).getWtype());
        holder.sunrise.setText(this.data.get(position).getSunrise());
        holder.sunset.setText(this.data.get(position).getSunset());
        holder.humidity.setText(this.data.get(position).getHumidity());
        holder.pressure.setText(this.data.get(position).getPressure());
        holder.windspeed.setText(this.data.get(position).getWindspeed());
        holder.realfeel.setText(this.data.get(position).getRealfeel());
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private final TextView city_name;
        private final TextView temperature,aqi,wtype,sunrise,sunset,humidity,pressure,windspeed,realfeel;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            this.city_name=itemView.findViewById(R.id.city_name);
            this.temperature=itemView.findViewById(R.id.temperature);
            this.aqi=itemView.findViewById(R.id.aqi);
            this.wtype=itemView.findViewById(R.id.wtype);
            this.sunrise=itemView.findViewById(R.id.sunrise);
            this.sunset=itemView.findViewById(R.id.sunset);
            this.humidity=itemView.findViewById(R.id.humidity);
            this.pressure=itemView.findViewById(R.id.pressure);
            this.windspeed=itemView.findViewById(R.id.windspeed);
            this.realfeel=itemView.findViewById(R.id.realfeel);

        }
    }
}
