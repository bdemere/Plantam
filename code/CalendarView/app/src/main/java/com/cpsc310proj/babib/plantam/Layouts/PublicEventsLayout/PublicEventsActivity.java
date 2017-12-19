package com.cpsc310proj.babib.plantam.Layouts.PublicEventsLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.cpsc310proj.babib.plantam.Enums.Accessibility;
import com.cpsc310proj.babib.plantam.Enums.Category;
import com.cpsc310proj.babib.plantam.Event.Event;
import com.cpsc310proj.babib.plantam.EventDatabase;
import com.cpsc310proj.babib.plantam.Firebase.FBDatabase;
import com.cpsc310proj.babib.plantam.DataObserver;
import com.cpsc310proj.babib.plantam.Layouts.AddEventLayout.AddEventActivity;
import com.cpsc310proj.babib.plantam.R;
import com.cpsc310proj.babib.plantam.SQLiteDatabase.SQLiteEventDatabase;
/**
 * @author GROUP 4
 * @version 1.0
 */
 //TODO


public class PublicEventsActivity extends AppCompatActivity
        implements PublicEventCategoryFragment.OnListFragmentInteractionListener, DataObserver {

    private static int EVENT_REQUEST = 1;


    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private FloatingActionButton mAddButton;

    private ViewPager mViewPager;
    private ProgressDialog mProgressDialog;


    /**
     * This method gets run when this Activity starts another activity waiting for a result from
     * the started Activity;
     * @param requestCode The requested result code
     * @param resultCode  about the result
     * @param data the result
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //When a new Event object is returned from the activity that adds classes,
        //this is executed
        if (requestCode == EVENT_REQUEST) {
            if(resultCode == AppCompatActivity.RESULT_OK){
                Event result = (Event)data.getSerializableExtra(AddEventActivity.EVENT_RESULT);

                Log.d("Adding FireBase: ", result.toString());

                //Adding to public database
                result.setAccessibility(Accessibility.PUBLIC.toString());
                EventDatabase eventDatabase = new FBDatabase();
                eventDatabase.addEvent(result);



                //Adding to private database
                eventDatabase = new SQLiteEventDatabase(PublicEventsActivity.this);
                result.setAccessibility(Accessibility.USERPUBLIC.toString());
                eventDatabase.addEvent(result);
                FBDatabase.updateEventsData();
            }
            if (resultCode == AppCompatActivity.RESULT_CANCELED) {
                //if there is no result
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_events);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mProgressDialog = new ProgressDialog(this);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.public_event_tabs);

        //add categories of events
        for (Category c : Category.values())
            tabLayout.addTab(tabLayout.newTab().setText(c.toString()));



        FBDatabase.addObserver(this);
        FBDatabase.makeEventsDataLocal();
        mProgressDialog.setMessage("Please wait ...");
        mProgressDialog.show();

        if(!FBDatabase.isEventDataUpdated())
            mProgressDialog.cancel();

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        mAddButton = (FloatingActionButton)findViewById(R.id.add_public_event_fab);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PublicEventsActivity.this, AddEventActivity.class);
                startActivityForResult(intent, EVENT_REQUEST);
            }
        });



    }

    public void eventDataChanged(){
        Log.d("PublicEventsActivity: ", "eventDataChanged()");
        if(mProgressDialog.isShowing()) mProgressDialog.cancel();

    }


    @Override
    protected void onPause() {
        super.onPause();
        FBDatabase.removeObserver(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        FBDatabase.addObserver(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_public_events, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if(id == R.id.public_action_update){
            mProgressDialog.setMessage("Please wait...");
            mProgressDialog.show();

            FBDatabase.updateEventsData();
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //Log.d("Change:: ", Category.values()[position].toString()+"");
            return PublicEventCategoryFragment.newInstance(Category.values()[position]);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return Category.values().length;
        }
    }

    @Override
    public void onListFragmentInteraction(String item) {
        //we can leave it empty
    }
}
