package com.example.android.mtdbustransit;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A placeholder fragment containing a simple view.
 */
public class RoutePlannerStopsFragment extends Fragment {

    public RoutePlannerStopsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.menu_route_planner_stops, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.action_refresh){
            FetchStopsTask stopsTask = new FetchStopsTask();
            stopsTask.execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_route_planner_stops, container, false);



        return rootView;
    }

    public class FetchStopsTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {

        private String[] getStopsDataFromJson(String stopsJsonStr)
            throws JSONException{

                JSONObject stopsList = new JSONObject(stopsJsonStr);
                JSONArray stopsArray
            }




            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            // Will contain the raw JSON response as a string.
            String stopsJsonStr = null;
            try {
// Construct the URL for the OpenWeatherMap query
// Possible parameters are available at OWM's forecast API page, at
// http://openweathermap.org/API#forecast
                URL url = new URL("https://developer.cumtd.com/api/v2.2/json/GetStops?key=0801726d5968485aa41600cc0002f50c");
// Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
// Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
// Nothing to do.
                    stopsJsonStr = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine()) != null) {
// Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
// But it does make debugging a *lot* easier if you print out the completed
// buffer for debugging.
                    buffer.append(line + "\n");
                }
                if (buffer.length() == 0) {
// Stream was empty. No point in parsing.
                    stopsJsonStr = null;
                }
                stopsJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e("RoutePlannerStops", "Error ", e);
// If the code didn't successfully get the weather data, there's no point in attempting
// to parse it.
                stopsJsonStr = null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("RoutePlannerStops", "Error closing stream", e);
                    }
                }
            }

            try {
                return getStopsDataFromJson(stopsJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            return null;
        }
    }



}

