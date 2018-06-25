package com.teterin.teterin.ceilinglist.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.teterin.teterin.ceilinglist.R;


/**
 * Created by ar4er25 on 2/21/2017.
 */

public class RenameDialog extends DialogFragment {

    public static final String EXTRA_NAME ="com.bignerdrunch.android.suspendseilingcalculator.RenameDialog.EXTRA_NAME";

    private String inputName;

    public static RenameDialog newInstance(String name){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_NAME, name);
        RenameDialog rd = new RenameDialog();
        rd.setArguments(args);
        return rd;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_rename, null);
        EditText nameText = (EditText) v.findViewById(R.id.dialog_renae_editText);
        nameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                inputName = s.toString();
            }
        });
        nameText.setText((String)getArguments().getSerializable(EXTRA_NAME));
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.dialog_rename_title)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResult(Activity.RESULT_OK);
                    }
                })
                .create();
    }
    public void sendResult(int resultCode){
        if (getTargetFragment()==null)
            return;
        Intent i = new Intent();
        i.putExtra(EXTRA_NAME, inputName);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);

    }
}
