package com.example.android.mtdbustransit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        final Button minimize = (Button) rootView.findViewById(R.id.minimize_button);
        final Button planTrips = (Button) rootView.findViewById(R.id.PlanTrips_Button)
        Button maxWalk = (Button) rootView.findViewById(R.id.max_walk_button);

        minimize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MinimizeDialogFragment newFragment = new MinimizeDialogFragment();
                newFragment.show(getFragmentManager(), "missiles");
            }
        });

        minimize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });



        origin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RoutePlannerStopsActivity.class);
                startActivityForResult(intent, 0);

            }
        });
        return rootView;
    }

    @Override
    public void onActivityResult (int requestCode, int resultCode,
                                     Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == getActivity().RESULT_OK){
                EditText destination = (EditText) getActivity().findViewById(R.id.destination_edittext);
                destination.setText(data.getStringExtra("STOP_NAME"));
            }

    }

}
