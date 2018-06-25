package com.teterin.teterin.ceilinglist;

import android.support.v4.app.Fragment;

import com.teterin.teterin.ceilinglist.controllers.CeilingListFragmentRecycler;
import com.teterin.teterin.ceilinglist.controllers.SingleFragmentActivity;
import com.teterin.teterin.ceilinglist.controllers.CeilingListFragmentRecycler;

public class MainActivity extends SingleFragmentActivity {
    @Override
    public Fragment createFagment() {
        return new CeilingListFragmentRecycler();
    }
}
