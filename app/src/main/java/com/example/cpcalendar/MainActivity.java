package com.example.cpcalendar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity{

    private RequestQueue mRequestQueue;
    private TextView mJsonData;
    private RecyclerView mRvMainScreen;
    private Datum[]data;
//    https://kontests.net/api/v1/all

    private static final String GET_ALL_CONTEST_DETAILS_URL = "https://kontests.net/api/v1/all";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRvMainScreen = findViewById(R.id.rv_main_screen);
        mRvMainScreen.setLayoutManager(new LinearLayoutManager(this));

        mRequestQueue = Volley.newRequestQueue(this);

        fetchJsonResponse();
    }

    private void fetchJsonResponse() {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, GET_ALL_CONTEST_DETAILS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    Gson gson = gsonBuilder.create();

                    data = gson.fromJson(String.valueOf(response), Datum[].class);

                    mRvMainScreen.setAdapter(new RcAdapter(data, new RcAdapter.OnContestInfoListener() {
                        @Override
                        public void onContestInfoClick(int position) {
                            Datum contestDetails = data[position];
//                            Toast.makeText(getApplicationContext(),contestDetails.getName(),Toast.LENGTH_SHORT).show();
                            addEvent(contestDetails.getName());
                        }
                    }));

//                    for(int i=0; i<data.length; i++){
//                        String name = data[i].getName();
//                        String site = data[i].getSite();
//                        mJsonData.append(name + "\n" + site +"\n\n\n");
//                    }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });

        /* Add your Requests to the RequestQueue to execute */
        mRequestQueue.add(stringRequest);
    }

    public void addEvent(String title) {
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, title);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}