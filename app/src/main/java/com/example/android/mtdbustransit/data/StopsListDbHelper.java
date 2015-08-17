/*
* Copyright (C) 2014 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.example.android.mtdbustransit.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.mtdbustransit.data.StopsListContract.StopsListEntry;
import com.example.android.mtdbustransit.data.StopsListContract.nearbyStopsListEntry;

/**
 * Manages a local database for weather data.
 */
public class StopsListDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 6;
    static final String DATABASE_NAME = "StopsList.db";

    final String SQL_CREATE_STOPS_TABLE = "CREATE TABLE " + StopsListEntry.TABLE_NAME + " (" +
            StopsListEntry._ID + " INTEGER PRIMARY KEY," +
            StopsListEntry.COLUMN_STOP_NAME + " TEXT NOT NULL UNIQUE, " +
            StopsListEntry.COLUMN_STOP_ID + " TEXT NOT NULL " +
            ");";

    final String SQL_CREATE_NEARBY_STOPS_TABLE = "CREATE TABLE " + StopsListEntry.TABLE_NAME + " (" +
            nearbyStopsListEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            nearbyStopsListEntry.COLUMN_STOP_NAME + " TEXT NOT NULL, " +
            nearbyStopsListEntry.COLUMN_STOP_ID + " TEXT NOT NULL " +
            nearbyStopsListEntry.COLUMN_STOP_LAT + " REAL NOT NULL" +
            nearbyStopsListEntry.COLUMN_STOP_LONG + " REAL NOT NULL" +
            ");";

    public StopsListDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
// Create a table to hold locations. A location consists of the string supplied in the
// location setting, the city name, and the latitude and longitude


// Why AutoIncrement here, and not above?
// Unique keys will be auto-generated in either case. But for weather
// forecasting, it's reasonable to assume the user will want information
// for a certain date and all dates *following*, so the forecast data
// should be sorted accordingly.

        sqLiteDatabase.execSQL(SQL_CREATE_STOPS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_NEARBY_STOPS_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
// This database is only a cache for online data, so its upgrade policy is
// to simply to discard the data and start over
// Note that this only fires if you change the version number for your database.
// It does NOT depend on the version number for your application.
// If you want to update the schema without wiping data, commenting out the next 2 lines
// should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + StopsListEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + nearbyStopsListEntry.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }


}