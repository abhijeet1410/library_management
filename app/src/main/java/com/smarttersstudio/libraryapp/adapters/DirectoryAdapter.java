package com.smarttersstudio.libraryapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smarttersstudio.libraryapp.R;
import com.smarttersstudio.libraryapp.pojos.DirectoryData;
import com.smarttersstudio.libraryapp.viewholders.DirectoryViewHolder;

import java.util.ArrayList;

public class DirectoryAdapter extends RecyclerView.Adapter<DirectoryViewHolder> {
    private ArrayList<DirectoryData> list;
    private Context context;

    public DirectoryAdapter(ArrayList<DirectoryData> list, Context context) {
        this.list = list;
        this.context = context;
    }
    public void addData(ArrayList<DirectoryData> list){
        this.list.addAll(list);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public DirectoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DirectoryViewHolder(LayoutInflater.from(context).inflate(R.layout.directory_book_card_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DirectoryViewHolder holder, int position) {
        holder.setAuthor(list.get(position).getAuthor());
        holder.setTitle(list.get(position).getName());
        holder.setAvailable(list.get(position).getAvailable());
        holder.handleCopy(list.get(position).getName(),context);
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
