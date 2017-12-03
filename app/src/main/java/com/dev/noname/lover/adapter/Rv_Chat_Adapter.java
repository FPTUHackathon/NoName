package com.dev.noname.lover.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.noname.lover.R;
import com.dev.noname.lover.activity.ChatActivity;
import com.dev.noname.lover.model.Friend;
import com.dev.noname.lover.model.Users;
import com.dev.noname.lover.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 12/3/2017.
 */

public class Rv_Chat_Adapter extends RecyclerView.Adapter<Rv_Chat_Adapter.ViewHolder> {


    private Activity activity;
    private ArrayList<Friend> listUser=new ArrayList<>();


    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvName;
        public TextView tvLastMes;
        public CircleImageView avatar;
        public View layout;
        public ImageView imv_online;
        public TextView tvUnRead;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            tvLastMes = v.findViewById(R.id.chat_last_mess);
            avatar=  v.findViewById(R.id.chat_user_single_image);
            tvName =  v.findViewById(R.id.chat_user_single_name);
            imv_online=v.findViewById(R.id.user_single_online_icon);
            tvUnRead=v.findViewById(R.id.tv_unread_mess);
        }
    }

//     public void add(int position, Users item) {
//        listUser.add(position, item);
//        notifyItemInserted(position);
//    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public Rv_Chat_Adapter( Activity activity,ArrayList<Friend> listUser) {
        this.activity=activity;
        this.listUser=listUser;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_recyclerview_chat_listfriend, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final Rv_Chat_Adapter.ViewHolder holder, int position) {

        final Users user=listUser.get(position);
        if (user!=null){
            holder.tvName.setText(user.getName());
            if (user.getImage().equals("default")){
                holder.avatar.setImageResource(R.drawable.user);
            } else {
                Picasso.with(holder.layout.getContext()).load(user.getImage()).into(holder.avatar);
            }
            if (!user.getOnline().equals("true")){
                holder.imv_online.setVisibility(View.INVISIBLE);

            }else{
                holder.imv_online.setVisibility(View.VISIBLE);
            }
            if (listUser.get(position).getNumMessUnRead()>0){
                holder.tvLastMes.setText("You got new message!");
                holder.tvUnRead.setVisibility(View.VISIBLE);
            }else {
                holder.tvUnRead.setVisibility(View.INVISIBLE);
            }
            holder.tvUnRead.setText(String.valueOf(listUser.get(holder.getAdapterPosition()).getNumMessUnRead()));

            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i=new Intent(activity, ChatActivity.class);
                    i.putExtra(Constants.NAME,user.getName());
                    i.putExtra(Constants.USER_ID,listUser.get(holder.getAdapterPosition()).getId());
                    activity.startActivity(i);
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return listUser.size();
    }
}