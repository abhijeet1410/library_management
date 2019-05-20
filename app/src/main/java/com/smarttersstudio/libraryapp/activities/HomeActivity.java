package com.smarttersstudio.libraryapp.activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.smarttersstudio.libraryapp.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void goToAboutUs(View view) {
        startActivity(new Intent(this,AboutUsActivity.class));
    }

    public void gotoIssuedBooks(View view) {
        startActivity(new Intent(this,IssuedBooksActivity.class));
    }

    public void gotoBookDirectory(View view) {
        startActivity(new Intent(this, DirectoryActivity.class));
    }

    public void gotoHistory(View view) {
        startActivity(new Intent(this,HistoryActivity.class));
    }

    public void gotoNotification(View view) {
        startActivity(new Intent(this,NotificationActivity.class));
    }
}
