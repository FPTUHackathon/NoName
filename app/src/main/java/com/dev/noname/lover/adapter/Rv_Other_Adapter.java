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
import com.dev.noname.lover.activity.GroupChat;
import com.dev.noname.lover.activity.StartActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.Map;

/**
 * Created by Admin on 12/1/2017.
 */

public class Rv_Other_Adapter extends RecyclerView.Adapter<Rv_Other_Adapter.ViewHolder> {

    String list[];
    Activity activity;
    int []listImage=new int[]{R.drawable.group_chat
            ,R.drawable.near_by,R.drawable.dating,R.drawable.logout};

    //make interface like this
    public interface OnItemClickListener {
        void onClick(View view, int position, boolean isLongClick);
    }

    public Rv_Other_Adapter(String [] list,Activity activity){
        this.list=list;
        this.activity=activity;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_recyclerview_other, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvTitle.setText(list[position]);
        holder.tvIcon.setImageResource(listImage[position]);
       // holder.on
        holder.setOnClick(new OnItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                switch (position){
                    case 0:{
                        Intent i =new Intent(activity, GroupChat.class);
                        activity.startActivity(i);
                      break;
                    }
                    case 3:{
                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                        DatabaseReference UReference =FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                        UReference.child("online").setValue("false");
                        UReference.child("last_temp").setValue(ServerValue.TIMESTAMP);
                        FirebaseAuth.getInstance().signOut();
                        Intent i= new Intent(activity, StartActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        activity.startActivity(i);
                        break;
                    }
                    case 1:{

                        break;
                    }
                    case 2:{
                        break;
                    }
                    default:break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvTitle;
        ImageView tvIcon;
        OnItemClickListener listener;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle=itemView.findViewById(R.id.tvRowTitle);
            tvIcon=itemView.findViewById(R.id.imv_icon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view,getPosition(),false);
        }
        public void setOnClick(OnItemClickListener listener){
            this.listener=listener;
        }
    }

}
