package com.example.gebruiker.woutsingerling_pset5;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * Created by Wout on 27-11-2017.
 */

public class OrderFragment extends DialogFragment implements View.OnClickListener {
//public class OrderFragment extends DialogFragment {

    private RestoDatabase db;
    private RestoAdapter adapter;

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        // get al the items from the database
        db = RestoDatabase.getInstance(getContext());
        Cursor cursor = db.selectAll();

        // Cursor count = 0;
        Log.d("Test!!!!!", "onViewStateRestored: " + cursor.getCount());

        // get the listview
        ListView listView = (ListView) getView().findViewById(R.id.dialog_view);

        // adapter
        adapter = new RestoAdapter(getContext(), cursor);
        listView.setAdapter(adapter);
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.order_fragment, container, false);

        Button b = (Button) view.findViewById(R.id.cancel_button);
        b.setOnClickListener(this);


        Button o = (Button) view.findViewById(R.id.order_button);
        o.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel_button:
                
                Toast.makeText(getContext(),"You deleted your order", Toast.LENGTH_LONG);

                db.clear();
                break;

            case  R.id.order_button:

                RequestQueue queue = Volley.newRequestQueue(getContext());
                String url ="https://resto.mprog.nl/order";

                JsonObjectRequest jsObjRequest = new JsonObjectRequest
                        (Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                String toast = response.toString();

                                Toast.makeText(getContext(), toast, Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO Auto-generated method stub

                            }
                        });
                queue.add(jsObjRequest);

                break;

        }
    }
}
