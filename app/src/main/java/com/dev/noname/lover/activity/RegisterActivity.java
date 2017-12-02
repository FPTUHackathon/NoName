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
import com.dev.noname.lover.R;


import com.dev.noname.lover.MainActivity;
import com.dev.noname.lover.utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;


public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout mDisplayName;
    private TextInputLayout mEmail;
    private TextInputLayout mPassword;
    private TextInputLayout mConfirmPassword;

    private ProgressDialog dialog;

    private FirebaseAuth mAuth;
    private DatabaseReference mReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //----------Toolbar------------//
        Toolbar mToolbar = findViewById(R.id.register_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //------------Firebas------//

        mAuth=FirebaseAuth.getInstance();

        //------------ANHXA------------//
        dialog= new ProgressDialog(this);

        mDisplayName = findViewById(R.id.edt_reg_displayname);
        mEmail = findViewById(R.id.edt_reg_email);
        mPassword =  findViewById(R.id.edt_reg_password);
        Button mCreateBtn = findViewById(R.id.btn_reg_create);
        mConfirmPassword = findViewById(R.id.edt_reg_confirm_password);

        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String display_name = mDisplayName.getEditText().getText().toString();
                String email = mEmail.getEditText().getText().toString();
                String password = mPassword.getEditText().getText().toString();
                String confirm_password = mConfirmPassword.getEditText().getText().toString();
                if(!TextUtils.isEmpty(display_name) || !TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)){

                    if (display_name.length()<4 || display_name.length()>32)
                        toaster("Display Name must contain 4-32 characters");
                    if(!email.contains("@"))
                        toaster("Wrong email! Ex: lover@gmail.com");
                    if (password.length()<6)
                        toaster("Password must have more than 6 characters");
                    if (!password.equalsIgnoreCase(confirm_password))
                        toaster("Confirm password don't match password");

                    if(!(display_name.length()<4 || display_name.length()>32)&&email.contains("@")
                            &&!(password.length()<6)&&password.equalsIgnoreCase(confirm_password)){
                        dialog.setTitle("Registering User");
                        dialog.setMessage("Please wait while we create your account !");
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();
                        register_user(display_name, email, password);
                    }


                }else
                    Toast.makeText(RegisterActivity.this, "All fields should be input!", Toast.LENGTH_SHORT).show();




            }
        });
    }

    private void register_user(final String display_name, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    FirebaseUser user = mAuth.getCurrentUser();
                    String userID = null;
                    if (user != null)
                        userID = user.getUid();


                    //Get device token//
                    String device_token= FirebaseInstanceId.getInstance().getToken();

                    mReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);

                    HashMap<String,String> userInfo=new HashMap<>();
                    userInfo.put(Constants.NAME,display_name);
                    userInfo.put(Constants.STATUS,"Hi there! I'm using Lover App");
                    userInfo.put(Constants.IMAGE,"default");
                    userInfo.put(Constants.THUMB_IMAGE,"default");
                    userInfo.put(Constants.DEVICE_TOKEN,device_token);
                    mReference.setValue(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                dialog.dismiss();
                                Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(mainIntent);
                                finish();
                            }
                        }
                    });

                }else{
                    dialog.hide();
                    toaster("Something went wrong. Please try again!" +  task.getException().toString());
                }
            }
        });

    }
    private void toaster(String mess){
        Toast.makeText(RegisterActivity.this,mess, Toast.LENGTH_SHORT).show();
    }
}
