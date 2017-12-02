package com.dev.noname.lover.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.dev.noname.lover.R;
import com.dev.noname.lover.utils.Constants;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;


import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private RecyclerView mUserInfo;

    private DatabaseReference mUsersReference;

    private TextView tvName;
    private TextView tvStatus;
    private CircleImageView imv_avatar;
    private ImageButton btnMore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        final String userId=getIntent().getStringExtra(Constants.USER_ID);
        mUsersReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);

        mUserInfo = findViewById(R.id.recycle_view_profile_info);
        mUserInfo.setHasFixedSize(true);
        mUserInfo.setLayoutManager(mLayoutManager);
        initView();
        initValue();
        initEvent();
    }


    private void initView() {
        tvName=findViewById(R.id.tvName);
        tvStatus=findViewById(R.id.tv_profile_status);
        imv_avatar=findViewById(R.id.imv_avatar);
        btnMore=findViewById(R.id.imv_icon_more);
    }

    private void initValue() {

        mUsersReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String display_name=dataSnapshot.child(Constants.NAME).getValue().toString();
                String image_link=dataSnapshot.child(Constants.IMAGE).getValue().toString();
                String status=dataSnapshot.child(Constants.STATUS).getValue().toString();
                tvName.setText(display_name);
                tvStatus.setText(status);
                Picasso.with(ProfileActivity.this).load(image_link).placeholder(R.drawable.user).into(imv_avatar);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
    private void initEvent() {
    btnMore.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i=new Intent(ProfileActivity.this,EditProfileActivity.class);
            Bundle b=new Bundle();
            b.putString(Constants.NAME,tvName.getText().toString());
            b.putString(Constants.STATUS,tvStatus.getText().toString());
            i.putExtras(b);
            startActivity(i);
        }
    });
    }


}
