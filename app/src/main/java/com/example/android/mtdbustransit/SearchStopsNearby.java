package com.example.android.mtdbustransit;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class SearchStopsNearby extends FragmentActivity implements OnMapReadyCallback,
        LocationProviderActivity.LocationCallback {



    private LocationProviderActivity mLocationProvider;
    private MapFragment mapFragment;
    private GoogleMap mGoogleMap;
    private HelperSharedPreferences mAppPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_stops_nearby);

        mAppPrefs = new HelperSharedPreferences(getApplicationContext());

        mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        mLocationProvider = new LocationProviderActivity(this, this);
    }



    @Override
    protected void onResume() {
        super.onResume();
        mLocationProvider.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationProvider.disconnect();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        // Add a marker in Sydney, Australia, and move the camera.
        mGoogleMap = map;
        setUpMap();
    }

    public void setUpMap(){

        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        if (mAppPrefs.getCurrLat()!=0 & mAppPrefs.getCurrLong()!=0) {
            LatLng latLng = new LatLng(mAppPrefs.getCurrLat(), mAppPrefs.getCurrLong());
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title("I am here!");
            mGoogleMap.addMarker(options);
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 25));
        }
    }


    @Override
    public void handleNewLocation(Location location) {
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        if (currentLatitude!=0 & currentLongitude!=0) {
            mAppPrefs.saveCurrLat(currentLatitude);
            mAppPrefs.saveCurrLong(currentLongitude);
            LatLng latLng = new LatLng(mAppPrefs.getCurrLat(), mAppPrefs.getCurrLong());
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title("I am here!");
            mGoogleMap.addMarker(options);
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        }

    }


}
