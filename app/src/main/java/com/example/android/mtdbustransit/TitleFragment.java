package com.example.android.mtdbustransit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class TitleFragment extends Fragment {

    public TitleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        TextView favorites = (TextView) rootView.findViewById(R.id.favorites_textview);
        TextView routeplanner = (TextView) rootView.findViewById(R.id.routeplanner_textview);
        TextView searchStops = (TextView) rootView.findViewById(R.id.searchstops_textview);

        favorites.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RoutePlannerActivity.class);
                startActivity(intent);

            }
        });

        routeplanner.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RoutePlannerActivity.class);
                startActivity(intent);
            }
        });

        searchStops.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchStopsActivity.class);
                startActivity(intent);

            }
        });


        return rootView;
    }
}
