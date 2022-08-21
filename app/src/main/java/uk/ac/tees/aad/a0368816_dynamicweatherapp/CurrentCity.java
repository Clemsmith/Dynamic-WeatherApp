package uk.ac.tees.aad.a0368816_dynamicweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class CurrentCity extends AppCompatActivity {
    TextView cityname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_city);
        SharedPreferences sh = getSharedPreferences("CurrentLocation",0);
    cityname = findViewById(R.id.citName);
       // float lat = sh.getFloat("latitude",0);
        //float lon = sh.getFloat("longitude", 0);
        String name = sh.getString("City","");
        Toast.makeText(CurrentCity.this,name,Toast.LENGTH_LONG).show();


        cityname.setText(name);
    }
}