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
import android.widget.ArrayAdapter;
import android.widget.ListView;

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

    private ArrayAdapter<String> mStopsListAdapter;

    public RoutePlannerStopsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_route_planner_stops, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            FetchStopsTask stopsTask = new FetchStopsTask();
            stopsTask.execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mStopsListAdapter =
                new ArrayAdapter<String>(
                        getActivity(),
                        R.layout.list_item_stop,
                        R.id.list_item_stop_textview,
                        FAKEDATA);

        View rootView = inflater.inflate(R.layout.fragment_route_planner_stops??, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_stops_list);
        listView.setAdapter(mStopsListAdapter);

        return rootView;
    }
 k 
    public class FetchStopsTask extends AsyncTask<Void, Void, String[]> {

        private final String LOG_TAG = FetchStopsTask.class.getSimpleName();


        private String[] getStopsDataFromJson(String stopsJsonStr)
                throws JSONException {

            final String JSON_STOPS = "stops";
            final String JSON_STOPNAME= "stop_name";

            JSONObject stopsJson = new JSONObject(stopsJsonStr);
            JSONArray stopsArray = stopsJson.getJSONArray(JSON_STOPS);

            String[] resultStrings = new String[stopsArray.length()];
            for (int i=0; i<stopsArray.length(); i++){
                JSONObject singleStop = stopsArray.getJSONObject(i);
                resultStrings[i]=singleStop.getString(JSON_STOPNAME);
            }

            Log.v(LOG_TAG, "FirstStop entry: " + resultStrings[0]);
            return resultStrings;
        }
            @Override
            protected String[] doInBackground (Void...params){

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
                }
                return null;
            }

        @Override
        protected void onPostExecute(String[] result){
            if (result!=null) {
                for(String stopItem:result){
                    mStopsListAdapter.add(stopItem);
                }
            }
        }
        }
    }

