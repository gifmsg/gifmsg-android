package com.gifmsg.application;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.DraweeView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity  {

    MyRecyclerViewAdapter adapter;
    public Context mainContext;
    public TextView mTextView;
    public Button mSend;
    public JSONObject obj;
    public JSONArray jArray;
    public ArrayList<String> urlArray = new ArrayList<>();
    public int arrayIndex = 1;
    public DraweeView mDrawee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);

        mSend = findViewById(R.id.button);
        mDrawee = findViewById(R.id.gifs);
        mainContext = this;


    }


    public void search(View view){
        String searchString;
        EditText mSearch = findViewById(R.id.editText);
        searchString = mSearch.getText().toString();
        final String key = "T0TKTfdnfMi0mToCYjCuZPOK1hkn8Cy5";

        RequestQueue queue = Volley.newRequestQueue(this);
        final String url ="https://api.giphy.com/v1/gifs/search?api_key=" + key + "&q=" + searchString + "&limit=25&offset=0&rating=G&lang=en";

        System.out.println(url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String responseString = response.toString();
                        try {

                            urlArray.clear();

                            obj = new JSONObject(responseString);

                            Log.d("My App", obj.toString());
                            Log.d("phonetype value ", obj.getString("phonetype"));

                        } catch (Throwable tx) {
                            Log.e("My App", "Could not parse malformed JSON");
                        }

                        try {
                            jArray = obj.getJSONArray("data");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int i=0; i < jArray.length(); i++)
                        {
                            try {
                                JSONObject oneObject = jArray.getJSONObject(i).getJSONObject("images").getJSONObject("original");
                                // Pulling items from the array
                                String oneObjectsItem = oneObject.getString("url");
                                urlArray.add(oneObjectsItem);
                                Log.d("Url array content", urlArray.get(i));
//                                mTextView.setText("Response is: " + oneObjectsItem );
                            } catch (JSONException e) {
                                // Oops
                            }
                        }

                        RecyclerView recyclerView = findViewById(R.id.recyclerHolder);
                        LinearLayoutManager layoutManager
                                = new LinearLayoutManager(mainContext, LinearLayoutManager.HORIZONTAL, false);
                        recyclerView.setLayoutManager(layoutManager);
                        adapter = new MyRecyclerViewAdapter(mainContext, urlArray);
                        recyclerView.setAdapter(adapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("My App", "Could not parse malformed JSON");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

}
