package com.dev.noname.lover.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.noname.lover.R;
import com.dev.noname.lover.utils.Constants;
import com.dev.noname.lover.activity.ProfileActivity;
import com.dev.noname.lover.adapter.Rv_Other_Adapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class OtherFragment extends Fragment {
    private CardView mCardHeader;
    private TextView tvName;
    private CircleImageView imv_avatar;
    private DatabaseReference dbReference;
    private String userId;
    private RecyclerView rv_other;
    private String list[];

    public OtherFragment() {
        // Required empty public constructor
    }
    public static OtherFragment newInstance() {
        OtherFragment fragment = new OtherFragment();

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
        dbReference= FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_other, container, false);
        mCardHeader= mView.findViewById(R.id.card_other_header);
        tvName= mView.findViewById(R.id.tvName);
        imv_avatar= mView.findViewById(R.id.imv_avatar);
        rv_other= mView.findViewById(R.id.recycle_view_other_layout);
        initValue();
        Rv_Other_Adapter adapter=new Rv_Other_Adapter(list,getActivity());
        rv_other.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_other.setAdapter(adapter);
        initEvent();
        return mView;
    }

    private void initValue() {
        list=new String[]{"Group chat","Find nearby friend","Dating","Logout"};
    }

    private void initEvent() {
        mCardHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), ProfileActivity.class);
                i.putExtra(Constants.USER_ID,userId);
                startActivity(i);
            }
        });
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String display_name=dataSnapshot.child(Constants.NAME).getValue().toString();
                String image_link=dataSnapshot.child(Constants.IMAGE).getValue().toString();
                tvName.setText(display_name);
                Picasso.with(getActivity()).load(image_link).placeholder(R.drawable.user).into(imv_avatar);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }


}
