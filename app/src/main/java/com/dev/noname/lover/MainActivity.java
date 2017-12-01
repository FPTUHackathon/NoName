package com.dev.noname.lover;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;


import com.dev.noname.lover.activity.UsersActivity;
import com.dev.noname.lover.activity.StartActivity;
import com.dev.noname.lover.activity.SearchActivity;
import com.dev.noname.lover.fragment.ChatsFragment;
import com.dev.noname.lover.fragment.FriendsFragment;
import com.dev.noname.lover.fragment.OtherFragment;
import com.dev.noname.lover.fragment.RequestsFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private LinearLayout mFakeSearchBar;
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser == null){

            sendToStart();

        } else {

           // mUserRef.child("online").setValue("true");

        }
    }
    private void sendToStart() {

        Intent i = new Intent(MainActivity.this, StartActivity.class);
        startActivity(i);
        finish();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu, menu);


        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.menu_main_logout){

           // mUserRef.child("online").setValue(ServerValue.TIMESTAMP);

            FirebaseAuth.getInstance().signOut();
            sendToStart();

        }

        if(item.getItemId() == R.id.menu_main_find_friend){

            Intent settingsIntent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(settingsIntent);

        }

        if(item.getItemId() == R.id.menu_main_profile){

            Intent settingsIntent = new Intent(MainActivity.this, UsersActivity.class);
            startActivity(settingsIntent);

        }
        return true;
    }
}
