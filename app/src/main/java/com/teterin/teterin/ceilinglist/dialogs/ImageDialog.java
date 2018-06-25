package com.teterin.teterin.ceilinglist.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.teterin.teterin.ceilinglist.R;


/**
 * Created by ar4er25 on 4/22/2017.
 */

public class ImageDialog extends DialogFragment {
    public static final String EXTRA_DESCRIPTION = "com.bignerdrunch.android.suspendseilingcalculator.imageDialogs.EXTRA_DESCRIPTION";
    public static final String EXTRA_lABEL = "com.bignerdrunch.android.suspendseilingcalculator.imageDialogs.EXTRA_label";
    public static final String EXTRA_IMAGE_RES = "com.bignerdrunch.android.suspendseilingcalculator.imageDialogs.EXTRA_IMAGE";


    public static ImageDialog getNewInstance(String description, int title, int imageRes){
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DESCRIPTION, description);
        args.putSerializable(EXTRA_lABEL, title);
        args.putSerializable(EXTRA_IMAGE_RES, imageRes);
        ImageDialog dialog = new ImageDialog();
        dialog.setArguments(args);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.image_dialog, null);
        ImageView imageView = (ImageView) v.findViewById(R.id.image_dialog_drawable);
        imageView.setImageResource((Integer) getArguments().getSerializable(EXTRA_IMAGE_RES));
        TextView textView = (TextView) v.findViewById(R.id.image_dialog_string);
        textView.setText((String) getArguments().getSerializable(EXTRA_DESCRIPTION));

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle((Integer) getArguments().getSerializable(EXTRA_lABEL))
                .setPositiveButton(android.R.string.ok, null).create();
    }


}
