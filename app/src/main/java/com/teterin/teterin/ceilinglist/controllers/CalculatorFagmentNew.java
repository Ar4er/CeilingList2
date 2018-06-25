package com.teterin.teterin.ceilinglist.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.teterin.teterin.ceilinglist.R;
import com.teterin.teterin.ceilinglist.dialogs.CalcInfoDialog;
import com.teterin.teterin.ceilinglist.dialogs.OrderExceptionDialog;
import com.teterin.teterin.ceilinglist.logic.CdSteps;
import com.teterin.teterin.ceilinglist.logic.CeilingLab;
import com.teterin.teterin.ceilinglist.logic.Materials;
import com.teterin.teterin.ceilinglist.logic.PanelLongs;
import com.teterin.teterin.ceilinglist.logic.SuspendCeiling;


/**
 * Created by ar4er25 on 3/26/2017.
 */

public class CalculatorFagmentNew extends Fragment {
    private final String TAG = "CalculatorFagmentNew";
    public static final String EXCEPT_DIALOG = "com.bignerdrunch.android.suspendseilingcalculator.EXCEP_DIALOG";
    private String x;
    private  String y;
    private SuspendCeiling mSuspendCeiling;
    private double intY=0.5;
    private double intX=0.5;
    double mCdfirstSize = 3000.0;
    double mCdsecendSize;
    private int mCdStep = 400;
    private int mPanelSize = 2500;
    private int mPanelLayer = 1;
    CheckBox cdSize3CheckBox;
    CheckBox cdSize4CheckBox;
    Materials wallMaterial = Materials.CONCRETE;
    Materials ceilingBaseMaterial = Materials.CONCRETE;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     /*  interstitialAd = Ads.loadInterstitialAd(getActivity());
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                calculateCeiling();
            }
        });*/
    }

    public void calculateCeiling() {
        try {
            intX = Double.parseDouble(x);
            intY = Double.parseDouble(y);
            if (intX < 0.6 || intY < 0.6) {
                throw new NumberFormatException();
            }
            mSuspendCeiling = new SuspendCeiling(intX,
                    intY,
                    mCdfirstSize,
                    mCdsecendSize,
                    mCdStep,
                    mPanelSize,
                    mPanelLayer,
                    getString(R.string.ceiling_default_name),
                    wallMaterial,
                    ceilingBaseMaterial);
            CeilingLab.getCeilingLab(getActivity()).addCeiling(mSuspendCeiling);
            Intent intent = PagerCeilingsActivity.newIntent(getActivity(), mSuspendCeiling.getId());
            startActivity(intent);


        } catch ( Exception e) {
            FragmentManager manager = getActivity().getSupportFragmentManager();
            OrderExceptionDialog dialog = new OrderExceptionDialog();
            dialog.show(manager, EXCEPT_DIALOG);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calculator_new, container, false);
        EditText editX = (EditText) v.findViewById(R.id.edit_X);
        editX.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                x = s.toString();
            }
        });

        EditText editY = (EditText) v.findViewById(R.id.edit_Y);
        editY.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                y = s.toString();
            }
        });

         cdSize3CheckBox = (CheckBox) v.findViewById(R.id.checkbox_cd_size_3);
         cdSize4CheckBox = (CheckBox) v.findViewById(R.id.checkbox_cd_size_4);

       cdSize3CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked)
                    cdSize4CheckBox.setChecked(true);
               mCdfirstSize = isChecked ? 3000.0 : 0.0;
               if (!CalcInfoDialog.ischeckedDontShowAgain() && isChecked && cdSize4CheckBox.isChecked()) {
                   showCalcImageDialog();
               }
           }
       });
        cdSize4CheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked)
                   cdSize3CheckBox.setChecked(true);
                mCdsecendSize = isChecked ? 4000.0 : 0.0;
                if (!CalcInfoDialog.ischeckedDontShowAgain() && isChecked && cdSize3CheckBox.isChecked()) {
                    showCalcImageDialog();
                }
            }
        });

        RadioGroup cdStepRadioGroup = (RadioGroup) v.findViewById(R.id.cd_step_radio_group);
        cdStepRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radiobotton_cd_step_300:
                        mCdStep = CdSteps.SIZE_300;
                        break;
                    case R.id.radiobotton_cd_step_400:
                        mCdStep = CdSteps.SIZE_400;
                        break;
                    case R.id.radiobotton_cd_step_600:
                        mCdStep = CdSteps.SIZE_600;
                        break;
                    default:
                        break;
                }
            }
        });
        RadioGroup panelSizeRadioGroup = (RadioGroup) v.findViewById(R.id.panel_sizes_radio_group);
        panelSizeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radiobotton_Panel_size_2200:
                        mPanelSize = PanelLongs.SIZE_2200;
                        break;
                    case R.id.radiobotton_Panel_size_2500:
                        mPanelSize = PanelLongs.SIZE_2500;
                        break;
                    case R.id.radiobotton_Panel_size_3000:
                        mPanelSize = PanelLongs.SIZE_3000;
                        break;
                    default:
                        break;
                }
            }
        });
        RadioGroup panelLayersRadioGroup = (RadioGroup) v.findViewById(R.id.panel_layers_radio_group);
        panelLayersRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radiobotton_Panel_layer_1:
                        mPanelLayer = 1;
                        break;
                    case R.id.radiobotton_Panel_layer_2:
                        mPanelLayer = 2;
                        break;
                    default:
                        break;
                }
            }
        });
        RadioGroup wallMatRadioGroup = (RadioGroup) v.findViewById(R.id.wall_base_material_radiogroup);
        wallMatRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radiobotton_wall_concrete:
                        wallMaterial = Materials.CONCRETE;
                        break;
                    case R.id.radiobotton_wall_drywall:
                        wallMaterial = Materials.DRYWALL;
                        break;
                    case R.id.radiobotton_wall_wood:
                        wallMaterial = Materials.WOOD;
                        break;
                    default:
                        break;
                }
            }
        });
        final RadioGroup ceilingBaseMatRadioGroup = (RadioGroup) v.findViewById(R.id.ceiling_base_material_radiogroup);
        ceilingBaseMatRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radiobotton_ceiling_concrete:
                        ceilingBaseMaterial = Materials.CONCRETE;
                        break;
                    case R.id.radiobotton_ceiling_wood:
                        ceilingBaseMaterial = Materials.WOOD;
                        break;
                    default:
                        break;
                }
            }
        });

        Button clulateOrderButton = (Button) v.findViewById(R.id.calculate_order_button);
        clulateOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  if (interstitialAd.isLoaded()) {
                    Ads.showInterstitialAd(interstitialAd);
                }else*/ calculateCeiling();
            }
        });

        return v;
    }

    private void showCalcImageDialog(){
        CalcInfoDialog dialog = new CalcInfoDialog();
        FragmentManager fm = getActivity().getSupportFragmentManager();
        dialog.show(fm, TAG);

    }


}
