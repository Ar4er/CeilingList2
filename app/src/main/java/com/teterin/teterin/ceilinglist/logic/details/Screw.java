package com.teterin.teterin.ceilinglist.logic.details;

/**
 * Created by ar4er25 on 03-Jun-17.
 */
public class Screw extends Detail {
    public Screw(Detail screws){
        super(screws);
        setCount(screws.getCount());
    }

    @Override
    public int calculateCount(int x, int y) {
        return 0;
    }

    public Screw(int c) {
        super(c);
    }

    public void addScrews(Detail screw){
        setCount(getCount()+screw.getCount());
    }
}
