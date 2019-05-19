package com.smarttersstudio.libraryapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
    public void onBindViewHolder(@NonNull IssuedBookViewHolder issuedBookViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
       return l.size();
    }
}
