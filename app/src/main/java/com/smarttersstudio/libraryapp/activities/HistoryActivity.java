package com.smarttersstudio.libraryapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.smarttersstudio.libraryapp.R;
import com.smarttersstudio.libraryapp.adapters.HistoryAdapter;
import com.smarttersstudio.libraryapp.adapters.IssuedBookAdapter;
import com.smarttersstudio.libraryapp.pojos.HistoryData;
import com.smarttersstudio.libraryapp.pojos.HistoryData;
import com.smarttersstudio.libraryapp.util.Constants;
import com.smarttersstudio.libraryapp.util.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HistoryActivity extends AppCompatActivity {

    private SuperRecyclerView historyList;
    private RelativeLayout loader,empty,warning;
    private FirebaseAuth mAuth;
    private String uid;
    private int offset = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        mAuth=FirebaseAuth.getInstance();
        uid= Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        initViews();
        loadData();
    }

    private void loadData() {
        loader.setVisibility(View.VISIBLE);
        warning.setVisibility(View.GONE);
        empty.setVisibility(View.GONE);
        historyList.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.HISTORY_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loader.setVisibility(View.GONE);
                        ArrayList<HistoryData> list=new ArrayList<>();
                        try {
                            Gson gson=new Gson();
                            JSONArray array=new JSONArray(response);
                            for(int i=0;i<array.length();i++){
                                String jsonObject=array.getString(i);
                                HistoryData data=gson.fromJson(jsonObject, HistoryData.class);
                                list.add(data);
                            }
                            if(list.isEmpty()){
                                empty.setVisibility(View.VISIBLE);
                            }else{
                                historyList.setVisibility(View.VISIBLE);
                                HistoryAdapter adapter=new HistoryAdapter(list,HistoryActivity.this);
                                historyList.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            Log.e("issueJSON",e.getMessage());
                            empty.setVisibility(View.GONE);
                            historyList.setVisibility(View.GONE);
                            warning.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                warning.setVisibility(View.VISIBLE);
                loader.setVisibility(View.GONE);
                Log.e("errorIssued",error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> m=new HashMap<>();
                m.put("uid", uid);
                m.put("offset",String.valueOf(offset));
                return m;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest,this);
    }

    private void initViews() {
        historyList =findViewById(R.id.history_list);
        loader=findViewById(R.id.loader_layout);
        empty=findViewById(R.id.empty_layout);
        warning=findViewById(R.id.warning_layout);
        historyList.setLayoutManager(new LinearLayoutManager(this));
        historyList.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
                historyList.setRefreshing(false);
            }
        });
        historyList.setOnMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
                if(overallItemsCount/20>0) {
                    offset += 20;
                    loadData();
                }
            }
        });
    }

    public void goBack(View view) {
        startActivity(new Intent(this,HomeActivity.class));
    }
}
