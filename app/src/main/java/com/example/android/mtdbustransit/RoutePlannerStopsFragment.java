package com.example.android.mtdbustransit;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;

import com.example.android.mtdbustransit.data.StopsListContract;

import java.util.Locale;

/**
 * A placeholder fragment containing a simple view.
 */
public class RoutePlannerStopsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int STOPSLIST_LOADER = 0;
    private StopsListAdapter mStopsListAdapter;

    private static final String[] STOPSLIST_COLUMNS = {
            StopsListContract.StopsListEntry.TABLE_NAME + "." +
            StopsListContract.StopsListEntry._ID,
            StopsListContract.StopsListEntry.COLUMN_STOP_NAME,
            StopsListContract.StopsListEntry.COLUMN_STOP_ID

    };

    static final int COL_AUTO_STOP_ID = 0;
    static final int COL_STOP_NAME = 1;
    static final int COL_MTD_STOP_ID = 2;

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
            updateStopsList();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        mStopsListAdapter = new StopsListAdapter(getActivity(), null, 0);

        View rootView = inflater.inflate(R.layout.fragment_route_planner_stops, container, false);

        final ListView listView = (ListView) rootView.findViewById(R.id.listview_stops_list);
        listView.setAdapter(mStopsListAdapter);


        final EditText editsearch = (EditText) rootView.findViewById(R.id.search);

        editsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {


                String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                mStopsListAdapter.getFilter().filter(text);
            }



            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {

            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {

            }
        });



        mStopsListAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            @Override
            public Cursor runQuery(CharSequence constraint) {
                return mStopsListAdapter.getListCursor(constraint);
            }
        });

        return rootView;
        }




    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(STOPSLIST_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    private void updateStopsList() {
        FetchStopsTask stopsTask = new FetchStopsTask(getActivity());
        stopsTask.execute();
    }

    @Override
    public void onStart() {
        super.onStart();
        updateStopsList();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String sortOrder = StopsListContract.StopsListEntry.COLUMN_STOP_NAME + " ASC";

        Uri stopsListUri = StopsListContract.StopsListEntry.CONTENT_URI;

        return new CursorLoader(getActivity(), stopsListUri,
                STOPSLIST_COLUMNS, null, null, sortOrder);

    }


    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        mStopsListAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        mStopsListAdapter.swapCursor(null);
    }

}