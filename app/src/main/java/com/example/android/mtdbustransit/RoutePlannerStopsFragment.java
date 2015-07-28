package com.example.android.mtdbustransit;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.example.android.mtdbustransit.data.StopsListContract;

import java.util.Locale;

/**
 * A placeholder fragment containing a simple view.
 */
public class RoutePlannerStopsFragment extends Fragment {

    private StopsListAdapter mStopsListAdapter;

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

        String sortOrder = StopsListContract.StopsListEntry.COLUMN_STOP_NAME + " ASC";

        Uri 

        View rootView = inflater.inflate(R.layout.fragment_route_planner_stops, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_stops_list);
        listView.setAdapter(mStopsListAdapter);

        final EditText editsearch = (EditText) rootView.findViewById(R.id.search);

        editsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                mStopsListAdapter.getFilter().filter(arg0);
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
        return rootView;
    }

    private void updateStopsList() {
        FetchStopsTask stopsTask = new FetchStopsTask(getActivity());
        stopsTask.execute();
    }

}

