package com.teterin.teterin.ceilinglist.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.teterin.teterin.ceilinglist.logic.Materials;
import com.teterin.teterin.ceilinglist.logic.SuspendCeiling;
import com.teterin.teterin.ceilinglist.logic.details.Cd60;
import com.teterin.teterin.ceilinglist.logic.details.Connector;
import com.teterin.teterin.ceilinglist.logic.details.Lock;
import com.teterin.teterin.ceilinglist.logic.details.Panel;
import com.teterin.teterin.ceilinglist.logic.details.Screw;
import com.teterin.teterin.ceilinglist.logic.details.ScrewCeiling;
import com.teterin.teterin.ceilinglist.logic.details.ScrewPanel;
import com.teterin.teterin.ceilinglist.logic.details.ScrewWall;
import com.teterin.teterin.ceilinglist.logic.details.Suspend;
import com.teterin.teterin.ceilinglist.logic.details.Ud28;

import java.util.Date;
import java.util.UUID;

/**
 * Created by ar4er25 on 3/15/2017.
 */

public class CeilingCursorWrapper extends CursorWrapper {
    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public CeilingCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public SuspendCeiling getCeiling(){
        String uuidString = getString(getColumnIndex(CeilingDataBaseSchema.CeilingTable.Cols.Id));
        SuspendCeiling ceiling = new SuspendCeiling(UUID.fromString(uuidString));
        ceiling.setName(getString(getColumnIndex(CeilingDataBaseSchema.CeilingTable.Cols.Name)));
        ceiling.setDate(new Date(getLong(getColumnIndex(CeilingDataBaseSchema.CeilingTable.Cols.Date))));
        ceiling.setArea(getDouble(getColumnIndex(CeilingDataBaseSchema.CeilingTable.Cols.Area)));
        ceiling.setCd(new Cd60(getInt(getColumnIndex(CeilingDataBaseSchema.CeilingTable.Cols.Cd60)),
                getInt(getColumnIndex(CeilingDataBaseSchema.CeilingTable.Cols.Cd60Size3)),
                getInt(getColumnIndex(CeilingDataBaseSchema.CeilingTable.Cols.Cd60Size4))));
        ceiling.setUd28(new Ud28(getInt(getColumnIndex(CeilingDataBaseSchema.CeilingTable.Cols.Ud28))));
        ceiling.setLock(new Lock(getInt(getColumnIndex(CeilingDataBaseSchema.CeilingTable.Cols.Lock))));
        ceiling.setSuspend(new Suspend(getInt(getColumnIndex(CeilingDataBaseSchema.CeilingTable.Cols.Suspend))));
        ceiling.setPanel(new Panel(getInt(getColumnIndex(CeilingDataBaseSchema.CeilingTable.Cols.Panel))));
        ceiling.setX(getDouble(getColumnIndex(CeilingDataBaseSchema.CeilingTable.Cols.X)));
        ceiling.setY(getDouble(getColumnIndex(CeilingDataBaseSchema.CeilingTable.Cols.Y)));
        ceiling.setScrewCeiling(new ScrewCeiling(getInt(getColumnIndex(CeilingDataBaseSchema.CeilingTable.Cols.screwCeiling))));
        ceiling.setScrewWall(new ScrewWall(getInt(getColumnIndex(CeilingDataBaseSchema.CeilingTable.Cols.screwWall))));
        ceiling.setScrewPanel(new ScrewPanel(getInt(getColumnIndex(CeilingDataBaseSchema.CeilingTable.Cols.screwPanel)),
                getInt(getColumnIndex(CeilingDataBaseSchema.CeilingTable.Cols.DW_SCREWS_25SIZE)),
                getInt(getColumnIndex(CeilingDataBaseSchema.CeilingTable.Cols.DW_SCREWS_40SIZE))));
        ceiling.setConector(new Connector(getInt(getColumnIndex(CeilingDataBaseSchema.CeilingTable.Cols.CONNECTOR))));
        ceiling.setCdStep(getInt(getColumnIndex(CeilingDataBaseSchema.CeilingTable.Cols.CD_STEP)));
        ceiling.setPanelSize(getInt(getColumnIndex(CeilingDataBaseSchema.CeilingTable.Cols.PANEL_SIZE)));
        ceiling.setPanelLayers(getInt(getColumnIndex(CeilingDataBaseSchema.CeilingTable.Cols.PANEL_LAYERS)));
        ceiling.setWallMaterial(Materials.valueOf(getString(getColumnIndex(CeilingDataBaseSchema.CeilingTable.Cols.WALL_MATERIAL))));
        ceiling.setCeilingBaseMaterial(Materials.valueOf(getString(getColumnIndex(CeilingDataBaseSchema.CeilingTable.Cols.CEILING_MATERIAL))));
        ceiling.setScrewConcrete(new Screw(getInt(getColumnIndex(CeilingDataBaseSchema.CeilingTable.Cols.SCREWS_CONCRETE))));
        ceiling.setScrewsWood(new Screw(getInt(getColumnIndex(CeilingDataBaseSchema.CeilingTable.Cols.SCREWS_WOOD))));
        ceiling.setScrewsDryWolls(new Screw(getInt(getColumnIndex(CeilingDataBaseSchema.CeilingTable.Cols.SCREWS_DRYWALL))));


        return ceiling;
    }
}
