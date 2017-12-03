package com.dev.noname.lover.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import com.dev.noname.lover.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GroupChat_Fragment extends Fragment {

    private RecyclerView rv_ListGroup;
    private List<String> listRoom;
    public GroupChat_Fragment() {
        // Required empty public constructor
    }

    public static GroupChat_Fragment newInstance() {
        // Required empty public constructor
        return new GroupChat_Fragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=  inflater.inflate(R.layout.fragment_group_chat_, container, false);
        rv_ListGroup=v.findViewById(R.id.recycleListGroup);

        initEvent();
        return v;
    }

    private void initEvent() {
        listRoom=new ArrayList<>();
        ListGroupAdapter adapter = new ListGroupAdapter();
        rv_ListGroup.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_ListGroup.setAdapter(adapter);
        addItem();
        adapter.notifyDataSetChanged();

    }
    private void addItem(){
        for(int i=0;i<10;i++)
            listRoom.add("Room"+i);
    }
    public class ListGroupAdapter extends RecyclerView.Adapter<ListGroupAdapter.ViewHolder>{

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater=LayoutInflater.from(parent.getContext());
            View v=inflater.inflate(R.layout.item_group_chat,parent,false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.tvRoom.setText(listRoom.get(position));
            holder.tvIcon.setText(position+"");
        }

        @Override

        public int getItemCount() {
            return 10;
        }
        public class ViewHolder extends RecyclerView.ViewHolder{
            TextView tvRoom;
            TextView tvIcon;
            public ViewHolder(View itemView) {
                super(itemView);
                tvRoom=itemView.findViewById(R.id.tv_GroupName);
                tvIcon=itemView.findViewById(R.id.icon_group);
            }
        }
    }

}
