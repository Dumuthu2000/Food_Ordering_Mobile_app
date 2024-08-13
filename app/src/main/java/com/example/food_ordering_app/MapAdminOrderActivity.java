package com.example.food_ordering_app;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapAdminOrderActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_admin_order);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Real delivery locations in Colombo
        LatLng[] deliveryLocations = {
                new LatLng(6.9366, 79.8426), // Colombo Fort
                new LatLng(6.9366, 79.8526),
                new LatLng(6.9366, 79.8726)
        };

        // Add markers for each location with custom colors
        for (LatLng location : deliveryLocations) {
            mMap.addMarker(new MarkerOptions()
                    .position(location)
                    .title("Delivery Location")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))); // Custom color
        }

        // Move camera to the first location and zoom out
        if (deliveryLocations.length > 0) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(deliveryLocations[0], 12)); // Adjust zoom level if needed
        }
    }
}