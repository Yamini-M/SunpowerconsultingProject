package com.yaminitask.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by jampalar on 30/06/16.
 */
public class DataBaseHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "allCarsMakesInfo";
    // Contacts table name
    private static final String TABLE_ALL_MAKES = "allmakes";
    // Shops Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_MAKES = "makesrow";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_ALL_CARS_TABLE = "CREATE TABLE " + TABLE_ALL_MAKES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_MAKES + " TEXT"
                + ")";
        sqLiteDatabase.execSQL(CREATE_ALL_CARS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
// Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ALL_MAKES);
// Creating tables again
        onCreate(sqLiteDatabase);
    }

    // Adding new record
    public void addAllCarMakes(String allCarMakes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MAKES, allCarMakes); // Serialized all car makes
// Inserting Row
        db.insert(TABLE_ALL_MAKES, null, values);
        db.close(); // Closing database connection
    }

    public String getAllCarMakes(){
        String selectQuery = "SELECT * FROM " + TABLE_ALL_MAKES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor.getString(1);
    }

    public void deleteRecord(String allCarMakes) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ALL_MAKES, KEY_ID + " = ?",
                new String[] { "0" });
           db.close();
    }

    // Updating a record
    public int updateRecord(String allCarMakes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_MAKES, allCarMakes);
// updating row
        return db.update(TABLE_ALL_MAKES, values, KEY_ID + " = ?",
                new String[]{"0"});
    }
}
