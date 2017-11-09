package com.cpsc310proj.babib.plantam.Layouts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cpsc310proj.babib.plantam.Event;
import com.cpsc310proj.babib.plantam.DailyEventList;
import com.cpsc310proj.babib.plantam.Firebase.FirebaseUserAuthentication;
import com.cpsc310proj.babib.plantam.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

/**
 * Created by MyNameIsYou on 10/21/17.
 */

public class CalendarActivity extends AppCompatActivity {

    private static final String TAG = "CalendarActivity";
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private TextView mDateView;
    private CalendarView mCalendarView;
    private ListView mEventsListView;
    private FloatingActionButton mAddButton;
    private CustomListAdapter mListAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout);

        mFirebaseAuth = FirebaseAuth.getInstance();

        mDateView = (TextView)findViewById(R.id.calendar_date_text_view);
        mCalendarView = (CalendarView)findViewById(R.id.calendar_view);
        mEventsListView = (ListView)findViewById(R.id.calendar_events_list_view);
        mAddButton = (FloatingActionButton)findViewById(R.id.calendar_event_add);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Event c = new Event("test title", "test d", "date", 12, 12);
                DailyEventList.addEventToList(c);
                Log.d("added:", "" + DailyEventList.getEventListInstance().size());
                mListAdapter.notifyDataSetChanged();
            }
        });
        mListAdapter = new CustomListAdapter(this, DailyEventList.getEventListInstance());

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                mDateView.setText("" + year + ":" + month + ":" + dayOfMonth);
                Log.d("testing", "" + year + ":" + month + ":" + dayOfMonth);
            }
        });





        mEventsListView.setAdapter(mListAdapter);


        /*
        CalendarViewFragment upperFragment = new CalendarViewFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .replace(R.id.upper_part_monthly_view, upperFragment, upperFragment.getTag())
                .commit();

        CalendarViewDetailFragment lowerFragment = new CalendarViewDetailFragment();
        FragmentManager manager1 = getSupportFragmentManager();
        manager1.beginTransaction()
                .replace(R.id.lower_part_monthly_view, lowerFragment, lowerFragment.getTag())
                .commit();*/

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    // user is signed out
                    finish();
                    Intent intent = new Intent(CalendarActivity.this, FirebaseUserAuthentication.class);
                    startActivity(intent);
                }else{
                    // user is signed int
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_sign_out:
                Toast.makeText(this, "Signing out...", Toast.LENGTH_SHORT)
                        .show();
                mFirebaseAuth.getInstance().signOut();
//                Toast.makeText(this, mAuth.getCurrentUser() + " is signed in??", Toast.LENGTH_SHORT)
//                        .show();
                finish();
                Intent intent = new Intent(CalendarActivity.this, FirebaseUserAuthentication.class);
                startActivity(intent);
                break;
            case R.id.action_explore:
                Intent intent1 = new Intent(CalendarActivity.this, PublicEventsActivity.class);
                startActivity(intent1);
                break;
        }

        return true;
    }

    private class CustomListAdapter extends BaseAdapter{
        ArrayList<Event> toDisplay;
        private LayoutInflater inflater;

        public CustomListAdapter(Context context, ArrayList<Event> event_list) {
            super();
            Log.d("list: ", "got here");
            toDisplay = event_list;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return toDisplay.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        /********* Create a holder Class to contain inflated xml file elements *********/
        private class ViewHolder{

            public TextView time;
            public TextView title;
            public TextView description;

        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View list_view;
            ViewHolder holder = new ViewHolder();

            if (convertView == null) {

                convertView = inflater.inflate(R.layout.calendar_detail_list_view, parent, false);

            }

            holder.title = (TextView)convertView.findViewById(R.id.calendar_detail_list_view_title);
            holder.time = (TextView)convertView.findViewById(R.id.calendar_detail_list_view_time);
            holder.description = (TextView)convertView.findViewById(R.id.calendar_detail_list_view_description);

            if(!toDisplay.isEmpty()){
                holder.title.setText(toDisplay.get(position).getTitle());
                holder.time.setText(toDisplay.get(position).getDate().toString());
                holder.description.setText(toDisplay.get(position).getDescription());
            }

            return convertView;
        }
    }

}
