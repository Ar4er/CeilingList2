package com.teterin.teterin.ceilinglist.controllers;

import android.support.v4.app.Fragment;

/**
 * Created by ar4er25 on 2/10/2017.
 */

public class CalculatorActivity extends SingleFragmentActivity {



    @Override
    public Fragment createFagment() {
        return new CalculatorFagmentNew();
    }
}
