package uk.ac.tees.aad.a0368816_dynamicweatherapp.adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import uk.ac.tees.aad.a0368816_dynamicweatherapp.Models.TomorrowPojo;
import uk.ac.tees.aad.a0368816_dynamicweatherapp.R;


public class TomorrowAdapter extends RecyclerView.Adapter<TomorrowAdapter.viewholder>{
    public ArrayList<TomorrowPojo> arrayList;
    Context context;


    public TomorrowAdapter(ArrayList<TomorrowPojo> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_info_row_tomorrow,parent,false);
        return new TomorrowAdapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        String iconUrl = "http://openweathermap.org/img/w/" + arrayList.get(position).getImageUrl() + ".png";
        Glide.with(context).load(iconUrl).into(holder.weatherimg);
        holder.datetime.setText(arrayList.get(position).getDatetime());
        holder.description.setText(arrayList.get(position).getDescription());
        holder.Temperature.setText(arrayList.get(position).getTemperature());
        holder.humidity.setText(arrayList.get(position).getHumidity());
        holder.wind.setText(arrayList.get(position).getWind());
        holder.pressure.setText(arrayList.get(position).getPressure());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder
    {

        ImageView weatherimg;
        TextView datetime;
        TextView description;
        TextView Temperature;
        TextView humidity;
        TextView wind;
        TextView pressure;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            weatherimg = itemView.findViewById(R.id.weatherImageTommorrow);
            datetime = itemView.findViewById(R.id.datetimetommorrow);
            description= itemView.findViewById(R.id.weatherinfoTommorrow);
            Temperature = itemView.findViewById(R.id.tempraturetommorrow);

            humidity = itemView.findViewById(R.id.humaditytommorrow);
            wind = itemView.findViewById(R.id.Windtommorrow);
            pressure = itemView.findViewById(R.id.pressureTommorrow);
        }
    }

}
