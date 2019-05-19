package com.smarttersstudio.libraryapp.viewholders;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.smarttersstudio.libraryapp.R;

public class IssuedBookViewHolder extends RecyclerView.ViewHolder {

    private ImageButton copyButton;
    private TextView tvBookTitle,tvBookId,tvIssuedDate;

    public IssuedBookViewHolder(@NonNull View itemView) {
        super(itemView);

        copyButton = itemView.findViewById(R.id.copy_button);
        tvBookTitle = itemView.findViewById(R.id.book_title);
        tvBookId = itemView.findViewById(R.id.book_id);
        tvIssuedDate = itemView.findViewById(R.id.issued_date);
    }

    public void setBookTitle(String bookTitle){
        tvBookTitle.setText(bookTitle);
    }

    public void setBookId(String bookId){
        tvBookId.setText(bookId);
    }

    public void setIssuedDate(String issuedDate){
        tvIssuedDate.setText(issuedDate);
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
