package com.cpsc310proj.babib.plantam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
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
            this.latitude = "41.744430994";
            this.longitude = "-72.6911037";
        } else if (location == LocationName.MATHER) {
            this.latitude = "41.746631";
            this.longitude = "-72.692567";
        } else if (location == LocationName.MCCOOK) {
            this.latitude = "41.745779";
            this.longitude = "-72.691427";
        } else if (location == LocationName.CINESTUDIO) {
            this.latitude = "41.746590";
            this.longitude = "-72.690722";
        }
        else {
            this.latitude = "0";
            this.longitude = "0";
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
        if (!this.latitude.equals("0") && !this.longitude.equals("0")) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query=" + latitude + ',' + longitude));
            //startActivity(browserIntent);
            //Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("google.navigation:q=an+address+city"));

        }
    }
}
