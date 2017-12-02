package com.dev.noname.lover.fragment;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.noname.lover.R;
import com.dev.noname.lover.model.ChatUserMessageHolder;
import com.dev.noname.lover.model.Messages;
import com.dev.noname.lover.model.MyMessageViewHolder;
import com.dev.noname.lover.model.UserMessageHolder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


/**
 * Created by Admin on 12/2/2017.
 */

public class MessageAdapter extends RecyclerView.Adapter<MyMessageViewHolder> {


    private List<Messages> mMessageList;
    private DatabaseReference mUserDatabase;

    public MessageAdapter(List<Messages> mMessageList) {

        this.mMessageList = mMessageList;
    }

    @Override
    public MyMessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(viewType, parent, false);

        MyMessageViewHolder holder = null;
        switch (viewType) {
            case R.layout.message_single_layout:
                holder = new UserMessageHolder(view);
                break;
            case R.layout.chat_user_message:
                holder = new ChatUserMessageHolder(view);
                break;

        }
        return holder;

    }

    @Override
    public int getItemViewType(int position) {
       String user= mMessageList.get(position).getFrom();
      // String type=mMessageList.get(position).getType();
        if (user.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
            return R.layout.chat_user_message;
        }else
            return R.layout.message_single_layout           ;
    }

    @Override
    public void onBindViewHolder(final MyMessageViewHolder viewHolder, int i) {

        Messages c = mMessageList.get(i);

        String from_user = c.getFrom();

        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users").child(from_user);

        mUserDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String image = dataSnapshot.child("thumb_image").getValue().toString();
                viewHolder.setImage(image);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        viewHolder.bind(mMessageList.get(i));


    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

}