package com.example.android.mtdbustransit;

import android.content.res.Resources;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;


public class SearchStopsNearby extends FragmentActivity implements OnMapReadyCallback,
        LocationProviderActivity.LocationCallback {

    private static final String TAG = SearchStopsNearby.class.getSimpleName();


    protected GoogleApiClient mGoogleApiClient;
    private PlaceAutocompleteAdapter mAdapter;
    private AutoCompleteTextView mAutocompleteView;
    private TextView mPlaceDetailsText;
    private TextView mPlaceDetailsAttribution;
    private static final LatLngBounds BOUNDS_CAMPUS = new LatLngBounds(
            new LatLng(40.098362, -88.23854), new LatLng(40.116251, -88.219565) );

    private LatLng mPickedLatLng;


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

        // Retrieve the AutoCompleteTextView that will display Place suggestions.
        mAutocompleteView = (AutoCompleteTextView)
                findViewById(R.id.autocomplete_places2);
// Register a listener that receives callbacks when a suggestion has been selected
        mAutocompleteView.setOnItemClickListener(mAutocompleteClickListener);
// Retrieve the TextViews that will display details and attributions of the selected place.


        mGoogleApiClient = mLocationProvider.mGoogleApiClient;

// Set up the adapter that will retrieve suggestions from the Places Geo Data API that cover
// the entire world.
        mAdapter = new PlaceAutocompleteAdapter(this, android.R.layout.simple_list_item_1,
                mGoogleApiClient, BOUNDS_CAMPUS, null);
        mAutocompleteView.setAdapter(mAdapter);
    }

    private AdapterView.OnItemClickListener mAutocompleteClickListener
            = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
/*
Retrieve the place ID of the selected item from the Adapter.
The adapter stores each Place suggestion in a PlaceAutocomplete object from which we
read the place ID.
*/
            final PlaceAutocompleteAdapter.PlaceAutocomplete item = mAdapter.getItem(position);
            final String placeId = String.valueOf(item.placeId);
            Log.i(TAG, "Autocomplete item selected: " + item.description);

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);

            placeResult.setResultCallback(mPlacePickedChangeMap);

        }
    };

    /**
     * Callback for results from a Places Geo Data API query that shows the first place result in
     * the details view on screen.
     */
    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
// Request did not complete successfully
                Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                places.release();
                return;
            }
// Get the Place object from the buffer.
            final Place place = places.get(0);
// Format details of the place for display and show it in a TextView.
            mPlaceDetailsText.setText(formatPlaceDetails(getResources(), place.getName(),
                    place.getId(), place.getAddress(), place.getPhoneNumber(),
                    place.getWebsiteUri()));
// Display the third party attributions if set.
            final CharSequence thirdPartyAttribution = places.getAttributions();
            if (thirdPartyAttribution == null) {
                mPlaceDetailsAttribution.setVisibility(View.GONE);
            } else {
                mPlaceDetailsAttribution.setVisibility(View.VISIBLE);
                mPlaceDetailsAttribution.setText(Html.fromHtml(thirdPartyAttribution.toString()));
            }
            Log.i(TAG, "Place details received: " + place.getName());
            places.release();
        }
    };

    private static Spanned formatPlaceDetails(Resources res, CharSequence name, String id,
                                              CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
        Log.e(TAG, res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));
        return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber,
                websiteUri));
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

    private ResultCallback<PlaceBuffer> mPlacePickedChangeMap
            = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(PlaceBuffer places) {
            if (!places.getStatus().isSuccess()) {
// Request did not complete successfully
                Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                places.release();
                return;
            }
// Get the Place object from the buffer.
            final Place place = places.get(0);
            mPickedLatLng = place.getLatLng();
// Format details of the place for display and show it in a TextView.

            MarkerOptions options = new MarkerOptions()
                    .position(mPickedLatLng)
                    .title("I am here!");
            mGoogleMap.addMarker(options);
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mPickedLatLng, 15));


            Log.i(TAG, "Place details received: " + place.getName());
            places.release();
        }
    };


}
