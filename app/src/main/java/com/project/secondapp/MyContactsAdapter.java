package com.project.secondapp;

import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
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
import static android.support.v4.content.ContextCompat.startActivity;

class MyContactsAdapter extends RecyclerView.Adapter<MyContactsAdapter.ViewHolder> {
    private final List<Travel> mValues;
    private final TravelFragment.OnListFragmentInteractionListener mListener;
    private Context context;
    public MyContactsAdapter(List<Travel> items, TravelFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_add_contacts, parent, false);
        return new MyContactsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyContactsAdapter.ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText("שם נוסע: " + mValues.get(position).getClientName());
        holder.mNumberView.setText("מספר טלפון: " + mValues.get(position).getClientNumber());
        holder.mSourceAddressView.setText("מקור: " + mValues.get(position).getStratDrving());
        // holder.mDestAddressView.setText("יעד: " + mValues.get(position).getEndDriving());
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
        public final TextView mNameView;
        public final TextView mNumberView;
        public final TextView mSourceAddressView;
        public final TextView mDestAddressView;
        public final TextView mEmailView;
        public final View mView;
        public Travel mItem;
        Button add_contact_button;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.userName);
            mNumberView = (TextView) view.findViewById(R.id.number);
            mSourceAddressView = (TextView) view.findViewById(R.id.sourceAddress);
            mDestAddressView = (TextView) view.findViewById(R.id.endAddress);
            mEmailView = (TextView) view.findViewById(R.id.email);
            add_contact_button = (Button) view.findViewById(R.id.AddContact);
            //add_contact_button.setOnClickListener(this);
            add_contact_button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick (View v){

                    String[] mails = {mEmailView.getText().toString()};
                    Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                    intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                    intent.
                            putExtra(ContactsContract.Intents.Insert.EMAIL, mails).
                            putExtra(ContactsContract.Intents.Insert.EMAIL_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK).
                            putExtra(ContactsContract.Intents.Insert.PHONE, mNumberView.getText().toString()).
                            putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK).
                            putExtra(ContactsContract.Intents.Insert.NAME, mNameView.getText().toString());
                    //startActivity(intent);
                }
            });
        }



        @Override
        public void onClick(View v) {
            //if (mItem.getDrivingStatus().toString() == "FINISH") {

            String[] mails = {mEmailView.getText().toString()};
            Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
            intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
            intent.
                    putExtra(ContactsContract.Intents.Insert.EMAIL, mails).
                    putExtra(ContactsContract.Intents.Insert.EMAIL_TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK).
                    putExtra(ContactsContract.Intents.Insert.PHONE, mNumberView.getText().toString()).
                    putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK).
                    putExtra(ContactsContract.Intents.Insert.NAME, mNameView.getText().toString());
            //startActivity(intent);

                //Toast.makeText(v.getContext(), mItem.getDateTravel() + "בדיקה", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(v.getContext(), EndTravelFragment.class);
//                intent.putExtra("TimeOfTravel", mItem.getDateTravel());
//                intent.putExtra("startDriving", mItem.getStratDrving());
//                intent.putExtra("endDriving", mItem.getEndDriving());
//                intent.putExtra("name", mItem.getClientName());
//                intent.putExtra("number", mItem.getClientNumber());
//                intent.putExtra("email", mItem.getClientEmail());
//                intent.putExtra("id", mItem.getId());
//                v.getContext().startActivity(intent);

           // }
        }


        @Override
        public String toString() {
            return super.toString() + " '" + mNumberView.getText() + "'";
        }
    }
}
