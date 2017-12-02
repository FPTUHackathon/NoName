package com.dev.noname.lover.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.dev.noname.lover.MainActivity;
import com.dev.noname.lover.R;
import com.dev.noname.lover.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class LoginActivity extends AppCompatActivity {
    private Toolbar mToolbar;

    private TextInputLayout mLoginEmail;
    private TextInputLayout mLoginPassword;

    private FirebaseAuth mAuth;
    private DatabaseReference mReference;

    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //-------ANH XA------//
        mLoginEmail =  findViewById(R.id.edt_login_email);
        mLoginPassword =  findViewById(R.id.edt_login_password);
        Button mLogin_btn = findViewById(R.id.btn_login);
        dialog=new ProgressDialog(this);
        //Firebase
        mAuth=FirebaseAuth.getInstance();
        mReference= FirebaseDatabase.getInstance().getReference().child("Users");
        //--------------Tool bar--------------------//
        mToolbar =  findViewById(R.id.login_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Login");

        //Event
        mLogin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mLoginEmail.getEditText().getText().toString();
                String password = mLoginPassword.getEditText().getText().toString();

                if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)){

                    dialog.setTitle("Logging In");
                    dialog.setMessage("Please wait a few seconds....");
                    dialog.setCanceledOnTouchOutside(false);
                    dialog.show();

                    login(email, password);

                }
            }
        });
    }

    private void login(String email, String password) {
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    dialog.dismiss();
                    String device_token= FirebaseInstanceId.getInstance().getToken();
                    String userID=mAuth.getCurrentUser().getUid();
                    mReference.child(userID).child(Constants.DEVICE_TOKEN).setValue(device_token);
                    Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                    mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainIntent);
                    finish();
                }else {
                    dialog.hide();
                    String error = task.getException().getMessage().toString();
                    Toast.makeText(LoginActivity.this, "Error : " + error, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
