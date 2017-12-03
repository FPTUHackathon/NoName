package com.dev.noname.lover.model;

/**
 * Created by Windows 10 on 12/3/2017.
 */

public class Location {
    private double lon;
    private double lat;
    private Users user;
    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public Users getUsser() {
        return user;
    }

    public void setUsser(Users usserId) {
        this.user = usserId ;
    }

    @Override
    public String toString() {
        return "Location{" +
                "lon=" + lon +
                ", lat=" + lat +
                ", usserId='" + user.toString() + '\'' +
                '}';
    }
}
