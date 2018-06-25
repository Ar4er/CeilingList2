package com.teterin.teterin.ceilinglist.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.teterin.teterin.ceilinglist.R;


/**
 * Created by ar4er25 on 05-May-17.
 */

public class CalcInfoDialog extends DialogFragment {

    private static boolean ischeckedDontShowAgain;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v =  getActivity().getLayoutInflater().inflate(R.layout.dialog_calculator_info, null);

        CheckBox dontShowAgainCheckBox = (CheckBox) v.findViewById(R.id.checkbox_dont_view_again);
        dontShowAgainCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ischeckedDontShowAgain = isChecked;
            }
        });
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton(android.R.string.ok, null).create();
    }

    public static boolean ischeckedDontShowAgain() {
        return ischeckedDontShowAgain;
    }
}
