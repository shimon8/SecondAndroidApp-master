package com.project.secondapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.secondapp.controller.model.entities.Travel;
import com.project.secondapp.dummy.DummyContent;



/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EndTravelFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EndTravelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class EndTravelFragment extends Fragment {
    private int mColumnCount = 1;
    private static final String ARG_COLUMN_COUNT= "column-count";
    private DummyContent content;
    private OnFragmentInteractionListener mListener;
    public EndTravelFragment() {
    }


    public static EndTravelFragment newInstance(int columnCount, DummyContent content) {
        EndTravelFragment fragment = new EndTravelFragment();
        fragment.setContent(content);
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
     }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_end_travel_list, container, false);
        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyFinishedTravelsAdapter(content.items, mListener));
        }
        return view;
    }
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
           mListener = new OnFragmentInteractionListener() {
               @Override
               public void onFragmentInteraction(Uri uri) {

               }

               @Override
               public void onListFragmentInteraction(Travel item) {

               }
           };
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void setContent(DummyContent content) {
        this.content = content;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener extends TravelFragment.OnListFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);

        @Override
        void onListFragmentInteraction(Travel item);
    }
}
