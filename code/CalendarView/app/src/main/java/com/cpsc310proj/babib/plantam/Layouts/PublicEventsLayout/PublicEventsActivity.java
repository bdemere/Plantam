package com.cpsc310proj.babib.plantam.Layouts.PublicEventsLayout;

import android.app.ProgressDialog;
import android.content.res.Resources;
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

import com.cpsc310proj.babib.plantam.Enums.Category;
import com.cpsc310proj.babib.plantam.Firebase.FBDatabase;
import com.cpsc310proj.babib.plantam.Firebase.FireBaseDataObserver;
import com.cpsc310proj.babib.plantam.R;

public class PublicEventsActivity extends AppCompatActivity
        implements PublicEventCategoryFragment.OnListFragmentInteractionListener, FireBaseDataObserver {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private ProgressDialog mProgressDialog;


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

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));




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

            FBDatabase.update();
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
