package com.dev.noname.lover.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.noname.lover.R;
import com.dev.noname.lover.adapter.Rv_Friend_Adapter;
import com.dev.noname.lover.model.Users;
import com.dev.noname.lover.utils.Constants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatsFragment extends Fragment {
    RecyclerView recyclerView;
    FirebaseAuth mAuth;
    public View layout;
    public String UID;
    public ArrayList<Users> userList;
    public ArrayList<String> friendList;
    public Rv_Friend_Adapter mAdapter;
    private DatabaseReference mUsersDatabase;
    private DatabaseReference mFriendsDatabase;
    public static ChatsFragment newInstance() {
        ChatsFragment fragment = new ChatsFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_chats, container, false);
        mAuth= FirebaseAuth.getInstance();
        FirebaseUser current=mAuth.getCurrentUser();
        if (current!=null){
            UID=current.getUid();
            mFriendsDatabase = FirebaseDatabase.getInstance().getReference().child("Friends").child(UID);
            
            mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

            userList = new ArrayList<>();
            friendList = new ArrayList<>();
            mFriendsDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    friendList.clear();
                    for (DataSnapshot dnsp:dataSnapshot.getChildren())
                        friendList.add(dnsp.getKey());

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            mUsersDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    userList.clear();
                    for (String ID : friendList){
                        Map<String,Object> map =(Map) dataSnapshot.child(ID).getValue();
                        Users users = new Users();
                        users.setName(String.valueOf(map.get(Constants.NAME)));
                        users.setStatus(String.valueOf(map.get(Constants.STATUS)));
                        users.setImage(String.valueOf(map.get(Constants.IMAGE)));
                        if (dataSnapshot.child(ID).hasChild(Constants.ONLINE))
                            users.setOnline(String.valueOf(map.get(Constants.ONLINE)));
                        else
                            users.setOnline("false");
                        userList.add(users);
                        mAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            recyclerView =  mView.findViewById(R.id.recycle_view_chat);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setHasFixedSize(true);

            mAdapter = new Rv_Friend_Adapter(userList,getActivity(),friendList);
            recyclerView.setAdapter(mAdapter);
        }



        return mView;
    }


}
