package com.smarttersstudio.libraryapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smarttersstudio.libraryapp.R;
import com.smarttersstudio.libraryapp.pojos.DirectoryData;
import com.smarttersstudio.libraryapp.pojos.NotificationData;
import com.smarttersstudio.libraryapp.viewholders.DirectoryViewHolder;
import com.smarttersstudio.libraryapp.viewholders.NotificationViewHolder;

import java.util.ArrayList;

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
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        holder.setTitle(list.get(position).getAction());
        holder.setSubtitle(list.get(position).getBookName(),list.get(position).getBookID());
        holder.setTime(list.get(position).getDateTime());

        holder.onAcceptClicked();
        holder.onDenyClicked();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
