package com.smarttersstudio.libraryapp.activities;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.smarttersstudio.libraryapp.R;
import com.smarttersstudio.libraryapp.util.Constants;
import com.smarttersstudio.libraryapp.util.VolleySingleton;
import com.smarttersstudio.libraryapp.util.views.ProgressDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {

    private EditText emailText, passwordText;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private String email,password,token,uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        initViews();
    }

    private void initViews() {
        emailText = findViewById(R.id.sign_in_email);
        passwordText = findViewById(R.id.sign_in_password);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
    }

    public void doSignIn(final View view) {
        if(validate()){
            progressDialog.showProgress(false,R.string.please_wait_msg);
            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                @Override
                public void onSuccess(InstanceIdResult instanceIdResult) {
                    token = instanceIdResult.getToken();
                    login();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismissProgress();
                    Toast.makeText(SignInActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void login() {
        mAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                uid = authResult.getUser().getUid();
                verifyFromServer();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismissProgress();
                Toast.makeText(SignInActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void verifyFromServer() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismissProgress();
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object.getBoolean("result")){
                                startActivity(new Intent(SignInActivity.this,HomeActivity.class));
                                finish();
                            }else{
                                mAuth.signOut();
                                Toast.makeText(SignInActivity.this, object.getString("reason"), Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Log.e("loginJSON",e.getMessage());
                            mAuth.signOut();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> m=new HashMap<>();
                m.put("uid", uid);
                m.put("fcm",token);
                return m;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest,this);
    }

    public void goToForgotPassword(View view) {
        startActivity(new Intent(SignInActivity.this, ForgotPasswordActivity.class));
    }

    public void goToSignUp(View view) {
        startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
    }

    private boolean validate(){
        email = emailText.getText().toString().trim();
        password = passwordText.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            emailText.setError(getString(R.string.empty_error_msg));
            return false;
        }else if( TextUtils.isEmpty(password)){
            passwordText.setError(getString(R.string.empty_error_msg));
            return false;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(SignInActivity.this,HomeActivity.class));
            finish();
        }
    }
}
