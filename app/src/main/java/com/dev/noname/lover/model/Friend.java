package com.dev.noname.lover.model;

/**
 * Created by Admin on 12/3/2017.
 */

public class Friend extends Users {
    String id;
    int numMessUnRead;

    public Friend() {
    }

    public Friend(String id, int numMessUnRead) {
        this.id = id;
        this.numMessUnRead = numMessUnRead;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumMessUnRead() {
        return numMessUnRead;
    }

    public void setNumMessUnRead(int numMessUnRead) {
        this.numMessUnRead = numMessUnRead;
    }
}
