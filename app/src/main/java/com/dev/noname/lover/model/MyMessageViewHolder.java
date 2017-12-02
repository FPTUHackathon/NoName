package com.dev.noname.lover.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Admin on 12/2/2017.
 */

public abstract class MyMessageViewHolder extends RecyclerView.ViewHolder {

    public MyMessageViewHolder(View itemView) {
        super(itemView);
    }
    public abstract void bind(Messages messages);
    public abstract void setImage(String image_link);
}
