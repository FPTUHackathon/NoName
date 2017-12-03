package com.dev.noname.lover.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.noname.lover.R;
import com.dev.noname.lover.activity.ChatActivity;
import com.dev.noname.lover.model.Location;
import com.dev.noname.lover.utils.Constants;
import com.dev.noname.lover.utils.DistanceUtils;
import com.dev.noname.lover.utils.GPSTracker;

import java.util.ArrayList;

/**
 * Created by Admin on 12/3/2017.
 */

public class Rv_Place_Adapter extends RecyclerView.Adapter<Rv_Place_Adapter.ViewHolder> {
    ArrayList<Location> list;
    Activity activity;
    public Rv_Place_Adapter(ArrayList<Location> list, Activity activity) {
        this.list = list;
        this.activity = activity;
    }


    //make interface like this
    public interface OnItemClickListener {
        void onClick(View view, int position, boolean isLongClick);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_user_location, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GPSTracker gpsTracker = new GPSTracker(activity);
        holder.tvUsername.setText(list.get(position).getUsser().getName());
        holder.tvUsrstatus.setText(list.get(position).getUsser().getStatus());
        holder.tvDistance.setText(String.format("%.2f1 km",
                DistanceUtils.distance(list.get(position).getLat(),list.get(position).getLon(),gpsTracker.getLatitude(),gpsTracker.getLongitude())));


        // holder.on
        holder.setOnClick(new OnItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
//                Intent i=new Intent(activity, ChatActivity.class);
//                i.putExtra(Constants.NAME,user.getName());
//                i.putExtra(Constants.USER_ID,listUserID.get(position));
//                activity.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView tvUsername;
        TextView tvUsrstatus;
        TextView tvDistance;
        OnItemClickListener listener;

        public ViewHolder(View itemView) {
            super(itemView);
            tvDistance=itemView.findViewById(R.id.tvDistance);
            tvUsername=itemView.findViewById(R.id.txPlaceUserName);
            tvUsrstatus=itemView.findViewById(R.id.txPlaceUserStatus);
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