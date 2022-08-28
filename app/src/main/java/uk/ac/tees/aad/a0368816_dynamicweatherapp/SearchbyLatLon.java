package uk.ac.tees.aad.a0368816_dynamicweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import uk.ac.tees.aad.a0368816_dynamicweatherapp.Singleton.Singleton;

public class SearchbyLatLon extends AppCompatActivity {

    EditText lat,lon;
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
        setContentView(R.layout.activity_searchby_lat_lon);

        lat = findViewById(R.id.searchbyLat);
        lon = findViewById(R.id.SearchByLon);
        temp = findViewById(R.id.lattemp);
        Description = findViewById(R.id.latdescriptionId);
        SunRise = findViewById(R.id.latsunRiseId);
        Sunset = findViewById(R.id.latsunsetid);
        weather= findViewById(R.id.latweatherid);
        Feelslike = findViewById(R.id.latfeelslike);
        Min_Temp = findViewById(R.id.latMinTemp);
        Max_Temp = findViewById(R.id.latMaxTemp);
        Pressure = findViewById(R.id.latPressures);
        Humidity = findViewById(R.id.latHumiditys);
        img = findViewById(R.id.latimg);
        Go = findViewById(R.id.latgo);
        cit = findViewById(R.id.latcitname);
        btn = findViewById(R.id.letdetailInformation);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchbyLatLon.this,CurrentCity.class);
                startActivity(intent);
            }
        });

        Go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String latitude = lat.getText().toString();
                String longitude = lon.getText().toString();


           //     Toast.makeText(SearchbyLatLon.this, latitude.toString() + " " + longitude.toString(), Toast.LENGTH_SHORT).show();


               if(!latitude.isEmpty() && !longitude.isEmpty())
                {
                    String url = "https://api.openweathermap.org/data/2.5/forecast?lat="+latitude+"&lon="+longitude+"&cnt=1&appid=e8c47ac2148c920c9b6fa302ceafaad7";
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                          //  Toast.makeText(SearchbyLatLon.this,response.toString(),Toast.LENGTH_LONG).show();
                            try {

                               // Toast.makeText(SearchbyLatLon.this, response.getString("cod"), Toast.LENGTH_SHORT).show();

                                JSONArray jsonArray = response.getJSONArray("list");
                                JSONObject objects = jsonArray.getJSONObject(0);
                                JSONObject obj = objects.getJSONObject("main");
                                JSONArray obj2 = objects.getJSONArray("weather");

                                String temps = obj.getString("temp");
                                float tempholder = Float.parseFloat(temps);
                                tempholder = tempholder-273.0f;
                                String feel = obj.getString("feels_like");
                                float tempfeel = Float.parseFloat(feel);
                                tempfeel = tempfeel-273.0f;
                                String min = obj.getString("temp_min");
                                float minholder = Float.parseFloat(min);
                                minholder = minholder-273.0f;
                                String max = obj.getString("temp_max");
                                float maxholder = Float.parseFloat(max);
                                String presure = obj.getString("pressure");
                                String humidity = obj.getString("humidity");


                                JSONObject weatherobj = obj2.getJSONObject(0);
                                String main = weatherobj.getString("main");
                                String description = weatherobj.getString("description");
                                String imgsrc = weatherobj.getString("icon");


                                JSONObject Cityobj = response.getJSONObject("city");


                                String sunrise = Cityobj.getString("sunrise");
                                String sunset = Cityobj.getString("sunset");
                                String cityname = Cityobj.getString("name");

                                Long TimeStamp = Long.parseLong(sunrise);
                                Long TimeStamp2 = Long.parseLong(sunset);
                                Date timeD = new Date(TimeStamp * 1000);
                                Date timeS = new Date(TimeStamp2 * 1000);
                                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                                String SunRiseTime = sdf.format(timeD);
                                String SunSetTime = sdf.format(timeS);



                                Description.setText(description);
                                SunRise.setText(SunRiseTime);
                                Sunset.setText(SunSetTime);
                                weather.setText(main);
                                temp.setText(tempholder +" °C");
                                Feelslike.setText(tempfeel + " °C");
                                Min_Temp.setText(minholder + " °C");
                                Max_Temp.setText(maxholder + "°C");
                                Pressure.setText(presure + "PA");
                                Humidity.setText(humidity + "g.kg-1");
                                String iconUrl = "http://openweathermap.org/img/w/" + imgsrc + ".png";
                                Glide.with(SearchbyLatLon.this).load(iconUrl).into(img);


                                sharedPreferences = getSharedPreferences("CurrentLocation", MODE_PRIVATE);
                                SharedPreferences.Editor myEdit = sharedPreferences.edit();

                                JSONObject CORD = Cityobj.getJSONObject("coord");
                                String lat_final = CORD.getString("lat");
                                Float lat_finals = Float.parseFloat(lat_final);
                                String lon_final = CORD.getString("lon");
                                Float lon_finals = Float.parseFloat(lon_final);


                                Toast.makeText(SearchbyLatLon.this, lat_final+ " AND "+ lon_final, Toast.LENGTH_SHORT).show();
                                if(!cityname.isEmpty()) {
                                    Toast.makeText(SearchbyLatLon.this, sunrise + "  " + cityname, Toast.LENGTH_SHORT).show();
                                    cit.setText(cityname);
                                    myEdit.putFloat("latitude", lat_finals);
                                    myEdit.putFloat("longitude", lon_finals);
                                    myEdit.putString("City",cityname);
                                    myEdit.commit();

                                }
                                else if(cityname.isEmpty())
                                    {
                                       // Toast.makeText(SearchbyLatLon.this, sunrise + "  " + sunset, Toast.LENGTH_SHORT).show();
                                        cit.setText("No CityFound");
                                        myEdit.putFloat("latitude", lat_finals);
                                        myEdit.putFloat("longitude", lon_finals);
                                        myEdit.putString("City","London");
                                        myEdit.commit();
                                    }

                             //   JSONArray array = response.getJSONArray("weather");
                             //   JSONObject OBJ = array.getJSONObject(0);
                              //  String main = OBJ.getString("main");
                               // String Desc = OBJ.getString("description");
                              //  String icon = OBJ.getString("icon");

                              //  String SunRise = response.getString("sunrise");
                             //   String Sunset = response.getString("sunset");





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
            }
        });


    }
}