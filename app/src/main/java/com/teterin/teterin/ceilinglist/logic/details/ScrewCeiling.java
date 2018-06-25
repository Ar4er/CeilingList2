package com.teterin.teterin.ceilinglist.logic.details;

/**
 * Created by ar4er25 on 4/9/2017.
 */

public class ScrewCeiling extends Detail {
    Suspend mSuspend;



    public ScrewCeiling(int c) {
        super(c);
    }


    public ScrewCeiling(Suspend suspend) {
        super(suspend);
        mSuspend = suspend;
        setCount(calculateCount(mSuspend));
    }

    public int calculateCount(Suspend suspend){
       return suspend.getCount()*2;
    }

    @Override
    public int calculateCount(int x, int y) {
        return 0;
    }
}
