package com.example.android.mtdbustransit;

import android.app.LoaderManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A placeholder fragment containing a simple view.
 */
public class RoutePlannerFragment extends Fragment  {

    public RoutePlannerFragment() {
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_route_planner, container, false);

        TextView origin = (TextView) rootView.findViewById(R.id.origin_textview);
        EditText destination = (EditText) rootView.findViewById(R.id.destination_edittext);


        origin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RoutePlannerStopsActivity.class);
                startActivity(intent);

            }
        });
        return rootView;
    }
}
