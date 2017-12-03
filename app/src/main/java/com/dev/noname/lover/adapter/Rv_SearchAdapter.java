package com.dev.noname.lover.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.dev.noname.lover.R;
import com.dev.noname.lover.activity.UsersActivity;
import com.dev.noname.lover.model.Users;
import com.dev.noname.lover.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 12/2/2017.
 */

public class Rv_SearchAdapter  extends RecyclerView.Adapter<Rv_SearchAdapter.ViewHolder>
        implements Filterable{
    private List<Users> values;

    private List<String> listID=new ArrayList<>();
    private List<Users> userFiltered;
    private Activity activity;

    //Constructor
    public Rv_SearchAdapter(List<Users> myDataset, Activity activity) {
        values = myDataset;
        this.activity=activity;
        this.userFiltered=values;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    userFiltered = values;
                } else {
                    List<Users> filteredList = new ArrayList<>();
                    for (Users row : values) {

                        if (row.getName().contains(charSequence)||row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    userFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = userFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                userFiltered = (List<Users>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface OnItemClick {
        void onClick(View view, int position, boolean isLongClick);
    }

    public void setUserID(String id){
        listID.add(id);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        OnItemClick itemClick;

        private TextView textName;
        private TextView textLastText;
        private CircleImageView avatar;
        public View layout;

        private ViewHolder(View v) {
            super(v);
            layout = v;
            textLastText = v.findViewById(R.id.chat_last_mess);
            avatar=  v.findViewById(R.id.chat_user_single_image);
            textName =  v.findViewById(R.id.chat_user_single_name);
            v.setOnClickListener(this);
        }



        public void setOnClick(OnItemClick itemClick){
            this.itemClick=itemClick;
        }

        @Override
        public void onClick(View view) {
            itemClick.onClick(view,getAdapterPosition(),false);
        }
    }

    public void add(int position, Users item) {
        values.add(position, item);
        notifyItemInserted(position);
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
        final String name = userFiltered.get(position).getName();
        holder.textName.setText(name);
        holder.textLastText.setText(userFiltered.get(position).getStatus());
        holder.setOnClick(new OnItemClick() {
            @Override
            public void onClick(View view, int p, boolean isLongClick) {
                Intent i= new Intent(activity, UsersActivity.class);
                i.putExtra(Constants.USER_ID, listID.get(p));
                activity.startActivity(i);
            }
        });
//        if (values.get(position).getImage().equals("default")){
//            holder.avatar.setImageDrawable(holder.layout.getResources().getDrawable(R.drawable.user));
//        } else {
//            Picasso.with(activity).load(values.get(position).getImage()).into(holder.avatar);
//        }
        Picasso.with(activity).load(values.get(position)
                .getImage()).placeholder(R.drawable.user).into(holder.avatar);

    }


    @Override
    public int getItemCount() {
        return userFiltered.size();
    }
}