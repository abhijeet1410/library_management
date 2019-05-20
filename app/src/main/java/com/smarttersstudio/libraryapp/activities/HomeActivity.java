package com.smarttersstudio.libraryapp.activities;

import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
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

    public void doSignOut(View view) {
        AlertDialog.Builder ad = new AlertDialog.Builder(this);
        ad.setMessage(getResources().getString(R.string.log_out_dialog_msg));
        ad.setPositiveButton(getResources().getString(R.string.yes_msg), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(HomeActivity.this,SignInActivity.class));
                finish();
            }
        }).setNegativeButton(getResources().getString(R.string.no_msg), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).show();
    }
}
