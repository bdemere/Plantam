package com.cpsc310proj.babib.plantam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by anhthuynguyen on 11/8/17.
 */

public class Location {
    String latitude;
    String longitude;

    public Location(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location(LocationName location) {
        if (location == LocationName.MECC) {
            this.latitude = "1";
            this.longitude = "2";
        }
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void gotoMap() {
        //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=Current%20Location&daddr=52.5651667, -8.7895846"));
        //startActivity(browserIntent);
        //Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("google.navigation:q=an+address+city"));
    }
}
