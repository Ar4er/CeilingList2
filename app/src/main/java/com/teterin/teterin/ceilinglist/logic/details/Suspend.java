package com.teterin.teterin.ceilinglist.logic.details;

/**
 * Created by ar4er25 on 2/10/2017.
 */

public class Suspend extends Detail {
    private Cd60 mCd60;

    public Suspend(Cd60 cd60) {
        super(cd60);
        mCd60 = cd60;
        setCount (calculateCount());
    }

    @Override
    public int calculateCount(int x, int y) {
        return 0;
    }

    public Suspend(int anInt) {
        super(anInt);
    }


    public int calculateCount() {
        if (mCd60.countOfUpSIde()==0){
            return 0;
        }
        int countInOneLine = (mCd60.getCeilingLong()-800)/1000+1;
        return countInOneLine*mCd60.calcuateUpLines();
    }
}

