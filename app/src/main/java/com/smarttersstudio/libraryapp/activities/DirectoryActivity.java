package com.smarttersstudio.libraryapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.smarttersstudio.libraryapp.R;
import com.smarttersstudio.libraryapp.adapters.DirectoryAdapter;
import com.smarttersstudio.libraryapp.pojos.DirectoryData;
import com.smarttersstudio.libraryapp.util.Constants;
import com.smarttersstudio.libraryapp.util.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DirectoryActivity extends AppCompatActivity {
    private SuperRecyclerView directoryList;
    private RelativeLayout loader,empty,warning;
    private String query="";
    private int offset=0;
    private EditText searchText;
    private DirectoryAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory);
        initViews();
        loadData();
    }

    private void loadData() {
        loader.setVisibility(View.VISIBLE);
        warning.setVisibility(View.GONE);
        empty.setVisibility(View.GONE);
        directoryList.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.DIRECTORY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loader.setVisibility(View.GONE);
                        ArrayList<DirectoryData> list=new ArrayList<>();
                        Log.e("directoryResponse",response);
                        try {
                            Gson gson=new Gson();
                            JSONArray array=new JSONArray(response);
                            for(int i=0;i<array.length();i++){
                                String jsonObject=array.getString(i);
                                DirectoryData data=gson.fromJson(jsonObject,DirectoryData.class);
                                list.add(data);
                            }
                            if(list.isEmpty()){
                                empty.setVisibility(View.VISIBLE);
                            }else{
                                directoryList.setVisibility(View.VISIBLE);
                                directoryList.setLoadingMore(false);
                                if(offset==0) {
                                    adapter= new DirectoryAdapter(list, DirectoryActivity.this);
                                    directoryList.setAdapter(adapter);
                                }else{
                                    adapter.addData(list);
                                }
                            }
                        } catch (JSONException e) {
                            Log.e("directoryJSON",e.getMessage());
                            empty.setVisibility(View.GONE);
                            directoryList.setVisibility(View.GONE);
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
                m.put("q", query);
                m.put("offset",String.valueOf(offset));
                return m;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest,this);
    }

    private void initViews() {
        loader=findViewById(R.id.loader_layout);
        empty=findViewById(R.id.empty_layout);
        warning=findViewById(R.id.warning_layout);
        searchText=findViewById(R.id.search_text);
        directoryList=findViewById(R.id.directory_list);
        directoryList.setLayoutManager(new LinearLayoutManager(this));
        directoryList.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                offset=0;
                loadData();
            }
        });
        directoryList.setOnMoreListener(new OnMoreListener() {
            @Override
            public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
                if(overallItemsCount/20>0) {
                    offset += 20;
                    loadData();
                }
            }
        });
    }

    public void search(View view) {
        query=searchText.getText().toString();
        offset=0;
        loadData();
    }

    public void goBack(View view) {
        startActivity(new Intent(this,HomeActivity.class));
    }
}
