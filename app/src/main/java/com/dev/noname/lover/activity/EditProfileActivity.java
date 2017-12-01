package com.dev.noname.lover.activity;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dev.noname.lover.R;
import com.dev.noname.lover.Utils.Constants;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {
    private TextInputLayout edtName;
    private TextInputLayout edtStatus;

    private Bundle bundle;


    private DatabaseReference mUserDatabase;
    private FirebaseUser mCurrentUser;


    //View

    private CircleImageView mAvatar;

    private Button btnUpdate;



    private static final int GALLERY_PICK = 1;

    // Storage Firebase
    private StorageReference mImageStorage;

    private ProgressDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        bundle = getIntent().getExtras();

        initView();
        initValue();
        initEvent();
    }


    private void initView() {
        Toolbar toolbar=findViewById(R.id.edit_profile_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit your profile");
        edtName=findViewById(R.id.edt_editProfile_Name);
        edtStatus=findViewById(R.id.edt_editProfile_status);
        btnUpdate=findViewById(R.id.btn_editProfile_update);
        mAvatar=findViewById(R.id.imv_avatar);

        mImageStorage = FirebaseStorage.getInstance().getReference();

        mCurrentUser = FirebaseAuth.getInstance().getCurrentUser();

        String current_uid = mCurrentUser.getUid();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(current_uid);
        mUserDatabase.keepSynced(true);
    }

    private void initValue() {
        if (bundle!=null){
            edtName.getEditText().setText(bundle.getString(Constants.NAME));
            edtStatus.getEditText().setText(bundle.getString(Constants.STATUS));
        }
    }

    private void initEvent() {
        //Listen event
        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String name = dataSnapshot.child(Constants.NAME).getValue().toString();
                final String image = dataSnapshot.child(Constants.IMAGE).getValue().toString();
                String status = dataSnapshot.child(Constants.STATUS).getValue().toString();
               // String thumb_image = dataSnapshot.child(Constants.).getValue().toString();

                edtName.getEditText().setText(name);
                edtStatus.getEditText().setText(status);

                if(!image.equals("default")) {


                    Picasso.with(EditProfileActivity.this).load(image).networkPolicy(NetworkPolicy.OFFLINE)
                            .placeholder(R.drawable.user).into(mAvatar, new Callback() {
                        @Override
                        public void onSuccess() {}

                        @Override
                        public void onError() {

                            Picasso.with(EditProfileActivity.this).load(image).placeholder(R.drawable.user).into(mAvatar);

                        }
                    });

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
        //Save progress//

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog = new ProgressDialog(EditProfileActivity.this);
                mDialog.setTitle("Saving Changes");
                mDialog.setMessage("Please wait while we save the changes");
                mDialog.show();

                Map userMap=new HashMap<>();
                String status = edtStatus.getEditText().getText().toString();
                String name=edtName.getEditText().getText().toString();
                userMap.put(Constants.NAME,name);
                userMap.put(Constants.STATUS,status);

                mUserDatabase.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){

                            mDialog.dismiss();
                            Toast.makeText(EditProfileActivity.this, "Success!", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
        //End save//

    }

    }

