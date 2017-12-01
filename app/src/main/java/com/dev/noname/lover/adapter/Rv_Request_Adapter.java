package com.dev.noname.lover.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dev.noname.lover.R;
import com.dev.noname.lover.model.Users;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 12/1/2017.
 */

public class Rv_Request_Adapter extends RecyclerView.Adapter<Rv_Request_Adapter.ViewHolder> {

    private List<Users> users;

    public Rv_Request_Adapter (List<Users> users){
        this.users=users;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.row_recycleview_request,parent,false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvName.setText(users.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView imv_thumb;
        private TextView tvName;
        private Button btnAccept;
        private Button btnDecline;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName=itemView.findViewById(R.id.tvName);

        }
    }
}
