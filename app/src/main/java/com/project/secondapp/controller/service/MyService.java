package com.project.secondapp.controller.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.project.secondapp.controller.model.backend.BackendFactory;
import com.project.secondapp.controller.model.datasource.Firebase_DBManager;
import com.project.secondapp.controller.model.entities.Drivingstatus;
import com.project.secondapp.controller.controller.MyReceiver;
import com.project.secondapp.controller.model.entities.Travel;

import java.io.Serializable;
import java.util.List;

public class MyService extends Service {

    private int lastCount = 0;
    Context context;
    Firebase_DBManager dbManager;

    @Override
    public int onStartCommand(final Intent intent, int flags, int startId) {
        dbManager = (Firebase_DBManager) BackendFactory.getBackend();
        context = getApplicationContext();
        Intent intent1 = new Intent(context, MyReceiver.class);
        dbManager.notifyToRequsetsList(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                try {
                    Travel  travel = dataSnapshot.getValue(Travel.class);
                    intent1.putExtra("name", travel.getClientName());
                    intent1.putExtra("phone", travel.getClientNumber());
                    sendBroadcast(intent1);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
