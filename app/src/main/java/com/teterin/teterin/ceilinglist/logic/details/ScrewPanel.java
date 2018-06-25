package com.teterin.teterin.ceilinglist.logic.details;

import android.util.Log;

import com.teterin.teterin.ceilinglist.logic.CdSteps;


/**
 * Created by ar4er25 on 4/9/2017.
 */

public class ScrewPanel extends Detail {
    private static final String TAG = "ScrewPanel";
    private Panel mPanel;
    private int mLayers;
    private int mCdStep;
    private int mCdStepIndex;
    private Detail firstLayerScrews;
    private Detail secondLayerScrews;


    public ScrewPanel(int c) {
        super(c);
    }

    public ScrewPanel(Panel panel, int layers, int cdStep) {
        super(panel);
        mPanel = panel;
        mLayers = layers;
        mCdStep = cdStep;
       mCdStepIndex = getCdStepIndex();
        Log.i(TAG, "Layers in constructor = " + mLayers);
        if (mLayers == 1) {
            setCount(calculateCount());
        } else {
            secondLayerScrews = new Detail(calculaterCountSecendLayer()) {
                @Override
                public int calculateCount(int x, int y) {
                    return calculaterCountSecendLayer();
                }
            };
            firstLayerScrews = new Detail(calculateCountFirstLayer()) {
                @Override
                public int calculateCount(int x, int y) {
                    return calculateCountFirstLayer();
                }
            };
        }
    }

    public ScrewPanel(int count, final int count25mm, final int count40mm) {
        super(count);
        firstLayerScrews = new Detail(count25mm) {
            @Override
            public int calculateCount(int x, int y) {
                return count25mm;
            }
        };
        secondLayerScrews = new Detail(count40mm) {
            @Override
            public int calculateCount(int x, int y) {
                return count40mm;
            }
        };
    }

    public int calculateCount (){
        int longIndex=mPanel.getPanelLong()/200+2;
        int countInOnePanel = mCdStepIndex*longIndex+15;
        return mPanel.getCount()*countInOnePanel;
    }

    public int calculaterCountSecendLayer(){
        return calculateCount()/2;
    }

    public int calculateCountFirstLayer (){
        int longIndex = mPanel.getPanelLong()/500;
        int countInOnePanel = mCdStepIndex * longIndex;
        return (mPanel.getCount()/2) * countInOnePanel;

    }

    private int getCdStepIndex() {
        int cdStepIndex;
        switch (mCdStep){
            case CdSteps.SIZE_300:
                cdStepIndex = 5;
                break;
            case CdSteps.SIZE_400:
                cdStepIndex = 4;
                break;
            case CdSteps.SIZE_600:
                cdStepIndex = 3;
                break;
            default:
                cdStepIndex = 4;
                break;
        }
        return cdStepIndex;
    }


    @Override
    public int calculateCount(int x, int y) {
       return 0;
    }

    public Detail getFirstLayerScrews() {
        return firstLayerScrews;
    }

    public Detail getSecondLayerScrews() {
        return secondLayerScrews;
    }

    public int getLayers() {
        return mLayers;
    }
}
