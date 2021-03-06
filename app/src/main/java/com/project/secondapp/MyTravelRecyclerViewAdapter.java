package com.project.secondapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.app.FragmentTransaction;
import android.app.FragmentManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.project.secondapp.TravelFragment.OnListFragmentInteractionListener;
import com.project.secondapp.controller.controller.AddDriver;
import com.project.secondapp.controller.controller.MainApp;
import com.project.secondapp.controller.controller.TravelActivity;
import com.project.secondapp.controller.model.backend.Backend;
import com.project.secondapp.controller.model.backend.BackendFactory;
import com.project.secondapp.controller.model.entities.Travel;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Travel} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */

public class MyTravelRecyclerViewAdapter extends RecyclerView.Adapter<MyTravelRecyclerViewAdapter.ViewHolder> {

    private List<Travel> mValues;
    private final OnListFragmentInteractionListener mListener;
    Geocoder geocoder;
    List<Address> addresses;
    public static int radius;


    public MyTravelRecyclerViewAdapter(List<Travel> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_travel, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTimeView.setText("זמן נסיעה: " + mValues.get(position).getDateTravel());
        holder.mContentView.setText("שם נוסע: " + mValues.get(position).getClientName());
        holder.mSourceAddressView.setText("מקור: " + mValues.get(position).getStratDrving());
        holder.mDestAddressView.setText("יעד: " + mValues.get(position).getEndDriving());
        if (position == 0) {
            holder.mSeekBarView.setVisibility(View.VISIBLE);
            holder.mSeekBarView.setProgress(radius);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {
        public final TextView mTimeView;
        public final TextView mContentView;
        public final TextView mSourceAddressView;
        public final TextView mDestAddressView;
        public final View mView;
        public Travel mItem;
        Button travel_button;
        public final SeekBar mSeekBarView;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTimeView = (TextView) view.findViewById(R.id.item_time);
            mContentView = (TextView) view.findViewById(R.id.content);
            mSourceAddressView = (TextView) view.findViewById(R.id.sourceAddress);
            mDestAddressView = (TextView) view.findViewById(R.id.destinationAddress);
            mSeekBarView = (SeekBar) view.findViewById(R.id.seekBar);
            mSeekBarView.setProgress(radius);

            mSeekBarView.setOnSeekBarChangeListener(this);
            SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    radius = progress;
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    final Backend backend = BackendFactory.getBackend();
                    radius = seekBar.getProgress();
                    backend.initTravel(radius);
                    mValues = backend.getAllDrive();
                }
            };
            mContentView.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        @Override
        public void onClick(View v) {
            if (mItem.getDrivingStatus().toString() == "FREE") {
                Intent intent = new Intent(v.getContext(), TravelActivity.class);
                intent.putExtra("TimeOfTravel", mItem.getDateTravel());
                intent.putExtra("startDriving", mItem.getStratDrving());
                intent.putExtra("endDriving", mItem.getEndDriving());
                intent.putExtra("name", mItem.getClientName());
                intent.putExtra("number", mItem.getClientNumber());
                intent.putExtra("email", mItem.getClientEmail());
                intent.putExtra("id", mItem.getId());
                v.getContext().startActivity(intent);
            }
        }

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        }
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }
        @Override
        public void onStopTrackingTouch(SeekBar seekBar)  {
            final Backend backend = BackendFactory.getBackend();
            radius = seekBar.getProgress();
            ArrayList<Travel> myFilter=backend.initTravel(radius);
            mValues=myFilter;
            if(seekBar.getParent().getParent() instanceof RecyclerView)
            {

                RecyclerView recyclerView=(RecyclerView) seekBar.getParent().getParent();
                recyclerView.setAdapter(new MyTravelRecyclerViewAdapter(mValues,mListener));
            }
        }
    }
}