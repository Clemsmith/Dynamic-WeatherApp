package uk.ac.tees.aad.a0368816_dynamicweatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class splashScreen extends AppCompatActivity {
    FusedLocationProviderClient locationProvider;
    LocationRequest locationRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        int SPLASH_TIME_OUT = 5500;
        locationRequest = new LocationRequest();
        locationRequest.setInterval(30000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        GPSUpdate();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(splashScreen.this, CurrentCity.class));
                finish();



            }
        }, SPLASH_TIME_OUT);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case 99:
                if(grantResults[0]== PackageManager.PERMISSION_GRANTED)
                {
                    GPSUpdate();
                }
                else {
                    Toast.makeText(splashScreen.this, "NO Permission Granted", Toast.LENGTH_LONG).show();
                    finish();
                }
        }
    }
    public void GPSUpdate()
    {
        locationProvider = LocationServices.getFusedLocationProviderClient(splashScreen.this);
        if(ActivityCompat.checkSelfPermission(splashScreen.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {
            locationProvider.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {

                    Toast.makeText(splashScreen.this, location.toString(), Toast.LENGTH_LONG).show();
                    TextView t;
                    SharedPreferences sharedPreferences = getSharedPreferences("CurrentLocation",MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();


                    Toast.makeText(splashScreen.this, "Saved", Toast.LENGTH_SHORT).show();

                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    List<Address> addresses = null;
                    try {
                        addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(addresses.size()>0)
                    {
                        String City = addresses.get(0).getAddressLine(0);

                        String sub[] = City.split(",");
                        String subs[] = sub[1].split(" ");





                        myEdit.putFloat("latitude", (float) location.getLatitude());
                        myEdit.putFloat("longitude", (float) location.getLongitude());
                        myEdit.putString("City",subs[1]);
                        myEdit.commit();

                    }



                }
            });
        }
        else
        {
            if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.M)
            {

                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},99);
            }
        }

    }
}