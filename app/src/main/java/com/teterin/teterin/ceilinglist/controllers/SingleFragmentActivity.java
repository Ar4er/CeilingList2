package com.teterin.teterin.ceilinglist.controllers;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.teterin.teterin.ceilinglist.R;

/**
 * Created by ar4er25 on 2/10/2017.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {

    public abstract Fragment createFagment();

    @Nullable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_container);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_c);

        if (fragment == null) {
            fragment = createFagment();
            fm.beginTransaction()
                    .add(R.id.fragment_c, fragment)
                    .commit();
        }
    }

}


