package com.cpsc310proj.babib.plantam.Layouts.PublicEventsLayout;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cpsc310proj.babib.plantam.Enums.Category;
import com.cpsc310proj.babib.plantam.Event.Event;
import com.cpsc310proj.babib.plantam.Firebase.FBDatabase;
import com.cpsc310proj.babib.plantam.DataObserver;
import com.cpsc310proj.babib.plantam.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author GROUP 4
 * @version 1.0
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class PublicEventCategoryFragment extends Fragment implements DataObserver {

    // TODO: Customize parameter argument names
    private static final String ARG_CATEGORY_NAME = "com.cpsc310proj.babib.plantam.Layouts." +
            "ARG_CATEGORY_NAME";

    // TODO: Customize parameters
    private Category mCategory;
    //private RecyclerView mRecyclerView;
    private OnListFragmentInteractionListener mListener;

    private ListView mEventsListView;
    private CustomListAdapter mListAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PublicEventCategoryFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static PublicEventCategoryFragment newInstance(Category category) {
        PublicEventCategoryFragment fragment = new PublicEventCategoryFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CATEGORY_NAME, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
            mCategory = (Category)getArguments().getSerializable(ARG_CATEGORY_NAME);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        mEventsListView = (ListView)view.findViewById(R.id.public_events_list_view);

        mListAdapter = new CustomListAdapter(
                getActivity(),
                FBDatabase.getPublicEventsWithCategory(mCategory)
        );

        mEventsListView.setAdapter(mListAdapter);

        mEventsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                // Create and show the dialog.
                ViewEventDialog newFragment =
                        ViewEventDialog.newInstance(mListAdapter.getList().get(position), new FBDatabase());
                newFragment.show(ft, "EditEventDialog: " + id);



                Log.d("CalendarActivity: ", position + " clicked");
                Toast.makeText(getActivity(), "TODO: show dialog for "
                        + mListAdapter.getList().get(position).getTitle(), Toast.LENGTH_LONG);
            }
        });



        return view;
    }


    public void eventDataChanged(){
        mListAdapter.notifyDataSetChanged();
        Log.d("PublicEve...gment: ", "eventDataChanged()");

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        FBDatabase.addObserver(this);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        FBDatabase.removeObserver(this);
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(String item);
    }

    private class CustomListAdapter extends BaseAdapter {
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            final CustomListAdapter.ViewHolder holder = new CustomListAdapter.ViewHolder();

            if (convertView == null) {

                convertView = inflater.inflate(R.layout.calendar_detail_list_view, parent, false);

            }


            holder.title = (TextView)convertView.findViewById(R.id.calendar_detail_list_view_title);
            holder.time = (TextView)convertView.findViewById(R.id.calendar_detail_list_view_time);
            //holder.description = (TextView)convertView.findViewById(R.id.calendar_detail_list_view_description);


            Event event = toDisplay.get(position);

            if(!toDisplay.isEmpty()){
                holder.title.setText(event.getTitle());
                holder.time.setText(event.getStartTime().toString());
                //holder.description.setText(toDisplay.get(position).getDescription());
            }


            return convertView;
        }
    }
}
