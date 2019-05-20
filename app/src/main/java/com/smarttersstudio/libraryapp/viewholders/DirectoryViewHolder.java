package com.smarttersstudio.libraryapp.viewholders;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smarttersstudio.libraryapp.R;

public class DirectoryViewHolder extends RecyclerView.ViewHolder {
    private TextView titleText,authorText,availableText;
    private ImageButton copyButton;
    public DirectoryViewHolder(@NonNull View itemView) {
        super(itemView);
        titleText=itemView.findViewById(R.id.book_title);
        authorText=itemView.findViewById(R.id.book_author);
        availableText=itemView.findViewById(R.id.book_available);
        copyButton=itemView.findViewById(R.id.copy_button);
    }
    public void setTitle(String title){
        titleText.setText(title);
    }
    public void setAuthor(String author){
        authorText.setText("Author : "+author);
    }
    public void setAvailable(String available){
        availableText.setText("Available : "+available);
    }

    public void handleCopy(final String bookTitle, final Context context){
        copyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Title", bookTitle);
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, "Book name copied to clipboard", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
