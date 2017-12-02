package com.dev.noname.lover;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;


import com.dev.noname.lover.activity.StartActivity;
import com.dev.noname.lover.activity.SearchActivity;
import com.dev.noname.lover.fragment.ChatsFragment;
import com.dev.noname.lover.fragment.FriendsFragment;
import com.dev.noname.lover.fragment.OtherFragment;
import com.dev.noname.lover.fragment.RequestsFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private LinearLayout mFakeSearchBar;
    private DatabaseReference mUserRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //--------------SearchBar-----------//
        mFakeSearchBar=findViewById(R.id.search_bar_main);
        mFakeSearchBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,SearchActivity.class);
                startActivity(i);
            }
        });
        //-------------BottomBar-----------//
        BottomBar bottomBar =  findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                Fragment selectedFragment = null;
                switch (tabId) {
                    case R.id.tab_chats:
                        selectedFragment = ChatsFragment.newInstance();
                        break;
                    case R.id.tab_friends:
                        selectedFragment = FriendsFragment.newInstance();
                        break;
                    case R.id.tab_request:
                        selectedFragment = RequestsFragment.newInstance();
                        break;
                    case R.id.tab_other:
                        selectedFragment = OtherFragment.newInstance();
                        break;
                }
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(R.anim.enter, R.anim.exit);
                ft.replace(R.id.contentContainer, selectedFragment);
                ft.commit();
            }
        });

        mAuth=FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {


            mUserRef = FirebaseDatabase.getInstance().getReference().child("Users").child(mAuth.getCurrentUser().getUid());

        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){

            sendToStart();

        } else {

            mUserRef.child("online").setValue("true");

        }
    }
    private void sendToStart() {

        Intent i = new Intent(MainActivity.this, StartActivity.class);
        startActivity(i);
        finish();

    }


//    @Override
//    protected void onStop() {
//        super.onStop();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//
//        if(currentUser != null) {
//
//            mUserRef.child("online").setValue("false");
//            mUserRef.child("last_temp").setValue(ServerValue.TIMESTAMP);
//        }
//    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null) {

            mUserRef.child("online").setValue("false");
            mUserRef.child("last_temp").setValue(ServerValue.TIMESTAMP);
        }
    }
}
