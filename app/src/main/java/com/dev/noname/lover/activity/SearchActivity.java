package com.dev.noname.lover.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


import com.dev.noname.lover.R;
import com.dev.noname.lover.adapter.Rv_SearchAdapter;
import com.dev.noname.lover.model.Users;
import com.dev.noname.lover.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class SearchActivity extends AppCompatActivity {
    private DatabaseReference mUsersDatabase;
    public Rv_SearchAdapter adapter;
    //public int pos;
    List<Users> list;
    private RecyclerView mUsersList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);

        list=new ArrayList<>() ;

        adapter=new Rv_SearchAdapter(list,this);

        mUsersList = findViewById(R.id.recycle_view_search_user);
        mUsersList.setHasFixedSize(true);
        mUsersList.setLayoutManager(mLayoutManager);

        initEvent();

        mUsersList.setAdapter(adapter);
    }


    private void initEvent(){
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Users");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot dsp: dataSnapshot.getChildren()){
                    Map<String,Object> map =(Map)dsp.getValue();
                    Users users = new Users();
                    users.setName(String.valueOf(map.get(Constants.NAME)));
                    users.setImage(String.valueOf(map.get(Constants.IMAGE)));
                    users.setStatus(String.valueOf(map.get(Constants.STATUS)));
                    list.add(users);
                    adapter.setUserID(dsp.getKey());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    }