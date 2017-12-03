package com.dev.noname.lover.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;;

import com.dev.noname.lover.R;
import com.dev.noname.lover.fragment.GroupChat_Fragment;


public class GroupChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);
        Toolbar toolbar=findViewById(R.id.toolbar_groupchat);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Group Chat");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Fragment fragment=GroupChat_Fragment.newInstance();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container_group_chat, fragment);
        ft.commit();
    }


}
