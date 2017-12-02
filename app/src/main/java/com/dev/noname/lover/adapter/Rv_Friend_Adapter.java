package com.dev.noname.lover.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.noname.lover.R;
import com.dev.noname.lover.activity.ChatActivity;
import com.dev.noname.lover.model.Users;
import com.dev.noname.lover.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 12/2/2017.
 */

public class Rv_Friend_Adapter extends RecyclerView.Adapter<Rv_Friend_Adapter.ViewHolder> {


    private List<Users> values;
    private Activity activity;
    private ArrayList<String> listUserID=new ArrayList<>();


    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView tvName;
        public TextView tvStatus;
        public CircleImageView avatar;
        public View layout;
        public ImageView imv_online;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            tvStatus = v.findViewById(R.id.chat_user_single_status);
            avatar=  v.findViewById(R.id.chat_user_single_image);
            tvName =  v.findViewById(R.id.chat_user_single_name);
            imv_online=v.findViewById(R.id.user_single_online_icon);
        }
    }

    public void add(int position, Users item) {
        values.add(position, item);
        notifyItemInserted(position);
    }


    // Provide a suitable constructor (depends on the kind of dataset)
    public Rv_Friend_Adapter(List<Users> myDataset, Activity activity,ArrayList<String> listUserID) {
        values = myDataset;
        this.activity=activity;
        this.listUserID=listUserID;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_recyclerview_chat_listfriend, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String name = values.get(position).getName();
        final Users user=values.get(position) ;
        holder.tvName.setText(name);
        holder.tvStatus.setText(user.getStatus());
        if (user.getImage().equals("default")){
            holder.avatar.setImageDrawable(holder.layout.getResources().getDrawable(R.drawable.user));
        } else {
            Picasso.with(holder.layout.getContext()).load(values.get(position).getImage()).into(holder.avatar);
        }
        if (!user.getOnline().equals("true")){
            holder.imv_online.setVisibility(View.INVISIBLE);

        }else{
            holder.imv_online.setVisibility(View.VISIBLE);
        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(activity, ChatActivity.class);
                i.putExtra(Constants.NAME,user.getName());
                i.putExtra(Constants.USER_ID,listUserID.get(position));
                activity.startActivity(i);
            }
        });
    }




    @Override
    public int getItemCount() {
        return values.size();
    }
}
