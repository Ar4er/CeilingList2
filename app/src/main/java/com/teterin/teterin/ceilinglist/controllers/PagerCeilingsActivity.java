package com.teterin.teterin.ceilinglist.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.teterin.teterin.ceilinglist.R;
import com.teterin.teterin.ceilinglist.logic.CeilingLab;
import com.teterin.teterin.ceilinglist.logic.SuspendCeiling;

import java.util.ArrayList;
import java.util.UUID;


/**
 * Created by ar4er25 on 2/10/2017.
 */

public class PagerCeilingsActivity extends AppCompatActivity {
    private static final String EXTRA_UUID = "com.bignerdrunch.android.suspendseilingcalculator.EXTRA_UUID";

   private ViewPager mViewPager;
   private ArrayList<SuspendCeiling> mCeilings;
    //метод для занесения ид объекта при вызове активности
    public static Intent newIntent(Context packageContext, UUID ceilingId){
        Intent intent = new Intent(packageContext, PagerCeilingsActivity.class);
        intent.putExtra(EXTRA_UUID, ceilingId);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewPager = new ViewPager(this);
        mViewPager.setId(R.id.ceiling_pager);
        setContentView(mViewPager);

        final UUID ceilingId = (UUID) getIntent().getSerializableExtra(EXTRA_UUID);

        mCeilings = CeilingLab.getCeilingLab(this).getList();

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                SuspendCeiling sc = mCeilings.get(position);
                return CardSetFragment.newInstance(sc.getId());
            }

            @Override
            public int getCount() {
                return mCeilings.size();
            }
        });



        for (int i = 0; i <mCeilings.size() ; i++) {
            if (mCeilings.get(i).getId().equals(ceilingId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
