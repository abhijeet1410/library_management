package com.smarttersstudio.libraryapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smarttersstudio.libraryapp.R;
import com.smarttersstudio.libraryapp.pojos.HistoryData;
import com.smarttersstudio.libraryapp.viewholders.HistoryViewHolder;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryViewHolder> {
    private ArrayList<HistoryData> l;
    private Context context;

    public HistoryAdapter(ArrayList<HistoryData> l, Context context) {
        this.l = l;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.history_card_layout,parent,false);
        return new HistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        holder.setBookAction(l.get(position).getAction());
        holder.setBookTitle(l.get(position).getName());
        holder.setBookId(l.get(position).getBookId());
        holder.setIssuedDate(l.get(position).getTimestamp(),l.get(position).getAction());
        holder.handleCopy(l.get(position).getName(),context);
    }

    @Override
    public int getItemCount() {
        return l.size();
    }
}
