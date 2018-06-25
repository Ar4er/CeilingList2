package com.teterin.teterin.ceilinglist.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ar4er25 on 3/15/2017.
 */

public class CeilingBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "ceilingBase.db";
    public CeilingBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + CeilingDataBaseSchema.CeilingTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                CeilingDataBaseSchema.CeilingTable.Cols.Cd60 + ", " +
                CeilingDataBaseSchema.CeilingTable.Cols.Ud28 + ", " +
                CeilingDataBaseSchema.CeilingTable.Cols.Area + ", " +
                CeilingDataBaseSchema.CeilingTable.Cols.Date + ", " +
                CeilingDataBaseSchema.CeilingTable.Cols.Lock + ", " +
                CeilingDataBaseSchema.CeilingTable.Cols.Id + ", "  +
                CeilingDataBaseSchema.CeilingTable.Cols.Name+ ", " +
                CeilingDataBaseSchema.CeilingTable.Cols.Suspend + ", " +
                CeilingDataBaseSchema.CeilingTable.Cols.X + ", " +
                CeilingDataBaseSchema.CeilingTable.Cols.Y + ", " +
                CeilingDataBaseSchema.CeilingTable.Cols.Panel + ", " +
                CeilingDataBaseSchema.CeilingTable.Cols.screwWall + ", " +
                CeilingDataBaseSchema.CeilingTable.Cols.screwCeiling + ", " +
                CeilingDataBaseSchema.CeilingTable.Cols.screwPanel + ", " +
                CeilingDataBaseSchema.CeilingTable.Cols.Cd60Size3 + ", " +
                CeilingDataBaseSchema.CeilingTable.Cols.Cd60Size4 + ", " +
                CeilingDataBaseSchema.CeilingTable.Cols.CONNECTOR + ", " +
                CeilingDataBaseSchema.CeilingTable.Cols.CD_STEP + ", " +
                CeilingDataBaseSchema.CeilingTable.Cols.PANEL_SIZE + ", " +
                CeilingDataBaseSchema.CeilingTable.Cols.PANEL_LAYERS + ", " +
                CeilingDataBaseSchema.CeilingTable.Cols.WALL_MATERIAL + ", " +
                CeilingDataBaseSchema.CeilingTable.Cols.CEILING_MATERIAL + ", " +
                CeilingDataBaseSchema.CeilingTable.Cols.DW_SCREWS_25SIZE + ", " +
                CeilingDataBaseSchema.CeilingTable.Cols.DW_SCREWS_40SIZE + ", " +
                CeilingDataBaseSchema.CeilingTable.Cols.SCREWS_CONCRETE + ", " +
                CeilingDataBaseSchema.CeilingTable.Cols.SCREWS_WOOD + ", " +
                CeilingDataBaseSchema.CeilingTable.Cols.SCREWS_DRYWALL +  ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
