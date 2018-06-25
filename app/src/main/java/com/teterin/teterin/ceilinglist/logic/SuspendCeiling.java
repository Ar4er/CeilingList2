package com.teterin.teterin.ceilinglist.logic;

import com.teterin.teterin.ceilinglist.logic.Materials;
import com.teterin.teterin.ceilinglist.logic.details.Cd60;
import com.teterin.teterin.ceilinglist.logic.details.Connector;
import com.teterin.teterin.ceilinglist.logic.details.Detail;
import com.teterin.teterin.ceilinglist.logic.details.Lock;
import com.teterin.teterin.ceilinglist.logic.details.Panel;
import com.teterin.teterin.ceilinglist.logic.details.Screw;
import com.teterin.teterin.ceilinglist.logic.details.ScrewCeiling;
import com.teterin.teterin.ceilinglist.logic.details.ScrewPanel;
import com.teterin.teterin.ceilinglist.logic.details.ScrewWall;
import com.teterin.teterin.ceilinglist.logic.details.Suspend;
import com.teterin.teterin.ceilinglist.logic.details.Ud28;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * Created by ar4er25 on 2/10/2017.
 */

public class SuspendCeiling implements Serializable{

    private Ud28 mUd28;
    private Cd60 mCd;
    private Lock mLock;
    private Suspend mSuspend;
    private Connector mConector;
    private Panel mPanel;
    private ScrewCeiling mScrewCeiling;
    private ScrewWall mScrewWall;
    private ScrewPanel mScrewPanel;
    private int mCdStep;
    private int mPanelSize;
    private int mPanelLayers;
    private Materials mWallMaterial;
    private Materials mCeilingBaseMaterial;
    private Screw mScrewConcrete;
    private Screw mScrewsWood;
    private Screw mScrewsDryWolls;

    private double mArea;
    private double mX;
    private double mY;
    private Date mDate;
    private UUID mId;
    private String mName;

    public SuspendCeiling() {}

    public SuspendCeiling(double x
            , double y
            , double cdFirstSize
            , double cdSecendSize
            , int cdStep
            , int PanelSize
            , int panelLayers
            , String defaultName
            , Materials wallMat
            , Materials ceilingBaseMat){
        mX = x;
        mY = y;
        mCdStep = cdStep;
        mPanelSize = PanelSize;
        mPanelLayers = panelLayers;
        double xMMd =  x*1000;
        int xMM =(int) xMMd;
        double yMMd = y*1000;
        int yMM =(int) yMMd;
        mUd28 = new Ud28(xMM, yMM);
        mCd = new Cd60(xMM, yMM, cdFirstSize, cdSecendSize, cdStep);
        mLock = new Lock(mCd);
        mSuspend = new Suspend(mCd);
        mConector = new Connector(mCd);
        mPanel = new Panel(xMM, yMM, PanelSize, panelLayers, cdStep);
        mDate = new Date();
        mArea = mX*mY;
        mScrewCeiling = new ScrewCeiling(mSuspend);
        mScrewPanel = new ScrewPanel(mPanel, panelLayers, cdStep);
        mScrewWall = new ScrewWall(xMM, yMM);
        mId = UUID.randomUUID();
        mName = defaultName;
        mWallMaterial = wallMat;
        mCeilingBaseMaterial = ceilingBaseMat;
        screwsInitialization(mScrewCeiling, mCeilingBaseMaterial);
        screwsInitialization(mScrewWall, mWallMaterial);
    }

    public SuspendCeiling(UUID id){
        mId = id;
    }

    public Screw getScrewConcrete() {
        return mScrewConcrete;
    }

    public void setScrewConcrete(Screw screwConcrete) {
        mScrewConcrete = screwConcrete;
    }

    public Screw getScrewsDryWolls() {
        return mScrewsDryWolls;
    }

    public void setScrewsDryWolls(Screw screwsDryWolls) {
        mScrewsDryWolls = screwsDryWolls;
    }

    public Screw getScrewsWood() {
        return mScrewsWood;
    }

    public void setScrewsWood(Screw screwsWood) {
        mScrewsWood = screwsWood;
    }

    public ScrewCeiling getScrewCeiling() {
        return mScrewCeiling;
    }

    public void setScrewCeiling(ScrewCeiling screwCeiling) {
        mScrewCeiling = screwCeiling;
    }

    public ScrewPanel getScrewPanel() {
        return mScrewPanel;
    }

    public void setScrewPanel(ScrewPanel screwPanel) {
        mScrewPanel = screwPanel;
    }

    public ScrewWall getScrewWall() {
        return mScrewWall;
    }

    public void setScrewWall(ScrewWall screwWall) {
        mScrewWall = screwWall;
    }

    public void setArea(double area) {
        mArea = area;
    }

    public void setCd(Cd60 cd) {
        mCd = cd;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public void setId(UUID id) {
        mId = id;
    }

    public void setLock(Lock lock) {
        mLock = lock;
    }

    public void setPanel(Panel panel) {
        mPanel = panel;
    }

    public void setSuspend(Suspend suspend) {
        mSuspend = suspend;
    }

    public void setConector(Connector conector) {
        mConector = conector;
    }

    public void setUd28(Ud28 ud28) {
        mUd28 = ud28;
    }

    public void setX(double x) {
        mX = x;
    }

    public void setY(double y) {
        mY = y;
    }

    public double getX() {
        return mX;
    }

    public double getY() {
        return mY;
    }

    public UUID getId() {
        return mId;
    }

    public Panel getPanel() {
        return mPanel;
    }

    public Suspend getSuspend() {
        return mSuspend;
    }

    public Lock getLock() {
        return mLock;
    }

    public Connector getConnector() {
        return mConector;
    }

    public Ud28 getUd28() {
        return mUd28;
    }

    public double getArea() {
        return mArea;
    }

    public Cd60 getCd() {
        return mCd;
    }

    public Date getDate() {
        return mDate;
    }

    public int getCdStep() {
        return mCdStep;
    }

    public void setCdStep(int cdStep) {
        mCdStep = cdStep;
    }

    public int getPanelLayers() {
        return mPanelLayers;
    }

    public void setPanelLayers(int panelLayers) {
        mPanelLayers = panelLayers;
    }

    public int getPanelSize() {
        return mPanelSize;
    }

    public void setPanelSize(int panelSize) {
        mPanelSize = panelSize;
    }

    @Override
    public String toString() {
        return String.format("%.1f м * %.1f м ",   mX, mY);
    }

     public String areaToString(){
         return String.format("%.1f m2", mArea);
     }


    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Materials getCeilingBaseMaterial() {
        return mCeilingBaseMaterial;
    }

    public void setCeilingBaseMaterial(Materials ceilingBaseMaterial) {
        mCeilingBaseMaterial = ceilingBaseMaterial;
    }

    public Connector getConector() {
        return mConector;
    }

    public Materials getWallMaterial() {
        return mWallMaterial;
    }

    public void setWallMaterial(Materials wallMaterial) {
        mWallMaterial = wallMaterial;
    }

    private void screwsInitialization(Detail screw, Materials material){
        switch (material){
            case CONCRETE:
                if (mScrewConcrete==null) {
                    mScrewConcrete = new Screw(screw);
                }else {
                  mScrewConcrete.addScrews(screw);
                }
                break;
            case WOOD:
                if(mScrewsWood==null){
                    mScrewsWood = new Screw(screw);
                }else {
                 mScrewsWood.addScrews(screw);
                }
                break;
            case DRYWALL:
                if (mScrewsDryWolls==null){
                    mScrewsDryWolls = new Screw(screw);
                }else {
                    mScrewsDryWolls.addScrews(screw);
                }
                break;
        }
        if (mScrewConcrete==null)
           mScrewConcrete = new Screw(0);
        if (mScrewsWood==null)
            mScrewsWood = new Screw(0);
        if (mScrewsDryWolls==null)
            mScrewsDryWolls = new Screw(0);




    }



}



