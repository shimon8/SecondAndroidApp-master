package com.project.secondapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.project.secondapp.controller.model.backend.Backend;
import com.project.secondapp.controller.model.backend.BackendFactory;
import com.project.secondapp.controller.model.entities.Travel;

import java.util.List;

import static android.content.Intent.getIntent;

class MyFinishedTravelsAdapter extends RecyclerView.Adapter<MyFinishedTravelsAdapter.ViewHolder> {
    private final List<Travel> mValues;
    private final TravelFragment.OnListFragmentInteractionListener mListener;

    public MyFinishedTravelsAdapter(List<Travel> items, TravelFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_end_travel, parent, false);
        return new MyFinishedTravelsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyFinishedTravelsAdapter.ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTimeView.setText("שם נוסע: " + mValues.get(position).getClientName());
        holder.mNumberView.setText("מספר טלפון: " + mValues.get(position).getClientNumber());
        holder.mSourceAddressView.setText("מקור: " + mValues.get(position).getStratDrving());
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mTimeView;
        public final TextView mNumberView;
        public final TextView mSourceAddressView;
        public final TextView mDestAddressView;
        public final View mView;
        public Travel mItem;
        Button finish_travel_button;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTimeView = (TextView) view.findViewById(R.id.userName);
            mNumberView = (TextView) view.findViewById(R.id.number);
            mSourceAddressView = (TextView) view.findViewById(R.id.sourceAddress);
            mDestAddressView = (TextView) view.findViewById(R.id.endAddress);
            finish_travel_button = (Button) view.findViewById(R.id.endTravel);
            finish_travel_button.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItem.getDrivingStatus().toString() == "BUSY") {
                final Backend backend = BackendFactory.getBackend();
                backend.FinishDrive(mItem.getId());
                mValues.remove(mItem);
                if(v.getParent().getParent() instanceof RecyclerView)
                {
                    RecyclerView recyclerView=(RecyclerView) v.getParent().getParent();
                    recyclerView.setAdapter(new MyFinishedTravelsAdapter(mValues,mListener));
                }
            }
        }


        @Override
        public String toString() {
            return super.toString() + " '" + mNumberView.getText() + "'";
        }
    }
}
