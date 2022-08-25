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

import uk.ac.tees.aad.a0368816_dynamicweatherapp.Models.laterPojo;
import uk.ac.tees.aad.a0368816_dynamicweatherapp.R;

public class laterAdapter extends RecyclerView.Adapter<laterAdapter.viewholder>{



    public  ArrayList<laterPojo> arrayList;
    Context context;
    public  laterAdapter(ArrayList<laterPojo> arrayList,Context context)
    {
        this.arrayList = arrayList;
        this.context = context;

    }
    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_info_row_later,parent,false);
        return new laterAdapter.viewholder(view);

    }
    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {





        String iconUrl = "http://openweathermap.org/img/w/" + arrayList.get(position).getImageUrl() + ".png";
        Glide.with(context).load(iconUrl).into(holder.weatherimg);
        holder.datetime.setText(arrayList.get(position).getDatetime());
        holder.description.setText(arrayList.get(position).getDescription());
        holder.Temprature.setText(arrayList.get(position).getTemperature());
        holder.humidity.setText(arrayList.get(position).getHumidity());
        holder.wind.setText(arrayList.get(position).getWind());
        holder.pressure.setText(arrayList.get(position).getPressure());



    }
    @Override
    public int getItemCount() {

        return arrayList.size();
    }







    class viewholder extends RecyclerView.ViewHolder
    {
        ImageView weatherimg;
        TextView datetime;
        TextView description;
        TextView Temprature;
        TextView humidity;
        TextView wind;
        TextView pressure;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            weatherimg = itemView.findViewById(R.id.imageViewLater);
            datetime = itemView.findViewById(R.id.dayandtimeLater);
            description= itemView.findViewById(R.id.dayinfoLater);
            Temprature = itemView.findViewById(R.id.tempratureLater);

            humidity = itemView.findViewById(R.id.humadityLater);
            wind = itemView.findViewById(R.id.windLater);
            pressure = itemView.findViewById(R.id.pressureLater);
        }
    }

}