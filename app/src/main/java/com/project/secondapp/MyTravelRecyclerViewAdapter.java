package com.project.secondapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.project.secondapp.TravelFragment.OnListFragmentInteractionListener;
import com.project.secondapp.controller.model.entities.Travel;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Travel} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyTravelRecyclerViewAdapter extends RecyclerView.Adapter<MyTravelRecyclerViewAdapter.ViewHolder> {

    private final List<Travel> mValues;
    private final OnListFragmentInteractionListener mListener;

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
        //TODO לשדה התואם בהולדר position לקשר כל שדה מהאובייקט במקום
        holder.mItem = mValues.get(position);
        holder.mTimeView.setText(mValues.get(position).getStratDrving());
        holder.mContentView.setText(mValues.get(position).getClientName());

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
        //TODO ליצור את כל הקומפוננטות שיציגו נסיעה אחת
        public final TextView mTimeView;
        public final TextView mContentView;
        public final View mView;
        public Travel mItem;
        Button travel_button;

        public ViewHolder(View view) {
            //TODO view לחבר את כל הקומפוננטות לשדות ע"י ה
            //TODO fragment_travel.xml להגדיר איך תראה כל נסיע ב
            super(view);
            mView = view;
            mTimeView = (TextView) view.findViewById(R.id.item_time);
            mContentView = (TextView) view.findViewById(R.id.content);
            mContentView.setOnClickListener(this);
//            travel_button=(Button) view.findViewById(R.id.item_number);
//            travel_button.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        @Override
        public void onClick(View v) {
                Toast.makeText(v.getContext() , "בדיקה", Toast.LENGTH_LONG).show();
        }
    }
}
