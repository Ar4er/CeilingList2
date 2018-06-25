package com.teterin.teterin.ceilinglist.logic.details;

/**
 * Created by ar4er25 on 5/1/2017.
 */
public class Connector extends Detail {

    private Cd60 mCd60;

    public Connector(Cd60 cd60) {
        super(cd60);
        mCd60 = cd60;
        setCount (calculateCount());
    }
    public Connector(int anInt) {
        super(anInt);
    }

    @Override
    public int calculateCount(int x, int y) {
        return 0;
    }

    public int calculateCount(){
        return mCd60.calculateConnectors();
    }
}
