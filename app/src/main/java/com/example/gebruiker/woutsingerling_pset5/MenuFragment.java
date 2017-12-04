package com.example.gebruiker.woutsingerling_pset5;


import android.content.ClipData;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends ListFragment {

    // global variables
    ArrayList<String> list = new ArrayList<String>();
    ArrayList<Double> prices = new ArrayList<Double>();
    private RestoDatabase db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = RestoDatabase.getInstance(getContext());

        Bundle arguments = this.getArguments();
        final String receivedcategory = arguments.getString("category");

        // Instantiate the Request queue
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url ="https://resto.mprog.nl/menu";

        // Connect to the database
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(

                Request.Method.GET, url, null,

                new Response.Listener<JSONObject>() {

                    private JSONObject jsonObject = null;
                    private JSONArray jsonArray = null;

                    @Override
                    public void onResponse(JSONObject response) {

                        // In success this will be executed:
                        try {
                            jsonObject = new JSONObject(response.toString());
                            jsonArray = jsonObject.getJSONArray("items");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // Converting JSONArray to ArrayList
                        // https://stackoverflow.com/questions/17037340/converting-jsonarray-to-arraylist
                        for (int i=0; i<jsonArray.length(); i++) {
                            try {
                                if (jsonArray.getJSONObject(i).optString("category").equals(receivedcategory)) {
                                    //list.add(jsonArray.getJSONObject(i).optString("name") +"     $" + jsonArray.getJSONObject(i).optString("price") );
                                    list.add(jsonArray.getJSONObject(i).optString("name"));
                                    prices.add(jsonArray.getJSONObject(i).optDouble("price"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        // call the adapter
                        setUpdateAdapter();
                    }
                },

                // If connection fails error will be shown.
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        );

        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    //function to set the adapter
    private void setUpdateAdapter() {
        ArrayAdapter<String> thisAdapter =
                new ArrayAdapter<String>(
                        getContext(),
                        android.R.layout.simple_list_item_1,
                        list
                );
        this.setListAdapter(thisAdapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.manu_fragment, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String ItemPicked = list.get((int)id) + "added to your oder";
        Toast.makeText(getContext(), ItemPicked, Toast.LENGTH_SHORT).show();

        db.addItem(list.get((int)id), prices.get((int)id),  id);

    }
}
