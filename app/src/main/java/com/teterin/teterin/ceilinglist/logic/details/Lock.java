package com.teterin.teterin.ceilinglist.logic.details;

/**
 * Created by ar4er25 on 2/10/2017.
 */

public class Lock extends Detail {
    private Cd60 mCd60;

    public Lock(Cd60 cd60) {
        super(cd60);
        mCd60 = cd60;
        setCount (calculateCount());
    }

    public Lock(int anInt) {
        super(anInt);
    }

    @Override
    public int calculateCount(int x, int y) {
        return 0;
    }

    public int calculateCount(){
        return mCd60.calcuateUpLines()*mCd60.getDownLines();
    }

}
