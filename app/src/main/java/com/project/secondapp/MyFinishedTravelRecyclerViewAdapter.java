package com.project.secondapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.project.secondapp.controller.model.entities.Travel;

import java.util.List;

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
                .inflate(R.layout.fragment_fragment_content, parent, false);
        return new MyFinishedTravelsAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull MyFinishedTravelsAdapter.ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mTimeView.setText("שם נוסע: " + mValues.get(position).getClientName());
        holder.mNumberView.setText("מספר טלפון: " + mValues.get(position).getClientNumber());
        holder.mSourceAddressView.setText("מקור: " + mValues.get(position).getStratDrving());
        holder.mDestAddressView.setText("יעד: " + mValues.get(position).getEndDriving());
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
        Button travel_button;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTimeView = (TextView) view.findViewById(R.id.userName);
            mNumberView = (TextView) view.findViewById(R.id.number);
            mSourceAddressView = (TextView) view.findViewById(R.id.sourceAddress);
            mDestAddressView = (TextView) view.findViewById(R.id.destinationAddress);
//            travel_button=(Button) view.findViewById(R.id.item_number);
//            travel_button.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mItem.getDrivingStatus().toString() == "BUSY") {
                //Toast.makeText(v.getContext(), mItem.getDateTravel() + "בדיקה", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(v.getContext(), fragment_content.class);
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
        public String toString() {
            return super.toString() + " '" + mNumberView.getText() + "'";
        }
    }
}
