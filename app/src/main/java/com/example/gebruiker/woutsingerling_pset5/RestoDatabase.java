package com.example.gebruiker.woutsingerling_pset5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Wout on 27-11-2017.
 */

public class RestoDatabase extends SQLiteOpenHelper {

    SQLiteDatabase db = this.getReadableDatabase();

    // Constuctor
    private RestoDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private static RestoDatabase instance;

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // relevant id, name, price and amount
        sqLiteDatabase.execSQL("create table orders(_id INTEGER PRIMARY KEY, name TEXT, price REAL, amount INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE orders");
    }

    public static RestoDatabase getInstance(Context context) {
        if (instance != null) {
            return instance;
        } else {
            instance = new RestoDatabase(context, "name", null, 1);
            return instance;
        }
    }

    public Cursor selectAll() {
        return getWritableDatabase().rawQuery("SELECT * FROM orders", null);
    }

    public void addItem(String name, double price, long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        // get the amount
         if ( db.rawQuery("SELECT amount FROM orders WHERE _id = "+ id, null).moveToFirst()) {
             db.execSQL("UPDATE orders SET amount = amount + 1 WHERE _id =" + id);
        } else {
             cv.put("name", name);
             cv.put("_id", id);
             cv.put("price", price);
             cv.put("amount", 1);

             db.insert("orders", "null", cv);
         }

    }

    public void clear() {
        db = this.getReadableDatabase();
        db.execSQL("DROP TABLE IF EXISTS" + " orders");
        onCreate(db);
    }

}
