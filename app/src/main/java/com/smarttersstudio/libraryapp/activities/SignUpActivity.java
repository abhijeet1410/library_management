package com.smarttersstudio.libraryapp.activities;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
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
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    private EditText nameText,emailText,phoneText,passwordText;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private String name,email,phone,password,token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initViews();
    }

    private void initViews() {
        nameText = findViewById(R.id.sign_up_name);
        emailText = findViewById(R.id.sign_up_email);
        phoneText = findViewById(R.id.sign_up_phone);
        passwordText = findViewById(R.id.sign_up_password);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
    }

    public void doRegister(final View view) {
        if(validate()){
            progressDialog.showProgress(false,R.string.please_wait_msg);
            FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(new OnSuccessListener<InstanceIdResult>() {
                @Override
                public void onSuccess(InstanceIdResult instanceIdResult) {
                    token = instanceIdResult.getToken();
                    register();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismissProgress();
                    Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void register() {
        mAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                sendData();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismissProgress();
                Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendData() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismissProgress();
                        try {
                            JSONObject object = new JSONObject(response);
                            if(object.getBoolean("result")){
                                Toast.makeText(SignUpActivity.this, "Successfully Registered!! Contact the librarian to verify your account", Toast.LENGTH_LONG).show();
                                mAuth.signOut();
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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
                m.put("uid", Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
                m.put("name",name);
                m.put("phone",phone);
                m.put("email",email);
                m.put("fcm",token);
                return m;
            }
        };
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest,this);
    }

    public void goToLogin(View view) {
        finish();
    }

    private boolean validate(){
        name = nameText.getText().toString().trim();
        email = emailText.getText().toString().trim();
        phone = phoneText.getText().toString().trim();
        password = passwordText.getText().toString().trim();

        if(TextUtils.isEmpty(name)){
            nameText.setError(getResources().getString(R.string.empty_error_msg));
            return false;
        }else if(TextUtils.isEmpty(email)){
            emailText.setError(getResources().getString(R.string.empty_error_msg));
            return false;
        }else if(TextUtils.isEmpty(phone)){
            phoneText.setError(getResources().getString(R.string.empty_error_msg));
            return false;
        }else if(TextUtils.isEmpty(password)){
            passwordText.setError(getResources().getString(R.string.empty_error_msg));
            return false;
        }
        return true;
    }

}
