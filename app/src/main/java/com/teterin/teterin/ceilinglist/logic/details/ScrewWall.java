package com.teterin.teterin.ceilinglist.logic.details;

/**
 * Created by ar4er25 on 4/9/2017.
 */

public class ScrewWall extends Detail {

    public ScrewWall(int x, int y) {
        super(x, y);
        setCount(calculateCount(x, y));
    }
    public ScrewWall(int anInt){
        super(anInt);
    }

    @Override
    public int calculateCount(int x, int y) {
        return (x/500+y/500)*2+4;
    }


}
