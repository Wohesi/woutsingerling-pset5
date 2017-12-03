package com.example.gebruiker.woutsingerling_pset5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Wout on 27-11-2017.
 */

public class RestoDatabase extends SQLiteOpenHelper {

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

    public void addItem(String name, double price, int amount, long id) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        // get the amount
        Cursor cursor = db.rawQuery("SELECT amount FROM orders WHERE _id = "+ id, null);
        amount = cursor.getInt(cursor.getColumnIndex("amount"));

        if (amount == 0 ) {
            amount += 1;
        } else {
            amount += 1;
        }

        cv.put("name", name);
        cv.put("_id", id);
        cv.put("price", price);
        cv.put("amount", amount);

        db.insert("orders", "null", cv);

        //
        System.out.println("TESTTTTTTT" + db.insert("orders", "null", cv));
    }

    public void clear() {
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("DROP IF TABLE EXISTS" + "orders");
    }
}
