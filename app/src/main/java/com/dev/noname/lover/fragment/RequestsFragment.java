package com.dev.noname.lover.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.noname.lover.R;
import com.dev.noname.lover.utils.Constants;
import com.dev.noname.lover.adapter.Rv_Request_Adapter;
import com.dev.noname.lover.model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RequestsFragment extends Fragment {

    private RecyclerView rv_request;
    private TextView tvAlert;

    private FirebaseUser user;
    private DatabaseReference mFriendReqRef;
    private DatabaseReference mUserDataRef;
    private List<String> list_key =new ArrayList<>();
    private List<Users> list_user=new ArrayList<>();

    private Rv_Request_Adapter adapter;
    public RequestsFragment() {
        // Required empty public constructor
    }
    public static RequestsFragment newInstance() {
        RequestsFragment fragment = new RequestsFragment();

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Firebase//
        user= FirebaseAuth.getInstance().getCurrentUser();
        mFriendReqRef= FirebaseDatabase.getInstance().getReference().child(Constants.FRIEND_REQ);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_requests, container, false);
        rv_request=mView.findViewById(R.id.recycle_view_request);
        tvAlert=mView.findViewById(R.id.tvAlertRequest);
        initEvent();

        adapter=new Rv_Request_Adapter(list_user);
        rv_request.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_request.setHasFixedSize(true);
        rv_request.setAdapter(adapter);
        return mView;
    }



    private void initEvent() {


        mFriendReqRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild(user.getUid())){

                    mUserDataRef=FirebaseDatabase.getInstance().getReference().child("Users");
                    for (String key:list_key) {
                        mUserDataRef.child(key).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String name= dataSnapshot.child(Constants.NAME).getValue().toString();
                                String thumb_image=dataSnapshot.child(Constants.THUMB_IMAGE).getValue().toString();
                                Users user=new Users();
                                user.setName(name);
                                user.setThumb_image(thumb_image);
                                list_user.add(user);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                    FirebaseDatabase.getInstance().getReference(user.getUid()).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }else {
                    tvAlert.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
