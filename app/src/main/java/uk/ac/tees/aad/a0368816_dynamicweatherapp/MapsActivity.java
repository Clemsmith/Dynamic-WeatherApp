package uk.ac.tees.aad.a0368816_dynamicweatherapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import uk.ac.tees.aad.a0368816_dynamicweatherapp.databinding.ActivityMapsBinding;
import uk.ac.tees.aad.a0368816_dynamicweatherapp.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    double Latitude;
    double Longitude;
    ArrayList<Address> addresses;
    Button detailbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addresses = new ArrayList<>();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MapsActivity.this, CurrentCity.class);
                startActivity(intent);
            }
        });


    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(@NonNull LatLng latLng) {

                Longitude = latLng.longitude;
                Latitude = latLng.latitude;
                MarkerOptions marker = new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude)).title("New Marker");
                mMap.addMarker(marker);
                Toast.makeText(MapsActivity.this, Longitude+" ", Toast.LENGTH_SHORT).show();

                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());


                try {

                    addresses = (ArrayList<Address>) geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(addresses!=null)
                {
                    SharedPreferences sharedPreferences = getSharedPreferences("CurrentLocation",MODE_PRIVATE);
                    SharedPreferences.Editor myEdit = sharedPreferences.edit();
                    String City = addresses.get(0).getAddressLine(0);
                    String sub[] = City.split(",");
                    String subs[] = sub[1].split(" ");
                    Toast.makeText(MapsActivity.this,City,Toast.LENGTH_LONG).show();
                    binding.button.setText(subs[1]);
                    myEdit.putFloat("latitude", (float) latLng.latitude);
                    myEdit.putFloat("longitude", (float) latLng.longitude);
                    myEdit.putString("City",subs[1]);
                    myEdit.commit();
                }

            }
        });
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
       mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        CameraUpdate camPosition = CameraUpdateFactory.newLatLngZoom(sydney,12);
        googleMap.animateCamera(camPosition);

    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {

        MarkerOptions marker = new MarkerOptions().position(new LatLng(latLng.latitude, latLng.longitude)).title("New Marker");
        mMap.addMarker(marker);
       // System.out.println(point.latitude+"---"+ point.longitude);
    }
}