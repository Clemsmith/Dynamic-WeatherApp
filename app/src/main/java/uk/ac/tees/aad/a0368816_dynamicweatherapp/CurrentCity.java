package uk.ac.tees.aad.a0368816_dynamicweatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import uk.ac.tees.aad.a0368816_dynamicweatherapp.Fragments.Later;
import uk.ac.tees.aad.a0368816_dynamicweatherapp.Fragments.Today;
import uk.ac.tees.aad.a0368816_dynamicweatherapp.Models.TomorrowPojo;
import uk.ac.tees.aad.a0368816_dynamicweatherapp.Models.laterPojo;
import uk.ac.tees.aad.a0368816_dynamicweatherapp.Models.todayPojo;
import uk.ac.tees.aad.a0368816_dynamicweatherapp.Singleton.Singleton;
import uk.ac.tees.aad.a0368816_dynamicweatherapp.adapter.pageAdapter;

public class CurrentCity extends AppCompatActivity {
    TextView cityname;

    TextView feellike,Temp,CityName;

    TabLayout tabLayout;
    TabItem Today,Tomorrow,Later;
    ViewPager viewPager;
    uk.ac.tees.aad.a0368816_dynamicweatherapp.adapter.pageAdapter pageAdapter;
    Button btn;
    Date date;
    Toolbar toolbar;
    public static ArrayList<todayPojo> arrayList;
    public static ArrayList<TomorrowPojo> tomorrowArrayList;
    public static ArrayList<laterPojo> laterArrayList;
    public static ArrayList<laterPojo> getLaterArrayList(){return laterArrayList;}
    public static ArrayList<todayPojo> getArrayList() {


        return arrayList;

    }
    public  static  ArrayList<TomorrowPojo> getTommorrowArrayList()
    {
        return tomorrowArrayList;


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_city);
        arrayList = new ArrayList<>();
        tomorrowArrayList = new ArrayList<>();
        laterArrayList = new ArrayList<>();

        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        Calendar cal = Calendar.getInstance();

        getSupportActionBar().show();
        feellike = findViewById(R.id.feellikee);
        Temp = findViewById(R.id.maintemp);
        CityName = findViewById(R.id.cityName);
        cityname = findViewById(R.id.citName);
        viewPager = findViewById(R.id.viewpager);
        Today = findViewById(R.id.today);
        Tomorrow = findViewById(R.id.tomorrow);
        Later = findViewById(R.id.later);
        tabLayout = findViewById(R.id.tabLayout);
        pageAdapter = new pageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
                //  uk.ac.tees.aad.design.uk.ac.tees.aad.a0368816_dynamicweatherapp.Fragments.Today.uk.ac.tees.aad.a0368816_dynamicweatherapp.adapter.notifyDataSetChanged();


                if (tab.getPosition() == 0 || tab.getPosition() == 1 || tab.getPosition() == 2) {
                    pageAdapter.notifyDataSetChanged();


                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {


            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {


            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        SharedPreferences sh = getSharedPreferences("CurrentLocation", 0);

        float lat = sh.getFloat("latitude", 0);
        float lon = sh.getFloat("longitude", 0);

        String s = sh.getString("City", "");
        Toast.makeText(CurrentCity.this, lat + " and   " + lon, Toast.LENGTH_LONG).show();
        CityName.setText(s);

        String urls = "https://api.openweathermap.org/data/2.5/weather?q="+s+"&appid=e8c47ac2148c920c9b6fa302ceafaad7";

        JsonObjectRequest jsonObjectRequests = new JsonObjectRequest(Request.Method.GET, urls, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                //   Toast.makeText(CurrentCityWeather.this,response.toString(),Toast.LENGTH_LONG).show();
                try {
                    JSONObject object = response.getJSONObject("main");
                    String tempholder = object.getString("temp");
                    String feelholder = object.getString("feels_like");

                    Float temp = Float.parseFloat(tempholder);
                    Float feels = Float.parseFloat(feelholder);
                    feels = feels-273.0f;
                    temp = temp-273.0f;
                    feellike.setText(" Feel like " + Math.round(feels)+" °c");
                    Temp.setText("temp "+ Math.round(temp )+" °c");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        Singleton.getInstance(this).addToRequestQueue(jsonObjectRequests);

        String url = "https://api.openweathermap.org/data/2.5/forecast?lat=" + lat + "&lon=" + lon + "&cnt=20&appid=e8c47ac2148c920c9b6fa302ceafaad7";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //   Toast.makeText(CurrentCityWeather.this,"tRY",Toast.LENGTH_LONG).show();
                try {


                    JSONArray jsonArray = response.getJSONArray("list");


                    cityname.setText(jsonArray.length() + "sdfsdf");
                    //
                    // JSONObject object =jsonArray.getJSONObject(0);
                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject objects = jsonArray.getJSONObject(i);

                        JSONObject obj = objects.getJSONObject("main");
                        JSONArray obj2 = objects.getJSONArray("weather");

                        JSONObject weatherobj = obj2.getJSONObject(0);
                        String desc = weatherobj.getString("description");
                        String icons = weatherobj.getString("icon");

                        String dates = objects.getString("dt_txt");
                        //  String des = Obj2.getString(3);
                        String xx = obj.getString("temp");
                        String hum = obj.getString("humidity");
                        String presure = obj.getString("pressure");
                        String sub = dates.substring(8, 10);

                        float temprature = Float.parseFloat(xx);
                        float finalTemprature = temprature - 273.15f;


                        int dayOfMonth = (cal.get(Calendar.DAY_OF_MONTH));
                        String x = "12";
                        String xxx = "12";
                        int num = Integer.parseInt(sub);
                        if (num == dayOfMonth) {

                            arrayList.add(new todayPojo(dates, desc, xx, hum, presure, sub, icons));
                        }
                        if (num == dayOfMonth+1) {
                            tomorrowArrayList.add(new TomorrowPojo(dates, desc, finalTemprature + "", hum, presure, sub, icons));
                        }
                        if (num >= dayOfMonth+2) {
                            laterArrayList.add(new laterPojo(dates, desc, finalTemprature + "", hum, presure, sub, icons));
                        }
                    }

                    uk.ac.tees.aad.a0368816_dynamicweatherapp.Fragments.Today.adapter.notifyDataSetChanged();
                    uk.ac.tees.aad.a0368816_dynamicweatherapp.Fragments.Tomorrow.adapter.notifyDataSetChanged();
                    //uk.ac.tees.aad.a0368816_dynamicweatherapp.Fragments.Later.adapter.notifyDataSetChanged();




                    String name = sh.getString("City", "");



                    cityname.setText(name);
                    //  CityName.setText(jsonObject.toString());


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(CurrentCity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    //  CityName.setText(e.getMessage());
                    //  lati.setText(e.getMessage().toString());
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        //queue.add(jsonObjectRequest);
        Singleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.abc:
                Intent intent = new Intent(CurrentCity.this, SearchbyCity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}