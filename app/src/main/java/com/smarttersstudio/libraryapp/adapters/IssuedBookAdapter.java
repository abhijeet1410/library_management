package com.smarttersstudio.libraryapp.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smarttersstudio.libraryapp.R;
import com.smarttersstudio.libraryapp.pojos.IssuedBookData;
import com.smarttersstudio.libraryapp.viewholders.IssuedBookViewHolder;

import java.util.ArrayList;

public class IssuedBookAdapter extends RecyclerView.Adapter<IssuedBookViewHolder> {
    private ArrayList<IssuedBookData> l;
    private Context context;

    public IssuedBookAdapter(ArrayList<IssuedBookData> l, Context c) {
        this.l = l;
        this.context = c;
    }
    @NonNull
    @Override
    public IssuedBookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.issued_book_card_layout,viewGroup,false);
        return new IssuedBookViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull IssuedBookViewHolder holder, int position) {
        holder.setBookTitle(""+l.get(position).getBookName());
        holder.setBookId("Book id : "+l.get(position).getBookId());
        holder.setIssuedDate("Issue Date : "+l.get(position).getIssueDate());
        holder.handleCopy(l.get(position).getBookName(),context);
    }

    @Override
    public int getItemCount() {
       return l.size();
    }
}
