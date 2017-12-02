package com.dev.noname.lover.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.dev.noname.lover.R;
import com.dev.noname.lover.utils.Constants;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersActivity extends AppCompatActivity {
    private ImageButton btnMore;
    private ImageButton btnBack;
    private CircleImageView imv_avatar;
    private TextView tvName;
    private TextView tvStatus;
    private Button btnAddFriend;
    private Button btnFollowing;

    private DatabaseReference mUsersDatabase;
    private DatabaseReference mFriendDatabase;
    private DatabaseReference mFriendReqDatabase;
    private FirebaseUser mCurrent_User;
    private DatabaseReference mRootRef;

    private String mCurrent_State;
    private String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        btnMore=findViewById(R.id.imv_icon_more);
        btnBack=findViewById(R.id.imv_icon_back);
        btnAddFriend=findViewById(R.id.btn_profile_add_friend);
        btnFollowing=findViewById(R.id.btn_profile_following);
        imv_avatar=findViewById(R.id.imv_avatar);
        tvName=findViewById(R.id.tvName);
        tvStatus=findViewById(R.id.tv_profile_status);
        //Event
        initDefaultValue();
        initValue();
        initEvent();


    }

    private void initDefaultValue() {

    }

    private void initValue() {
        user_id = getIntent().getStringExtra(Constants.USER_ID);
        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
        mFriendReqDatabase = FirebaseDatabase.getInstance().getReference().child("Friend_req");
        mFriendDatabase = FirebaseDatabase.getInstance().getReference().child("Friend");
        mRootRef=FirebaseDatabase.getInstance().getReference();
        mCurrent_User = FirebaseAuth.getInstance().getCurrentUser();
    }

    private void initEvent() {
        mUsersDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String display_name = dataSnapshot.child("name").getValue().toString();
                String status = dataSnapshot.child("status").getValue().toString();
                String image = dataSnapshot.child("image").getValue().toString();

                tvName.setText(display_name);
                tvStatus.setText(status);

                Picasso.with(UsersActivity.this).load(image).placeholder(R.drawable.user).into(imv_avatar);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    //FriendRequestListener

        mFriendReqDatabase.child(mCurrent_User.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChild(user_id)){

                    String req_type = dataSnapshot.child(user_id).child("request_type").getValue().toString();

                    if(req_type.equals("received")){

                        mCurrent_State = "req_received";
                        btnAddFriend.setText("Accept request");

                    } else if(req_type.equals("sent")) {

                        mCurrent_State = "sent";
                        btnAddFriend.setText("Cancel Friend Request");
                        btnAddFriend.setClickable(false);

                    }


                } else {


                    mFriendDatabase.child(mCurrent_User.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if(dataSnapshot.hasChild(user_id)){

                                mCurrent_State = "friends";
                                btnAddFriend.setText("UnFriend");

                            }else {
                                mCurrent_State = "not_friends";
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {}
                    });

                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });

        //--------------button add friend event-----------------//
        btnAddFriend.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                btnAddFriend.setEnabled(false);

                if(mCurrent_State.equals("not_friends")){


                    DatabaseReference newNotificationRef = mRootRef.child("notifications").child(user_id).push();
                    String newNotificationId = newNotificationRef.getKey();

                    HashMap<String, String> notificationData = new HashMap<>();
                        notificationData.put("from", mCurrent_User.getUid());
                    notificationData.put("type", "request");

                    Map requestMap = new HashMap();
                    requestMap.put("Friend_req/" + mCurrent_User.getUid() + "/" + user_id + "/request_type", "sent");
                    requestMap.put("Friend_req/" + user_id + "/" + mCurrent_User.getUid() + "/request_type", "received");
                    requestMap.put("notifications/" + user_id + "/" + newNotificationId, notificationData);

                    mRootRef.updateChildren(requestMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {

                            if(databaseError != null){

                                Toast.makeText(UsersActivity.this, "There was some error in sending request", Toast.LENGTH_SHORT).show();

                            } else {

                                mCurrent_State = "req_sent";
                                btnAddFriend.setText("Cancel Friend Request");

                            }

                            btnAddFriend.setEnabled(true);


                        }
                    });

                }


                // - -------------- CANCEL REQUEST STATE ------------

                if(mCurrent_State.equals("req_sent")){

                    mFriendReqDatabase.child(mCurrent_User.getUid()).child(user_id).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            mFriendReqDatabase.child(user_id).child(mCurrent_User.getUid()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {


                                    btnAddFriend.setEnabled(true);
                                    mCurrent_State = "not_friends";
                                    btnAddFriend.setText("Send Friend Request");


                                }
                            });

                        }
                    });

                }


                // ------------ REQ RECEIVED STATE ----------

                if(mCurrent_State.equals("req_received")){

                    final String currentDate = DateFormat.getDateTimeInstance().format(new Date());

                    Map friendsMap = new HashMap();
                    friendsMap.put("Friend/" + mCurrent_User.getUid() + "/" + user_id + "/date", currentDate);
                    friendsMap.put("Friend/" + user_id + "/"  + mCurrent_User.getUid() + "/date", currentDate);


                    friendsMap.put("Friend_req/" + mCurrent_User.getUid() + "/" + user_id, null);
                    friendsMap.put("Friend_req/" + user_id + "/" + mCurrent_User.getUid(), null);


                    mRootRef.updateChildren(friendsMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {


                            if(databaseError == null){

                                btnAddFriend.setEnabled(true);
                                mCurrent_State = "friends";
                                btnAddFriend.setText("UnFriend");

                              //  mDeclineBtn.setVisibility(View.INVISIBLE);
                                //mDeclineBtn.setEnabled(false);

                            } else {

                                String error = databaseError.getMessage();

                                Toast.makeText(UsersActivity.this, error, Toast.LENGTH_SHORT).show();


                            }

                        }
                    });

                }


                // ------------ UNFRIENDS ---------

                if(mCurrent_State.equals("friends")){

                    Map unfriendMap = new HashMap();
                    unfriendMap.put("Friend/" + mCurrent_User.getUid() + "/" + user_id, null);
                    unfriendMap.put("Friend/" + user_id + "/" + mCurrent_User.getUid(), null);

                    mRootRef.updateChildren(unfriendMap, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {


                            if(databaseError == null){

                                mCurrent_State = "not_friends";
                                btnAddFriend.setText("Add friend");

                               // mDeclineBtn.setVisibility(View.INVISIBLE);
                              //  mDeclineBtn.setEnabled(false);

                            } else {

                                String error = databaseError.getMessage();

                                Toast.makeText(UsersActivity.this, error, Toast.LENGTH_SHORT).show();


                            }

                            btnAddFriend.setEnabled(true);

                        }
                    });

                }

            }
        });


        //End button event


    }
}
