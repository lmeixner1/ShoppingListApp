package edu.fvtc.grocerylist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;


public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String TAG = "DatabaseHelper";
    public static final int DATABASE_VERSION = 1;
    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql;

        sql = "CREATE TABLE IF NOT EXISTS tblGroceryList (Id integer primary key autoincrement, Item text, IsOnShoppingList  integer, IsInCart integer);";
        Log.d(TAG, "onCreate: " + sql);
        db.execSQL(sql);

        //Insert and item
        sql = "INSERT INTO tblItem VALUES (1,'Poptarts', 0, 0);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: ");

    }
}
