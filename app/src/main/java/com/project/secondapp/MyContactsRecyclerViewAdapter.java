package com.project.secondapp;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.project.secondapp.TravelFragment.OnListFragmentInteractionListener;
import com.project.secondapp.controller.controller.TravelActivity;
import com.project.secondapp.controller.model.entities.Travel;

import java.util.ArrayList;
import java.util.List;

import static android.support.v4.app.ActivityCompat.startActivityForResult;
import static android.support.v4.content.ContextCompat.startActivity;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Travel} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */

public class MyContactsRecyclerViewAdapter extends RecyclerView.Adapter<MyContactsRecyclerViewAdapter.ViewHolder> {

    private final List<Travel> mValues;
    private final ContactsFragment.OnListFragmentInteractionListener mListener;
    Geocoder geocoder;
    List<Address> addresses;

    public MyContactsRecyclerViewAdapter(List<Travel> items, ContactsFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_add_contacts, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        //TODO לשדה התואם בהולדר position לקשר כל שדה מהאובייקט במקום
        holder.mItem = mValues.get(position);
        holder.mContentView.setText("שם נוסע: " + mValues.get(position).getClientName());
        holder.mPhoneView.setText("מספר טלפון: " + mValues.get(position).getClientNumber());

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
        public final TextView mPhoneView;
        public final TextView mContentView;
        public final FloatingActionButton AddContact;
        public final View mView;
        public Travel mItem;
        Button travel_button;

        public ViewHolder(View view) {
            //TODO view לחבר את כל הקומפוננטות לשדות ע"י ה
            super(view);
            mView = view;
            mContentView = view.findViewById(R.id.userName);
            mPhoneView = view.findViewById(R.id.number);
            AddContact = view.findViewById(R.id.AddContact);
            AddContact.setOnClickListener(this);
//            travel_button=(Button) view.findViewById(R.id.item_number);
//            travel_button.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }


        @Override
        public void onClick(View v) {
//            Intent contactIntent = new Intent(ContactsContract.Intents.Insert.ACTION);
//            contactIntent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
//            Bundle bundle = new Bundle();
//            contactIntent
//                    .putExtra(ContactsContract.Intents.Insert.NAME, mContentView.getText().toString().replace("שם נוסע: ",""))
//                    .putExtra(ContactsContract.Intents.Insert.PHONE, mPhoneView.getText().toString().replace("מספר טלפון: ",""));
//            startActivity(v.getContext(),contactIntent,bundle);

//            Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
//            intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
//            intent.
//                    putExtra(ContactsContract.Intents.Insert.PHONE, mPhoneView.getText().toString()).
//                    putExtra(ContactsContract.Intents.Insert.PHONE_TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_WORK).
//                    putExtra(ContactsContract.Intents.Insert.NAME, mContentView.getText().toString());
//            startActivity(intent);

            ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
            int rawContactInsertIndex = ops.size();
            ops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                    .build());

            //INSERT NAME
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, mItem.getClientName()) // Name of the person
                    .withValue(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME, mItem.getClientName())
                    .build());

            //INSERT PHONE
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, mItem.getClientNumber()) // Number of the person
                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                    .build());

            //INSERT EMAIL
            ops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, rawContactInsertIndex)
                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                    .withValue(ContactsContract.CommonDataKinds.Email.DATA, mItem.getClientEmail())
                    .withValue(ContactsContract.CommonDataKinds.Email.TYPE, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                    .build());
            Uri newContactUri = null;

            //PUSH EVERYTHING TO CONTACTS
            try {
                ContentProviderResult[] res = v.getContext().getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
                if (res != null && res[0] != null) {
                    newContactUri = res[0].uri;
                    //02-20 22:21:09 URI added contact:content://com.android.contacts/raw_contacts/612

                } else
                    Toast.makeText(v.getContext(), "Contact not added.", Toast.LENGTH_LONG).show();
            } catch (RemoteException e) {
                // error
                Toast.makeText(v.getContext(), "Error (1) adding contact.", Toast.LENGTH_LONG).show();
                newContactUri = null;
            } catch (OperationApplicationException e) {
                // error
                Toast.makeText(v.getContext(), "Error (2) adding contact.", Toast.LENGTH_LONG).show();
                newContactUri = null;
            }
            Toast.makeText(v.getContext(), "האיש קשר נשמר בהצלחה ", Toast.LENGTH_LONG).show();

            if (newContactUri == null) {
                Toast.makeText(v.getContext(), "Error creating contact", Toast.LENGTH_LONG).show();
            }

        }
    }
}