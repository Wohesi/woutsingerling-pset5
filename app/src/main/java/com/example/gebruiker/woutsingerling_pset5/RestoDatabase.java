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
    public RestoDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    private static RestoDatabase instance;

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // relevant id, name, price and amount
        sqLiteDatabase.execSQL("create table orders(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, price INTEGER, amount INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP IF TABLE EXISTS" + "orders");
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

    public void addItem(String name, int price, int amount) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("name", name);
        cv.put("price", price);
        cv.put("amount", amount);

        db.insert("orders", "null", cv);
    }

    public void clear() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP IF TABLE EXISTS" + "orders");
    }
}
