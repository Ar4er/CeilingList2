package com.teterin.teterin.ceilinglist.logic.details;

import android.util.Log;

/**
 * Created by ar4er25 on 2/10/2017.
 */

public class Panel extends Detail {
    private static final String TAG = "Panel";
    private int mPanelLong;

    public Panel(int x, int y) {
        super(x, y);
    }

    public Panel(int anInt){
        super(anInt);
    }
    //public  Panel(int anInt, int panelLong) {
      //  this(anInt);
        //mPanelLong = panelLong;
   // }

    public Panel(int x, int y, int panelLong, int layer, int cdStep) {
        super(x, y, 0.0);
        setLongWidth();
        mPanelLong = panelLong;
        setCount(calculateCountWhithLayers(x, y, layer));
        Log.i(TAG, "Layer = " + layer);
    }

    public int calculateCountWhithLayers(int x, int y, int layer){
        if (layer > 1 && getCeilingLong()%1200 <= 600 && getCeilingLong()%1200 !=0) {
            return calculateCount(x, y) * layer - 1;
        }else
            return calculateCount(x, y) * layer;

    }

    @Override
    public int calculateCount(int x, int y) {
        int count;
        int lines;
        int area = x*y;
        int panelArea = 1200 * mPanelLong;
        if (getCeilingLong()%1200>0) {
             lines = getCeilingLong() / 1200 + 1;
        }else {
           lines = getCeilingLong() / 1200;
        }
        double panelsInLine = getCeilingWidth()*1.0/mPanelLong;
        count = (int) panelsInLine;
        double lastList = mPanelLong*(panelsInLine - (int)panelsInLine);
        int intRest = mPanelLong - (int)lastList;

        for (int i = 0; i < lines ; i++) {
            while (intRest < getCeilingWidth()) {
                intRest = intRest + mPanelLong;
                count++;
            }
            intRest-= getCeilingWidth();
        }
        return count;
    }

    public int getPanelLong() {
        return mPanelLong;
    }
}
