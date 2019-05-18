package com.smarttersstudio.libraryapp.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.smarttersstudio.libraryapp.R;
import com.smarttersstudio.libraryapp.util.views.ProgressDialog;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText emailText;
    private FirebaseAuth mAuth;
    private String email;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initViews();
    }

    private void initViews() {
        emailText = findViewById(R.id.forgot_pass_email);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
    }

    public void doResetPassword(View view) {
        if(validate()){
            progressDialog.showProgress(false,R.string.please_wait_msg);
            mAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    progressDialog.dismissProgress();
                    Toast.makeText(ForgotPasswordActivity.this, "Email sent successfully check you email to reset password", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismissProgress();
                    Toast.makeText(ForgotPasswordActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private boolean validate(){
        email = emailText.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            emailText.setError(getResources().getString(R.string.empty_error_msg));
            return false;
        }
        return true;
    }
}
