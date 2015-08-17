package com.example.android.mtdbustransit;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

/**
 * Created by peter on 8/15/2015.
 */
public class MinimizeDialogFragment extends DialogFragment
{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final Resources res = getActivity().getResources();
        Bundle bundle = getArguments();

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());

        dialog.setTitle("Please Select");

        dialog.setSingleChoiceItems(R.array.MinimizeOptions, 0,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selected = res.getStringArray(R.array.MinimizeValues)[which];
                        Toast.makeText(getActivity(), selected,
                                Toast.LENGTH_SHORT).show();

                    }




                });

        return dialog.create();
    }



}
