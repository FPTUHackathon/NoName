package com.dev.noname.lover.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.noname.lover.R;


public class GroupChat_Detail_Fragment extends Fragment {


    public GroupChat_Detail_Fragment() {
        // Required empty public constructor
    }

    public static GroupChat_Detail_Fragment newInstance() {
        GroupChat_Detail_Fragment fragment = new GroupChat_Detail_Fragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_group_chat__detail_, container, false);
    }


}
