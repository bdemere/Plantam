package com.cpsc310proj.babib.plantam.Layouts.PublicEventsLayout;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cpsc310proj.babib.plantam.Enums.Category;
import com.cpsc310proj.babib.plantam.Firebase.FBDatabase;
import com.cpsc310proj.babib.plantam.Firebase.FireBaseDataObserver;
import com.cpsc310proj.babib.plantam.R;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class PublicEventCategoryFragment extends Fragment implements FireBaseDataObserver {

    // TODO: Customize parameter argument names
    private static final String ARG_CATEGORY_NAME = "com.cpsc310proj.babib.plantam.Layouts." +
            "ARG_CATEGORY_NAME";

    // TODO: Customize parameters
    private Category mCategory;
    private RecyclerView mRecyclerView;
    private EventRecyclerViewAdapter mAdapter;
    private OnListFragmentInteractionListener mListener;

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

        // Set the mAdapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            mRecyclerView = (RecyclerView) view;
            //if (mColumnCount <= 1) {
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            //} else {
            //    recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            //}

            mAdapter = new EventRecyclerViewAdapter(FBDatabase.getPublicEventsWithCategory(mCategory)
                      , mListener);

            mRecyclerView.setAdapter(mAdapter);
        }
        return view;
    }


    public void eventDataChanged(){
        if(mAdapter != null)
            mAdapter.notifyDataSetChanged();
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
}
