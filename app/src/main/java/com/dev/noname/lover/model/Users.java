package com.dev.noname.lover.model;

/**
 * Created by Admin on 12/1/2017.
 */

public class Users {
    public String name;
    public String image;
    public String status;
    public String thumb_image;
    public String online;
    public long last_online;
    public Users(){

    }



    public Users(String name, String image, String status, String thumb_image, String online, long last_online) {
        this.name = name;
        this.image = image;
        this.status = status;
        this.thumb_image = thumb_image;
        this.online=online;
        this.last_online=last_online;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getThumb_image() {
        return thumb_image;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public long getLast_online() {
        return last_online;
    }

    public void setLast_online(long last_online) {
        this.last_online = last_online;
    }
}
