package com.smarttersstudio.libraryapp.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smarttersstudio.libraryapp.R;

public class DirectoryViewHolder extends RecyclerView.ViewHolder {
    private TextView titleText,authorText,availableText;
    public DirectoryViewHolder(@NonNull View itemView) {
        super(itemView);
        titleText=itemView.findViewById(R.id.book_title);
        authorText=itemView.findViewById(R.id.book_author);
        availableText=itemView.findViewById(R.id.book_available);
    }
    public void setTitle(String title){
        titleText.setText("Book Name :"+title);
    }
    public void setAuthor(String author){
        authorText.setText("Author :"+author);
    }
    public void setAvailable(String available){
        availableText.setText("Available :"+available);
    }

}
