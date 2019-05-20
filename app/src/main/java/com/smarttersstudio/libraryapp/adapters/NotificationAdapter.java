package com.smarttersstudio.libraryapp.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.smarttersstudio.libraryapp.R;
import com.smarttersstudio.libraryapp.pojos.NotificationData;
import com.smarttersstudio.libraryapp.util.Constants;
import com.smarttersstudio.libraryapp.util.VolleySingleton;
import com.smarttersstudio.libraryapp.viewholders.NotificationViewHolder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder> {
    private ArrayList<NotificationData> list;
    private Context context;

    public NotificationAdapter(ArrayList<NotificationData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void addData(ArrayList<NotificationData> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotificationViewHolder(LayoutInflater.from(context).inflate(R.layout.notification_card_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, final int position) {
        holder.setTitle(list.get(position).getAction());
        holder.setSubtitle(list.get(position).getBookName(),list.get(position).getBookID());
        holder.setTime(list.get(position).getDateTime());
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resolve(list.get(position).getRequestID(), "1",position);
            }
        });
        holder.deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resolve(list.get(position).getRequestID(), "0",position);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void resolve(final String requestID, final String action, final int position) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.RESOLVE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("notificationResponse",response);
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object.getString("status").equalsIgnoreCase("success")){
                                list.remove(position);
                                notifyItemRemoved(position);
                                Toast.makeText(context, object.getString("reason"), Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, object.getString("reason"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Log.e("resolveJSON",e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("resolvedIssued",error.getMessage());
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> m=new HashMap<>();
                m.put("request_id", requestID);
                m.put("action",action);
                return m;
            }
        };
        VolleySingleton.getInstance(context).addToRequestQueue(stringRequest,context);
    }
}
