package com.dev.noname.lover.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dev.noname.lover.R;
import com.dev.noname.lover.model.Users;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 12/2/2017.
 */

public class UserChatAdapter extends RecyclerView.Adapter<UserChatAdapter.ViewHolder> {
    private List<Users> values;


    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView textName;
        public TextView textLastText;
        public CircleImageView avatar;
        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            textLastText = v.findViewById(R.id.chat_user_single_status);
            avatar=  v.findViewById(R.id.chat_user_single_image);
            textName =  v.findViewById(R.id.chat_user_single_name);
        }
    }

    public void add(int position, Users item) {
        values.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        values.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public UserChatAdapter(List<Users> myDataset) {
        values = myDataset;
    }

    @Override
    public UserChatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.row_recyclerview_chat_listfriend, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final String name = values.get(position).getName();
        holder.textName.setText(name);
        holder.textLastText.setText(values.get(position).getStatus());
        if (values.get(position).getImage().equals("default")){
            holder.avatar.setImageDrawable(holder.layout.getResources().getDrawable(R.drawable.user));
        } else {
            Picasso.with(holder.layout.getContext()).load(values.get(position).getImage()).into(holder.avatar);
        }


    }


    @Override
    public int getItemCount() {
        return values.size();
    }
}
