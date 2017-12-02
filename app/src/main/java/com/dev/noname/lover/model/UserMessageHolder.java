package com.dev.noname.lover.model;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.noname.lover.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Admin on 12/2/2017.
 */

public class UserMessageHolder extends MyMessageViewHolder {


    private TextView tvMessage;
    private TextView tvTime;
    public CircleImageView imv_avatar;
    private ImageView mess_image;
    private View view;

    public UserMessageHolder(View view) {
        super(view);

        tvMessage = view.findViewById(R.id.message_text_layout);
        imv_avatar = view.findViewById(R.id.message_profile_layout);
        tvTime = view.findViewById(R.id.time_text_layout);
        mess_image=view.findViewById(R.id.message_image);
        this.view=view;
    }

    @Override
    public void setImage(String image_link) {
        Picasso.with(view.getContext()).load(image_link).placeholder(R.drawable.user).into(imv_avatar);

    }

    @Override
    public void bind(Messages item) {
        if (item.getType().equals("text")){
            tvMessage.setText(item.getMessage());
            mess_image.setVisibility(View.GONE);
        }else {
            mess_image.setVisibility(View.VISIBLE);
            tvMessage.setVisibility(View.GONE);
            Picasso.with(view.getContext()).load(item.getMessage()).placeholder(R.drawable.background).into(mess_image);

        }
    }
}
