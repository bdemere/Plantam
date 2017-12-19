package com.cpsc310proj.babib.plantam.Layouts.CalendarLayout;

import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cpsc310proj.babib.plantam.CurrentDate;
import com.cpsc310proj.babib.plantam.DataObserver;
import com.cpsc310proj.babib.plantam.Enums.Accessibility;
import com.cpsc310proj.babib.plantam.Event.CustomDate;
import com.cpsc310proj.babib.plantam.Event.Event;
import com.cpsc310proj.babib.plantam.EventDatabase;
import com.cpsc310proj.babib.plantam.Firebase.FBDatabase;
import com.cpsc310proj.babib.plantam.Firebase.LoginActivity;
import com.cpsc310proj.babib.plantam.Layouts.AddEventLayout.AddEventActivity;
import com.cpsc310proj.babib.plantam.Layouts.PublicEventsLayout.PublicEventsActivity;
import com.cpsc310proj.babib.plantam.R;
import com.cpsc310proj.babib.plantam.SQLiteDatabase.SQLiteEventDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by MyNameIsYou on 10/21/17.
 *
 * An Activity class that will show the schedule of the user
 * It has a Calendar
 *        List of events on a selected day
 */
public class CalendarActivity extends AppCompatActivity implements DataObserver {

    private static final String TAG = "CalendarActivity";

    private static int EVENT_REQUEST = 1;


    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private CalendarView mCalendarView; //Calendar: show the calendar
    private ListView mEventsListView; //List: show list of events selected in a date selected
    private FloatingActionButton mAddButton;//Floating Button: button to open a form to add events

    /**
     * A custom Adapter for the list class
     * It enables to define a new design for the items in the list
     **/
    private CustomListAdapter mListAdapter;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == EVENT_REQUEST) {
            //When a new Event object is returned from the activity that adds classes,
            //this is executed
            if(resultCode == AppCompatActivity.RESULT_OK){
                //Get the Event object returned
                Event result = (Event)data.getSerializableExtra(AddEventActivity.EVENT_RESULT);
                EventDatabase SQLiteEventDatabase = new SQLiteEventDatabase(CalendarActivity.this);
                Log.d("Adding: ", result.toString());

                //Set the accessibility of the object to private
                result.setAccessibility(Accessibility.PRIVATE.toString());
                SQLiteEventDatabase.addEvent(result);
            }
            if (resultCode == AppCompatActivity.RESULT_CANCELED) {
                //if there is no result
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: this is for testing purpose, modify!!!
        FirebaseMessaging.getInstance().subscribeToTopic("pushNotifications");


        setContentView(R.layout.calendar_layout); //attach layout to the activity

        mFirebaseAuth = FirebaseAuth.getInstance();


        /**
         * Observe data changes from the local database
         */
        SQLiteEventDatabase.addObserver(this);

        mCalendarView = (CalendarView)findViewById(R.id.calendar_view);
        mCalendarView.setMinDate(java.util.Calendar.getInstance().getTime().getTime());

        mEventsListView = (ListView)findViewById(R.id.calendar_events_list_view);

        mAddButton = (FloatingActionButton)findViewById(R.id.calendar_event_add);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, AddEventActivity.class);
                //intent.putExtra("key", this.getClass());
                startActivityForResult(intent, EVENT_REQUEST);
            }
        });





        int day = CurrentDate.getDay();
        int month = CurrentDate.getMonth();
        int year = CurrentDate.getYear();


        //mDateView.setText(""+ year + ":" + month + ":" + day);
        mCalendarView.setDate(CurrentDate.getDate());

        final SQLiteEventDatabase eventDatabase =  SQLiteEventDatabase.getEventDatabase(CalendarActivity.this);
        //new SQLiteEventDatabase(CalendarActivity.this);

        mListAdapter = new CustomListAdapter(
                this,
                eventDatabase.getEventsAtDate( //when date is changed
                    (new CustomDate()).setYear(year).setMonth(month).setDay(day)
                )
        );

        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //mDateView.setText("" + year + ":" + month + ":" + dayOfMonth);
                //Log.d("testing", "" + year + ":" + month + ":" + dayOfMonth);
                SQLiteEventDatabase eventDatabase = SQLiteEventDatabase.getEventDatabase(CalendarActivity.this);
                List<Event> events = eventDatabase.getEventsAtDate( //when date is changed
                        (new CustomDate()).setYear(year).setMonth(month).setDay(dayOfMonth)
                );


                mListAdapter.setList(events);
                mListAdapter.notifyDataSetChanged();

            }
        });


        mEventsListView.setAdapter(mListAdapter);

        //TODO: open dialog when an event clicked
        mEventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                // Create and show the dia
                EditEventDialog newFragment =
                        EditEventDialog.newInstance(mListAdapter.getList().get(position), eventDatabase);
                newFragment.show(ft, "EditEventDialog: " + id);


                Log.d("CalendarActivity: ", position + " clicked");
                Toast.makeText(getBaseContext(), "TODO: show dialog for "
                        + mListAdapter.getList().get(position).getTitle(), Toast.LENGTH_LONG);
            }
        });


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    // user is signed out
                    finish();
                    Intent intent = new Intent(CalendarActivity.this, LoginActivity.class);
                    startActivity(intent);
                }else{
                    // user is signed int
                }
            }
        };
    }



    @Override
    public void eventDataChanged(){
        SQLiteEventDatabase eventDatabase = SQLiteEventDatabase.getEventDatabase(CalendarActivity.this);
        int year = CurrentDate.getYear();
        int month = CurrentDate.getMonth();
        int dayOfMonth = CurrentDate.getMonth();

        mListAdapter = new CustomListAdapter(
                this,
                eventDatabase.getEventsAtDate( //when date is changed
                        (new CustomDate()).setYear(year).setMonth(month).setDay(dayOfMonth)
                )
        );

        mEventsListView.setAdapter(mListAdapter);
        mListAdapter.notifyDataSetChanged();
        Log.d("Local: eventDa..ed(): ", mListAdapter.getList().toString());


    }

    @Override
    protected void onStop() {
        super.onStop();
        SQLiteEventDatabase.removeObserver(this);
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


                finish();
                Intent intent = new Intent(CalendarActivity.this, LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.action_explore:
                Intent intent1 = new Intent(CalendarActivity.this, PublicEventsActivity.class);
                startActivity(intent1);
                break;

            case R.id.action_settings:
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                Log.d("USERID: ",
                        "" + FBDatabase.writeUser(FirebaseAuth.getInstance().getCurrentUser().getUid()));

                break;
        }

        return true;
    }

    private class CustomListAdapter extends BaseAdapter{
        private List<Event> toDisplay;
        private LayoutInflater inflater;

        public CustomListAdapter(Context context, List<Event> event_list) {
            super();
            Log.d("list: ", "got here");
            toDisplay = event_list;
            inflater = LayoutInflater.from(context);
        }



        public void setList(List<Event> list){
            toDisplay = list;
        }
        public List<Event> getList(){
            return toDisplay;
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
            ViewHolder holder = new ViewHolder();

            if (convertView == null) {

                convertView = inflater.inflate(R.layout.calendar_detail_list_view, parent, false);

            }

            holder.title = (TextView)convertView.findViewById(R.id.calendar_detail_list_view_title);
            holder.time = (TextView)convertView.findViewById(R.id.calendar_detail_list_view_time);
            //holder.description = (TextView)convertView.findViewById(R.id.calendar_detail_list_view_description);

            if(!toDisplay.isEmpty()){
                holder.title.setText(toDisplay.get(position).getTitle());
                holder.time.setText(toDisplay.get(position).getStartTime().toString());
                //holder.description.setText(toDisplay.get(position).getDescription());
            }


            return convertView;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mCalendarView.setMinDate(java.util.Calendar.getInstance().getTime().getTime());
    }
}
