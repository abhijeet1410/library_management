package com.smarttersstudio.libraryapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.smarttersstudio.libraryapp.adapters.DirectoryAdapter;
import com.smarttersstudio.libraryapp.adapters.NotificationAdapter;
import com.smarttersstudio.libraryapp.pojos.DirectoryData;
import com.smarttersstudio.libraryapp.pojos.NotificationData;
import com.smarttersstudio.libraryapp.util.Constants;
import com.smarttersstudio.libraryapp.util.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class NotificationActivity extends AppCompatActivity {
    private SuperRecyclerView notificationList;
    private RelativeLayout loader,empty,warning;
    private String uid;
    private NotificationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        initViews();
        loadData();
    }

    private void loadData() {
        loader.setVisibility(View.VISIBLE);
        warning.setVisibility(View.GONE);
        empty.setVisibility(View.GONE);
        notificationList.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.NOTIFICATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loader.setVisibility(View.GONE);
                        ArrayList<NotificationData> list = new ArrayList<>();
                        Log.e("notificationResponse",response);
                        try {
                            Gson gson=new Gson();
                            JSONArray array=new JSONArray(response);
                            for(int i=0;i<array.length();i++){
                                String jsonObject=array.getString(i);
                                NotificationData data=gson.fromJson(jsonObject, NotificationData.class);
                                list.add(data);
                            }
                            if(list.isEmpty()){
                                empty.setVisibility(View.VISIBLE);
                            }else{
                                notificationList.setVisibility(View.VISIBLE);
                                notificationList.setLoadingMore(false);
                                adapter= new NotificationAdapter(list, NotificationActivity.this);
                                notificationList.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            Log.e("notificationJSON",e.getMessage());
                            empty.setVisibility(View.GONE);
                            notificationList.setVisibility(View.GONE);
                            warning.setVisibility(View.VISIBLE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                warning.setVisibility(View.VISIBLE);
                loader.setVisibility(View.GONE);
                Log.e("notificationIssued",error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> m=new HashMap<>();
                m.put("uid", uid);
                return m;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest,this);
    }

    private void initViews() {
        notificationList = findViewById(R.id.notification_list);
        loader=findViewById(R.id.loader_layout);
        empty=findViewById(R.id.empty_layout);
        warning=findViewById(R.id.warning_layout);
        notificationList.setLayoutManager(new LinearLayoutManager(this));

        uid = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();

        notificationList.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
    }
}
