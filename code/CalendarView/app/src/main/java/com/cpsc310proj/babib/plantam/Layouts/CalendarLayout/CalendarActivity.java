package com.cpsc310proj.babib.plantam.Layouts.CalendarLayout;

import android.content.Context;
import android.content.Intent;
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
import com.cpsc310proj.babib.plantam.Event.CustomDate;
import com.cpsc310proj.babib.plantam.Event.Event;
import com.cpsc310proj.babib.plantam.Firebase.FBDatabase;
import com.cpsc310proj.babib.plantam.Firebase.LoginActivity;
import com.cpsc310proj.babib.plantam.Layouts.AddEventLayout.AddEventActivity;
import com.cpsc310proj.babib.plantam.Layouts.PublicEventsLayout.PublicEventsActivity;
import com.cpsc310proj.babib.plantam.R;
import com.cpsc310proj.babib.plantam.SQLiteDatabase.EventDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MyNameIsYou on 10/21/17.
 *
 * An Activity class that will show the schedule of the user
 * It has a Calendar
 *        List of events on a selected day
 */
public class CalendarActivity extends AppCompatActivity {

    private static final String TAG = "CalendarActivity";


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
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout); //attach layout to the activity

        mFirebaseAuth = FirebaseAuth.getInstance();


        mCalendarView = (CalendarView)findViewById(R.id.calendar_view);
        mEventsListView = (ListView)findViewById(R.id.calendar_events_list_view);

        mAddButton = (FloatingActionButton)findViewById(R.id.calendar_event_add);

        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CalendarActivity.this, AddEventActivity.class);
                startActivity(intent);
            }
        });





        int day = CurrentDate.getDay();
        int month = CurrentDate.getMonth();
        int year = CurrentDate.getYear();


        //mDateView.setText(""+ year + ":" + month + ":" + day);
        mCalendarView.setDate(CurrentDate.getDate());

        EventDatabase eventDatabase =  EventDatabase.getEventDatabase(CalendarActivity.this);
        //new EventDatabase(CalendarActivity.this);

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
                EventDatabase eventDatabase = EventDatabase.getEventDatabase(CalendarActivity.this);
                ArrayList<Event> events = eventDatabase.getEventsAtDate( //when date is changed
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
                // Create and show the dialog.
                EventDetailDialog newFragment =
                        EventDetailDialog.newInstance(mListAdapter.getList().get(position).getEventUID());
                newFragment.show(ft, "EventDetailDialog: " + id);


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

        public CustomListAdapter(Context context, ArrayList<Event> event_list) {
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

}
