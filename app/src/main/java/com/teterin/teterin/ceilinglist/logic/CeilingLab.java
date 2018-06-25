package com.teterin.teterin.ceilinglist.logic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.teterin.teterin.ceilinglist.database.CeilingBaseHelper;
import com.teterin.teterin.ceilinglist.database.CeilingCursorWrapper;
import com.teterin.teterin.ceilinglist.database.CeilingDataBaseSchema;
import com.teterin.teterin.ceilinglist.database.CeilingDataBaseSchema.CeilingTable;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by ar4er25 on 2/10/2017.
 */

public class CeilingLab {
    private Context mContext;
    private SQLiteDatabase mDatabase;

    private static CeilingLab sCeilingLab;

    private static ArrayList<SuspendCeiling> mList;

    private CeilingLab(Context context){
        mContext = context.getApplicationContext();
        mDatabase = new CeilingBaseHelper(mContext)
                .getWritableDatabase();
        mList = new ArrayList<>();
    }

    private static ContentValues getContentValues(SuspendCeiling ceiling){
        ContentValues values = new ContentValues();
        values.put(CeilingDataBaseSchema.CeilingTable.Cols.Area, ceiling.getArea());
        values.put(CeilingDataBaseSchema.CeilingTable.Cols.Cd60, ceiling.getCd().getCount());
        values.put(CeilingDataBaseSchema.CeilingTable.Cols.Date, ceiling.getDate().getTime());
        values.put(CeilingDataBaseSchema.CeilingTable.Cols.Id, ceiling.getId().toString());
        values.put(CeilingDataBaseSchema.CeilingTable.Cols.Lock, ceiling.getLock().getCount());
        values.put(CeilingDataBaseSchema.CeilingTable.Cols.Name, ceiling.getName());
        values.put(CeilingDataBaseSchema.CeilingTable.Cols.Panel, ceiling.getPanel().getCount());
        values.put(CeilingDataBaseSchema.CeilingTable.Cols.Suspend, ceiling.getSuspend().getCount());
        values.put(CeilingDataBaseSchema.CeilingTable.Cols.Ud28, ceiling.getUd28().getCount());
        values.put(CeilingDataBaseSchema.CeilingTable.Cols.X, ceiling.getX());
        values.put(CeilingDataBaseSchema.CeilingTable.Cols.Y, ceiling.getY());
        values.put(CeilingDataBaseSchema.CeilingTable.Cols.screwCeiling, ceiling.getScrewCeiling().getCount());
        values.put(CeilingDataBaseSchema.CeilingTable.Cols.screwWall, ceiling.getScrewWall().getCount());
        values.put(CeilingDataBaseSchema.CeilingTable.Cols.screwPanel, ceiling.getScrewPanel().getCount());
        values.put(CeilingDataBaseSchema.CeilingTable.Cols.Cd60Size3,  ceiling.getCd().getCdCountOf3Size().getCount());
        values.put(CeilingDataBaseSchema.CeilingTable.Cols.Cd60Size4, isTwoSizes(ceiling) ? ceiling.getCd().getCdCountOf4Size().getCount() : 0);
        values.put(CeilingDataBaseSchema.CeilingTable.Cols.CONNECTOR, ceiling.getConnector().getCount());
        values.put(CeilingDataBaseSchema.CeilingTable.Cols.CD_STEP, ceiling.getCdStep());
        values.put(CeilingDataBaseSchema.CeilingTable.Cols.PANEL_SIZE, ceiling.getPanelSize());
        values.put(CeilingDataBaseSchema.CeilingTable.Cols.PANEL_LAYERS, ceiling.getPanelLayers());
        values.put(CeilingDataBaseSchema.CeilingTable.Cols.WALL_MATERIAL, ceiling.getWallMaterial().toString());
        values.put(CeilingDataBaseSchema.CeilingTable.Cols.CEILING_MATERIAL, ceiling.getCeilingBaseMaterial().toString());
        values.put(CeilingDataBaseSchema.CeilingTable.Cols.DW_SCREWS_25SIZE, isTwoLayers(ceiling) ? ceiling.getScrewPanel().getFirstLayerScrews().getCount() : 0);
        values.put(CeilingDataBaseSchema.CeilingTable.Cols.DW_SCREWS_40SIZE, isTwoLayers(ceiling) ? ceiling.getScrewPanel().getSecondLayerScrews().getCount() : 0);
        values.put(CeilingDataBaseSchema.CeilingTable.Cols.SCREWS_CONCRETE, ceiling.getScrewConcrete().getCount());
        values.put(CeilingDataBaseSchema.CeilingTable.Cols.SCREWS_DRYWALL, ceiling.getScrewsDryWolls().getCount());
        values.put(CeilingDataBaseSchema.CeilingTable.Cols.SCREWS_WOOD, ceiling.getScrewsWood().getCount());
        return values;
    }
    //метод поверки наличия двух размеров каркаса в потолке
    private static boolean isTwoSizes(SuspendCeiling sc){
        if (sc.getCd().getCdCountOf4Size()==null)
            return false;
        else return true;
    }

    private static boolean isTwoLayers(SuspendCeiling sc){
        if (sc.getPanelLayers()!=2)
            return  false;
        else return true;
    }


    public void addCeiling(SuspendCeiling sc){
        ContentValues values = getContentValues(sc);
        mDatabase.insert(CeilingDataBaseSchema.CeilingTable.NAME, null, values);

    }

    public void updateCeiling (SuspendCeiling sc){
        String uuidString = sc.getId().toString();
        ContentValues values = getContentValues(sc);
        mDatabase.update(CeilingDataBaseSchema.CeilingTable.NAME, values,
                CeilingDataBaseSchema.CeilingTable.Cols.Id +" = ?",
                new String[] {uuidString});
    }

    public static CeilingLab getCeilingLab(Context context){
        if (sCeilingLab==null){
            sCeilingLab = new CeilingLab(context);
        }
        return sCeilingLab;
    }



    public ArrayList<SuspendCeiling> getList() {
        ArrayList<SuspendCeiling> ceilings = new ArrayList<>();
        CeilingCursorWrapper cursor = quertyCeilings(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                ceilings.add(cursor.getCeiling());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return ceilings;
    }

    public SuspendCeiling getSuspendCeiling(UUID id){
        CeilingCursorWrapper cursor = quertyCeilings(CeilingDataBaseSchema.CeilingTable.Cols.Id + " = ?", new String[] {id.toString()});
        try {
            if (cursor.getCount()==0){
                return null;
            }
            cursor.moveToFirst();
            return  cursor.getCeiling();
        }finally {
            cursor.close();
        }
    }


    public void deliteCeiling(SuspendCeiling sc){
        mDatabase.delete(
                CeilingDataBaseSchema.CeilingTable.NAME, CeilingDataBaseSchema.CeilingTable.Cols.Id + " = ?",
                new String[]{sc.getId().toString()}
        );
    }

    private CeilingCursorWrapper quertyCeilings(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                CeilingDataBaseSchema.CeilingTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return  new CeilingCursorWrapper(cursor);
    }
}
