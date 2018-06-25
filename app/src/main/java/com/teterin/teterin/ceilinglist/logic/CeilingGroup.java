package com.teterin.teterin.ceilinglist.logic;


import com.teterin.teterin.ceilinglist.logic.details.Cd60;
import com.teterin.teterin.ceilinglist.logic.details.Connector;
import com.teterin.teterin.ceilinglist.logic.details.Lock;
import com.teterin.teterin.ceilinglist.logic.details.Panel;
import com.teterin.teterin.ceilinglist.logic.details.Screw;
import com.teterin.teterin.ceilinglist.logic.details.ScrewPanel;
import com.teterin.teterin.ceilinglist.logic.details.Suspend;
import com.teterin.teterin.ceilinglist.logic.details.Ud28;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by ar4er25 on 03-Jun-17.
 */

public class CeilingGroup extends SuspendCeiling {

    private List<SuspendCeiling> mCeilingList;

    public CeilingGroup(SuspendCeiling ceiling) {
        mCeilingList = new ArrayList<>();
        mCeilingList.add(ceiling);
        setId(UUID.randomUUID());
        setCd(ceiling.getCd());
        setUd28(ceiling.getUd28());
        setLock(ceiling.getLock());
        setSuspend(ceiling.getSuspend());
        setPanel(ceiling.getPanel());
        setConector(ceiling.getConector());
        setScrewPanel(ceiling.getScrewPanel());
        setScrewConcrete(ceiling.getScrewConcrete());
        setScrewsWood(ceiling.getScrewsWood());
        setScrewsDryWolls(ceiling.getScrewsDryWolls());
    }

    public List<SuspendCeiling> getCeilingList() {
        return mCeilingList;
    }

    public void addCeiling(SuspendCeiling ceiling){
        mCeilingList.add(ceiling);
        setCd(new Cd60(getCd().getCount()+ceiling.getCd().getCount(), getCd().getCdCountOf3Size().getCount()+ceiling.getCd().getCdCountOf3Size().getCount(), getCd().getCdCountOf4Size().getCount()+ceiling.getCd().getCdCountOf4Size().getCount()));
        setUd28(new Ud28(ceiling.getUd28().getCount()+getUd28().getCount()));
        setLock(new Lock(ceiling.getLock().getCount()+getLock().getCount()));
        setSuspend(new Suspend(ceiling.getSuspend().getCount()+getSuspend().getCount()));
        setPanel(new Panel(ceiling.getPanel().getCount()+getPanel().getCount()));
        setConector(new Connector(ceiling.getConector().getCount()+getConector().getCount()));
        setScrewPanel(new ScrewPanel(ceiling.getScrewPanel().getCount() + getScrewPanel().getCount(),
                ceiling.getScrewPanel().getFirstLayerScrews().getCount() + getScrewPanel().getFirstLayerScrews().getCount(),
                ceiling.getScrewPanel().getSecondLayerScrews().getCount() + getScrewPanel().getSecondLayerScrews().getCount()));
        setScrewConcrete(new Screw(getScrewConcrete().getCount()+ceiling.getScrewConcrete().getCount()));
        setScrewsWood(new Screw(getScrewsWood().getCount()+ceiling.getScrewsWood().getCount()));
        setScrewsDryWolls(new Screw(getScrewsDryWolls().getCount()+ceiling.getScrewsWood().getCount()));

    }
}
