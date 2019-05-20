package com.smarttersstudio.libraryapp.viewholders;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.smarttersstudio.libraryapp.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smarttersstudio.libraryapp.activities.NotificationActivity;
import com.smarttersstudio.libraryapp.adapters.NotificationAdapter;
import com.smarttersstudio.libraryapp.pojos.NotificationData;
import com.smarttersstudio.libraryapp.util.Constants;
import com.smarttersstudio.libraryapp.util.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotificationViewHolder extends RecyclerView.ViewHolder {
    private TextView title, subtitle, time;
    public FloatingActionButton accept, deny;

    public NotificationViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.notification_card_title);
        subtitle = itemView.findViewById(R.id.notification_card_subtitle);
        time = itemView.findViewById(R.id.notification_card_time);
        accept = itemView.findViewById(R.id.notification_card_tick);
        deny = itemView.findViewById(R.id.notification_card_cross);
    }
    public void setTitle(String action){
        this.title.setText("Request for "+action);
    }

    public void setSubtitle(String bookName, String bookID){
        this.subtitle.setText(bookName+" ("+bookID+")");
    }

    public void setTime(String time){
        this.time.setText(time);
    }

}
