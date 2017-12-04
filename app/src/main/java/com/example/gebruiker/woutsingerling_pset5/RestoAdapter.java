package com.example.gebruiker.woutsingerling_pset5;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

/**
 * Created by Wout on 27-11-2017.
 */

public class RestoAdapter extends ResourceCursorAdapter {


    public RestoAdapter(Context context, Cursor cursor) {
        super(context, R.layout.row_todo, cursor);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // set text to name
        TextView name = view.findViewById(R.id.name);
        name.setText(cursor.getString(cursor.getColumnIndex("name")));

        // set price
        TextView price = view.findViewById(R.id.price);
        price.setText(cursor.getString(cursor.getColumnIndex("price")));

        // set amount
        TextView amount = view.findViewById(R.id.amount);
        amount.setText(cursor.getString(cursor.getColumnIndex("amount")));
    }
}
