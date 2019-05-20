package com.smarttersstudio.libraryapp.viewholders;

import android.view.View;
import android.widget.TextView;
import com.smarttersstudio.libraryapp.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class NotificationViewHolder extends RecyclerView.ViewHolder {
    private TextView title, subtitle, time;
    private FloatingActionButton accept, deny;

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

    public void onAcceptClicked(){
        this.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    public void onDenyClicked(){
        this.deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
