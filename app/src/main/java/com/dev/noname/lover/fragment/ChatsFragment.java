package com.dev.noname.lover.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.noname.lover.R;
import com.dev.noname.lover.adapter.Rv_Chat_Adapter;
import com.dev.noname.lover.adapter.Rv_Friend_Adapter;
import com.dev.noname.lover.model.Friend;
import com.dev.noname.lover.model.Messages;
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
    public ArrayList<Friend> friendList;

    public Rv_Chat_Adapter mAdapter;
    private DatabaseReference mFriendMessDatabase;
    private DatabaseReference mUsersDatabase;

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

            mFriendMessDatabase=FirebaseDatabase.getInstance().getReference().child("messages").child(current.getUid());

            friendList = new ArrayList<>();
            mFriendMessDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    friendList.clear();
                    for (DataSnapshot dnsp:dataSnapshot.getChildren())
                    {   int num_unread=0;

                        for (DataSnapshot dn : dnsp.getChildren()){
                            Messages messages =dn.getValue(Messages.class);
                            if (messages.isSeen())
                                num_unread++;

                        }
                        Log.e("Unread",num_unread+"");
                        friendList.add(new Friend(dnsp.getKey(),num_unread));
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            mUsersDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

            mUsersDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //friendList.clear();
                    for (int i=0;i<friendList.size();i++){
                        String ID = friendList.get(i).getId();
                        Map<String,Object> map =(Map) dataSnapshot.child(ID).getValue();

                        Friend f=friendList.get(i);
                        f.setName(String.valueOf(map.get(Constants.NAME)));
                        f.setStatus(String.valueOf(map.get(Constants.STATUS)));
                        f.setImage(String.valueOf(map.get(Constants.THUMB_IMAGE)));
                        if (dataSnapshot.child(ID).hasChild(Constants.ONLINE))
                            f.setOnline(String.valueOf(map.get(Constants.ONLINE)));
                        else
                            f.setOnline("false");
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

            mAdapter = new Rv_Chat_Adapter(getActivity(),friendList);
            recyclerView.setAdapter(mAdapter);
        }



        return mView;
    }


}
