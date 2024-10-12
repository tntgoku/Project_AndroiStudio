package com.example.onlineshopp.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class Datahelps1 extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "products1.db";
    public static final int DATABASE_VERSION = 3;
    public static final String TABLE_NAME = "Sach";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_COST = "cost";
    public static final String COLUMN_IMG1 = "img1";
    public Datahelps1(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public Datahelps1(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_COST+" INTEGER,"+
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_IMG1 + " BLOB)";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
        Log.e("TAG", "Table dropped and recreated");
    }
    public void dropTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE da"+TABLE_NAME);
        Log.d("DatabaseHelper", "Table '"+TABLE_NAME+"' has been dropped.");
    }
}
