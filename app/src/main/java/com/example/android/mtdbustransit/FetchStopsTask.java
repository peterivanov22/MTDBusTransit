package com.example.android.mtdbustransit;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.mtdbustransit.data.StopsListContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

/**
* Created by peter on 7/29/2015.
*/
public class FetchStopsTask extends AsyncTask<Void, Void, Void> {


    private final String LOG_TAG = FetchStopsTask.class.getSimpleName();
    private final Context mContext;

    public FetchStopsTask(Context context) {
       mContext = context;
    }


    private void getStopsDataFromJson(String stopsJsonStr)
            throws JSONException {

        final String JSON_STOPS = "stops";
        final String JSON_STOP_NAME = "stop_name";
        final String JSON_STOP_LAT = "stop_lat";
        final String JSON_STOP_LONG = "stop_long";
        final String JSON_STOP_ID = "stop_id";

        try {

            JSONObject stopsJson = new JSONObject(stopsJsonStr);
            JSONArray stopsArray = stopsJson.getJSONArray(JSON_STOPS);

            Vector<ContentValues> cVector = new Vector<ContentValues>(stopsArray.length());


            String[] resultStrings = new String[stopsArray.length()];
            for (int i = 0; i < stopsArray.length(); i++) {

                double slat;
                double slong;
                String id;
                String name;

                JSONObject singleStop = stopsArray.getJSONObject(i);


                id = singleStop.getString(JSON_STOP_ID);
                name = singleStop.getString(JSON_STOP_NAME);


                ContentValues StopListValues = new ContentValues();

                StopListValues.put(StopsListContract.StopsListEntry.COLUMN_STOP_NAME, name);
                StopListValues.put(StopsListContract.StopsListEntry.COLUMN_STOP_ID, id);

                cVector.add(StopListValues);
            }

            int inserted = 0;
            if (cVector.size()>0) {
                ContentValues[] cvArray = new ContentValues[cVector.size()];
                cVector.toArray(cvArray);
                inserted = mContext.getContentResolver().bulkInsert(
                        StopsListContract.StopsListEntry.CONTENT_URI, cvArray);
            }

            Log.v(LOG_TAG, "FirstStop entry: " + resultStrings[0]);
        } catch (JSONException e){
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }
        @Override
        protected Void doInBackground (Void...params){

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
                getStopsDataFromJson(stopsJsonStr);
            } catch (IOException e) {
                Log.e("RoutePlannerStops", "Error ", e);
// If the code didn't successfully get the weather data, there's no point in attempting
// to parse it.
            } catch (JSONException e){
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            finally {
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

            return null;
        }

    }
