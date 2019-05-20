package com.smarttersstudio.libraryapp.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.smarttersstudio.libraryapp.R;
import com.smarttersstudio.libraryapp.adapters.IssuedBookAdapter;
import com.smarttersstudio.libraryapp.pojos.IssuedBookData;
import com.smarttersstudio.libraryapp.util.Constants;
import com.smarttersstudio.libraryapp.util.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class IssuedBooksActivity extends AppCompatActivity {
    private SuperRecyclerView issuedList;
    private RelativeLayout loader,empty,warning;
    private FirebaseAuth mAuth;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issued_books);
        mAuth=FirebaseAuth.getInstance();
        uid= Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        initViews();
        loadData();
    }

    private void loadData() {
        loader.setVisibility(View.VISIBLE);
        warning.setVisibility(View.GONE);
        empty.setVisibility(View.GONE);
        issuedList.setVisibility(View.GONE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.ISSUED_BOOK,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loader.setVisibility(View.GONE);
                        ArrayList<IssuedBookData> list=new ArrayList<>();
                        try {
                            Gson gson=new Gson();
                            JSONArray array=new JSONArray(response);
                            for(int i=0;i<array.length();i++){
                                String jsonObject=array.getString(i);
                                IssuedBookData data=gson.fromJson(jsonObject,IssuedBookData.class);
                                list.add(data);
                            }
                            if(list.isEmpty()){
                                empty.setVisibility(View.VISIBLE);
                            }else{
                                issuedList.setVisibility(View.VISIBLE);
                                IssuedBookAdapter adapter=new IssuedBookAdapter(list,IssuedBooksActivity.this);
                                issuedList.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            Log.e("issueJSON",e.getMessage());
                            empty.setVisibility(View.GONE);
                            issuedList.setVisibility(View.GONE);
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
                return m;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest,this);
    }

    private void initViews() {
        issuedList=findViewById(R.id.issued_books_list);
        loader=findViewById(R.id.loader_layout);
        empty=findViewById(R.id.empty_layout);
        warning=findViewById(R.id.warning_layout);
        issuedList.setLayoutManager(new LinearLayoutManager(this));
        issuedList.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
                issuedList.setRefreshing(false);
            }
        });
    }

    public void goBack(View view) {
        finish();
    }
}
