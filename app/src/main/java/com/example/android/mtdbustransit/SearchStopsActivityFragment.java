package com.example.android.mtdbustransit;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A placeholder fragment containing a simple view.
 */
public class SearchStopsActivityFragment extends Fragment {

    public SearchStopsActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_search_stops, container, false);

        TextView stopsNearby = (TextView) rootView.findViewById(R.id.stopsNearby_textview);
        TextView stopsLookup = (TextView) rootView.findViewById(R.id.stopsLookup_textview);


        stopsNearby.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchStopsNearby.class);
                startActivity(intent);

            }
        });

        stopsLookup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RoutePlannerActivity.class);
                startActivity(intent);
            }
        });

        return rootView;

    }
}
