package com.cpsc310proj.babib.plantam.Layouts.CalendarLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.cpsc310proj.babib.plantam.Enums.Location;
import com.cpsc310proj.babib.plantam.R;

public class ViewLocation extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_location);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    public void openLocation(View view) {
        Intent intent = null, chooser = null;
        String geo_coordinates = null;
        String location_name = null;

        switch(view.getId()) {
            case R.id.button_mather:
                location_name = Location.MATHER.toString();
                geo_coordinates = Location.MATHER.getGeo();
                break;
            case R.id.button_mecc:
                location_name = Location.MECC.toString();
                geo_coordinates = Location.MECC.getGeo();
                break;
            case R.id.button_mccook:
                location_name = Location.MCCOOK.toString();
                geo_coordinates = Location.MCCOOK.getGeo();
                break;
            case R.id.button_cinestudio:
                location_name = Location.CINESTUDIO.toString();
                geo_coordinates = Location.CINESTUDIO.getGeo();
                break;
            case R.id.button_lsc:
                location_name = Location.LSC.toString();
                geo_coordinates = Location.LSC.getGeo();
                break;
            case R.id.button_austin:
                location_name = Location.AUSTIN.toString();
                geo_coordinates = Location.AUSTIN.getGeo();
                break;
            case R.id.button_library:
                location_name = Location.LIBRARY.toString();
                geo_coordinates = Location.LIBRARY.getGeo();
                break;
            case R.id.button_vernon:
                location_name = Location.VERNON.toString();
                geo_coordinates = Location.VERNON.getGeo();
                break;
            case R.id.button_ferris:
                location_name = Location.FERRIS.toString();
                geo_coordinates = Location.FERRIS.getGeo();
                break;
            case R.id.button_koeppel:
                location_name = Location.KOEPPEL.toString();
                geo_coordinates = Location.KOEPPEL.getGeo();
                break;
        }
        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + geo_coordinates + "?q=" + geo_coordinates + "(" + location_name + ")"));
        chooser = Intent.createChooser(intent, "Launch Map");
        startActivity(chooser);
    }
}
