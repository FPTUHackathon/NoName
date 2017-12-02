package com.dev.noname.lover.model;

/**
 * Created by Admin on 12/2/2017.
 */

public class Group extends Room {
    public String id;
    public ListFriend listFriend;

    public Group(){
        listFriend = new ListFriend();
    }
}
