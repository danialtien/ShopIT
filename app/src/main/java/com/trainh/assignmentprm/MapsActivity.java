package com.trainh.assignmentprm;

import android.location.LocationManager;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.trainh.assignmentprm.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    private LocationManager locationManager;

    private final LatLng LC1 = new LatLng(10.790425, 106.688791);

    private Marker marker1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng fpt = new LatLng(10.841318, 106.809880);
        mMap.addMarker(new MarkerOptions().position(fpt).title("FPT UNIVERSITY"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(fpt));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));

        marker1 = mMap.addMarker(new MarkerOptions().position(LC1).title("Gear Nè").snippet("1286 Đ. Nguyễn Duy Trinh, Long Trường, Thành phố Thủ Đức, Thành phố Hồ Chí Minh, Việt Nam"));
        marker1.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.logo));
        marker1.setTag(0);

    }
}