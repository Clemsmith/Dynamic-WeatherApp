package uk.ac.tees.aad.a0368816_dynamicweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import uk.ac.tees.aad.a0368816_dynamicweatherapp.Singleton.Singleton;

public class SearchbyCity extends AppCompatActivity  {

    EditText serachcity;
    Button btn;
    TextView temp;
    Button Go;
    TextView Description, SunRise, Sunset, weather, Feelslike,Min_Temp,Max_Temp,Pressure,Humidity;
    ImageView img;
    SharedPreferences sharedPreferences;
    TextView Cities;
    TextView cit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchby_city);
        serachcity = findViewById(R.id.SearchBycity);



        Go = findViewById(R.id.latgo);
        cit = findViewById(R.id.city_id);



        temp = findViewById(R.id.lattemp);
        Description = findViewById(R.id.latdescriptionId);
        Sunset = findViewById(R.id.latsunsetid);
        SunRise = findViewById(R.id.latsunRiseId);
        weather = findViewById(R.id.latweatherid);
        Feelslike = findViewById(R.id.latfeelslike);
        Min_Temp = findViewById(R.id.latMinTemp);
        Max_Temp = findViewById(R.id.latMaxTemp);
        Pressure = findViewById(R.id.latPressures);
        Humidity = findViewById(R.id.latHumiditys);
        img = findViewById(R.id.latimg);

        String CitySerach = serachcity.getText().toString();




        btn = findViewById(R.id.letdetailInformation);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (Geocoder.isPresent()) {
                    try {
                        String location = CitySerach;
                        Geocoder gc = new Geocoder(SearchbyCity.this);
                        List<Address> addresses = gc.getFromLocationName(location, 5); // get the found Address Objects

                        List<LatLng> ll = new ArrayList<LatLng>(addresses.size()); // A list to save the coordinates if they are available
                        for (Address a : addresses) {
                            if (a.hasLatitude() && a.hasLongitude()) {
                                ll.add(new LatLng(a.getLatitude(), a.getLongitude()));
                                Toast.makeText(SearchbyCity.this, a.getLatitude() + " And " + a.getLongitude(), Toast.LENGTH_LONG).show();


                            }
                        }
                    } catch (IOException e) {
                        // handle the exception
                    } finally {
                        Intent intent = new Intent(SearchbyCity.this, CurrentCity.class);
                        startActivity(intent);

                    }
                }
                //   Intent intent = new Intent(SearchbyCity.this, CurrentCityWeather.class);
                // startActivity(intent);

            }
        });

        Go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String search = serachcity.getText().toString();
                if(!search.isEmpty() && search.length()>4) {

                    String URL = "https://api.openweathermap.org/data/2.5/weather?q="+search+"&appid=e8c47ac2148c920c9b6fa302ceafaad7";
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONArray array = response.getJSONArray("weather");


                                JSONObject OBJ = array.getJSONObject(0);
                                String main = OBJ.getString("main");
                                String Desc = OBJ.getString("description");
                                String icon = OBJ.getString("icon");
                                //  String main = Detail.
                                //  String Desc = Detail.getString(2);
                                // JSONObject jsonObject = array.getJSONObject(0);
                                //  btn.setText(jsonObject.getString("main"));

                                JSONObject jsonObject = response.getJSONObject("main");
                                JSONObject jsonObject1 = response.getJSONObject("sys");

                                //  JSONObject jsonObject1 =
                                String temps = jsonObject.getString("temp");
                                String Feel = jsonObject.getString("feels_like");
                                String MinTemp = jsonObject.getString("temp_min");
                                String MaxTemp = jsonObject.getString("temp_max");
                                String Humid = jsonObject.getString("humidity");
                                String presu = jsonObject.getString("pressure");

                                float tempratureHolder = Float.parseFloat(temps);
                                float temprature = tempratureHolder - 273.0f ;
                                float FeelHolder = Float.parseFloat(Feel);
                                float FeelLike = FeelHolder - 273.0f    ;
                                float MinHolder = Float.parseFloat(MinTemp);
                                Float min = MinHolder - 273.0f;
                                float MaxHolder = Float.parseFloat(MaxTemp);
                                Float max = MaxHolder - 273.0f;

                                String Sun_Rise = jsonObject1.getString("sunrise");
                                String Sun_Set = jsonObject1.getString("sunset");


                                Long TimeStamp = Long.parseLong(Sun_Rise);
                                Long TimeStamp2 = Long.parseLong(Sun_Set);
                                Date timeD = new Date(TimeStamp * 1000);
                                Date timeS = new Date(TimeStamp2 * 1000);
                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                                String SunRiseTime = sdf.format(timeD);
                                String SunSetTime = sdf.format(timeS);

                                temp.setText(Math.round(temprature * 100.0)/100.0 + " °C");
                                Feelslike.setText(Math.round(FeelLike * 100.0)/100.0 + " °C");
                                Min_Temp.setText(Math.round(min * 100.0)/100.0 + " °C");
                                Max_Temp.setText(Math.round(max * 100.0)/100.0 + " °C");

                                SunRise.setText(SunRiseTime);
                                Sunset.setText(SunSetTime);
                                Description.setText(Desc);
                                weather.setText(main);
                                Humidity.setText(Humid + " g.kg-1");
                                Pressure.setText(presu + " PA");


                                String iconUrl = "http://openweathermap.org/img/w/" + icon + ".png";
                                Glide.with(SearchbyCity.this).load(iconUrl).into(img);

                                sharedPreferences = getSharedPreferences("CurrentLocation", MODE_PRIVATE);
                                SharedPreferences.Editor myEdit = sharedPreferences.edit();

                                String cityName = response.getString("name");
                                cit.setText(cityName);
                                JSONObject oks = response.getJSONObject("coord");
                                String lon = oks.getString("lon");
                                String lat = oks.getString("lat");

                                float longi = Float.parseFloat(lon);
                                float lati = Float.parseFloat(lat);

                                myEdit.putFloat("latitude", longi);
                                myEdit.putFloat("longitude", lati);
                                myEdit.putString("City",cityName);
                                myEdit.commit();


                                   Toast.makeText(SearchbyCity.this,oks.getString("lon"),Toast.LENGTH_LONG).show();


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });


                    Singleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
                }
                else
                    {
                        Toast.makeText(SearchbyCity.this,"NO",Toast.LENGTH_LONG).show();
                    }

            }


        });


    }
}