package com.example.gebruiker.woutsingerling_pset5;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.ResourceCursorAdapter;

/**
 * Created by Wout on 27-11-2017.
 */

public class RestoAdapter extends ResourceCursorAdapter {


    public RestoAdapter(Context context, Cursor cursor) {
        super(context, R.id.actions, cursor);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        // do stuff
    }
}
