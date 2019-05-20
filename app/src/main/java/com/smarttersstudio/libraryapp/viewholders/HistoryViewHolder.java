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

public class HistoryViewHolder extends RecyclerView.ViewHolder {
    private TextView tvBookTitle,tvBookId,tvIssuedDate,tvBookAction;
    private ImageButton copyButton;
    public HistoryViewHolder(@NonNull View itemView) {
        super(itemView);
        tvBookAction = itemView.findViewById(R.id.book_action);
        tvBookTitle = itemView.findViewById(R.id.book_title);
        tvBookId = itemView.findViewById(R.id.book_id);
        tvIssuedDate = itemView.findViewById(R.id.issued_date);
        copyButton = itemView.findViewById(R.id.copy_button);
    }

    public void setBookAction(String action){
        tvBookAction.setText(action);
        if(action.equalsIgnoreCase("issued")){
            tvBookAction.setBackgroundResource(R.drawable.green_stroke);
            tvBookAction.setTextColor(itemView.getResources().getColor(R.color.color_green));
        }else{
            tvBookAction.setBackgroundResource(R.drawable.red_stroke);
            tvBookAction.setTextColor(itemView.getResources().getColor(R.color.color_red));
        }
    }

    public void setBookTitle(String bookTitle){
        tvBookTitle.setText(bookTitle);
    }

    public void setBookId(String bookId){
        tvBookId.setText("Book ID : "+bookId);
    }

    public void setIssuedDate(String issuedDate, String action){
        if(action.equalsIgnoreCase("issued")){
            tvIssuedDate.setText("Issued Date : "+issuedDate);
        }else{
            tvIssuedDate.setText("Returned Date : "+issuedDate);
        }
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
