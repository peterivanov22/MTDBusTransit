import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.text.format.Time;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.android.mtdbustransit.HelperSharedPreferences;
import com.example.android.mtdbustransit.R;
import com.example.android.mtdbustransit.SearchStopsNearby;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;



public class FetchNearbyStops extends AsyncTask<String, Void, String[]> {


    private final String LOG_TAG = FetchNearbyStops.class.getSimpleName();

    private ArrayAdapter<String> mForecastAdapter;
    private final Context mContext;

    private HelperSharedPreferences mAppPrefs;




    public FetchNearbyStops(Context context, ArrayAdapter<String> forecastAdapter) {
        mContext = context;
        mForecastAdapter = forecastAdapter;
        mAppPrefs = new HelperSharedPreferences(mContext);


    }

    private boolean DEBUG = true;




    @Override
    protected String[] doInBackground(String... params) {


        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String nearbyStopsJsonStr = null;

        String format = "json";
        String units = "metric";
        int numDays = 14;




        try {
            // Construct the URL for the OpenWeatherMap query
            // Possible parameters are avaiable at OWM's forecast API page, at
            // http://openweathermap.org/API#forecast
            final String FORECAST_BASE_URL =
                    "https://developer.cumtd.com/api/v2.2/json/GetStopsByLatLon?";
            final String KEY_PARAM = "key";
            final String key_value= "0801726d5968485aa41600cc0002f50c";
            final String LAT_PARAM = "lat";
            final String LONG_PARAM = "long";

            Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                    .appendQueryParameter(KEY_PARAM, key_value)
                    .appendQueryParameter(LAT_PARAM, Double.toString(mAppPrefs.getCurrLat()))
                    .appendQueryParameter(LAT_PARAM, Double.toString(mAppPrefs.getCurrLong()))
                    .build();

            URL url = new URL(builtUri.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
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
                // Stream was empty.  No point in parsing.
                return null;
            }
            nearbyStopsJsonStr = buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            // If the code didn't successfully get the weather data, there's no point in attemping
            // to parse it.
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        try {
            return getNearbyStopsDataFromJson(nearbyStopsJsonStr);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        // This will only happen if there was an error getting or parsing the forecast.
        return null;
    }

    @Override
    protected void onPostExecute(String[] result) {
        if (result != null && mForecastAdapter != null) {
            mForecastAdapter.clear();
            for(String dayForecastStr : result) {
                mForecastAdapter.add(dayForecastStr);
            }
            // New data is back from the server.  Hooray!
        }
    }

    private String[] getNearbyStopsDataFromJson(String nearbyStopsJsonStr)
            throws JSONException {

        final String JSON_STOPS = "stops";
        final String JSON_STOP_NAME = "stop_name";
        final String JSON_STOP_LAT = "stop_lat";
        final String JSON_STOP_LONG = "stop_long";
        final String JSON_STOP_ID = "stop_id";
        final String JSON_CHANGESET_ID = "changeset_id";

        try {
            JSONArray stopsArray;
            JSONObject stopsJson = new JSONObject(nearbyStopsJsonStr);

            String changeset_id = stopsJson.getString(JSON_CHANGESET_ID);


            stopsArray = stopsJson.getJSONArray(JSON_STOPS);



            for (int i = 0; i < stopsArray.length(); i++) {

                double slat;
                double slong;
                String id;
                String name;

                JSONObject singleStop = stopsArray.getJSONObject(i);


                id = singleStop.getString(JSON_STOP_ID);
                name = singleStop.getString(JSON_STOP_NAME);
                slat = singleStop.getJSONArray("stop_points").get(0).getDouble(JSON_STOP_LAT);


                ContentValues StopListValues = new ContentValues();

                StopListValues.put(StopsListContract.StopsListEntry.COLUMN_STOP_NAME, name);
                StopListValues.put(StopsListContract.StopsListEntry.COLUMN_STOP_ID, id);

                cVector.add(StopListValues);
            }

            int inserted = 0;
            if (cVector.size()>0) {
                ContentValues[] cvArray = new ContentValues[cVector.size()];
                cVector.toArray(cvArray);
                getContext().getContentResolver().bulkInsert(
                        StopsListContract.StopsListEntry.CONTENT_URI, cvArray);
            }


        } catch (JSONException e){
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }



    }
}