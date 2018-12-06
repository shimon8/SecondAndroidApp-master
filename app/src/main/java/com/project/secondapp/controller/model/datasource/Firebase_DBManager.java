package com.project.secondapp.controller.model.datasource;

import android.annotation.TargetApi;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;
import android.os.AsyncTask;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.secondapp.controller.model.backend.Backend;
import com.project.secondapp.controller.model.backend.BackendFactory;
import com.project.secondapp.controller.model.entities.Driver;
import com.project.secondapp.controller.model.entities.Drivingstatus;
import com.project.secondapp.controller.model.entities.Travel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Firebase_DBManager implements Backend {
    // ----- constructors -----

    public Firebase_DBManager() {
        requests = new ArrayList<>();
//        this.notifyToRequsetsList(new NotifyDataChange<List<Travel>>() {
//            @Override
//            public void OnDataChanged(List<Travel> obj) {
//                if (requests != obj) {
//                    requests = obj;
//                }
//            }
//            @Override
//            public void onFailure(Exception exception) {
//            }
//        });
    }


    public Firebase_DBManager(BackendFactory.Friend friend) {
        //if try to create Firebase_DBManager with null param
        //this throw NullPointerException
        requests = new ArrayList<>();
//        this.notifyToRequsetsList(new NotifyDataChange<List<Travel>>() {
//            @Override
//            public void OnDataChanged(List<Travel> obj) {
//                if (requests != obj) {
//                    requests = obj;
//                }
//            }
//
//            @Override
//            public void onFailure(Exception exception) {
//            }
//        });
        //f.hashCode();
    }

    private DatabaseReference clientsRequestRef = FirebaseDatabase.getInstance().getReference("clients");
    private DatabaseReference driversRef = FirebaseDatabase.getInstance().getReference("drivers");
    private List<Travel> requests;


    private boolean driverExist(String userName) {
        final Boolean[] result = {null};
        driversRef.orderByChild("UserName").equalTo(userName).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        result[0] = dataSnapshot.exists();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        result[0] = false;
                    }
                });
        while (result[0] == null) ;
        return result[0];
    }

// the listener to the requests database
// --------- listen to firebase requests changes  ---------

    public interface NotifyDataChange<T> {
        void OnDataChanged(T obj);

        void onFailure(Exception exception);

    }

    private ChildEventListener requestsRefChildEventListener;
    private ChildEventListener serviceListener;


//    public void notifyToRequsetsList(final NotifyDataChange<List<Travel>> notifyDataChange) {
//        if (notifyDataChange != null) {
//
//            if (requestsRefChildEventListener != null) {
//                if (serviceListener != null) {
//                    notifyDataChange.onFailure(new Exception("first unNotify Travel list"));
//                    return;
//                } else {
//                    serviceListener = new ChildEventListener() {
//                        @Override
//                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                            notifyDataChange.OnDataChanged(requests);
//                        }
//
//                        @Override
//                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                        }
//
//                        @Override
//                        public void onChildRemoved(DataSnapshot dataSnapshot) {
//                        }
//
//                        @Override
//                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//                        }
//
//                        @Override
//                        public void onCancelled(DatabaseError databaseError) {
//                        }
//                    };
//                    clientsRequestRef.addChildEventListener(serviceListener);
//                    return;
//                }
//            }
//            requests.clear();
//            requestsRefChildEventListener = new ChildEventListener() {
//                @Override
//                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                    Travel request = dataSnapshot.getValue(Travel.class);
//                    //TODO here the conditions for relevant requests
//                    request.setClientNumber(dataSnapshot.getKey());
//                    requests.add(request);
//                    notifyDataChange.OnDataChanged(requests);
//                }
//
//                @Override
//                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                    Travel request = dataSnapshot.getValue(Travel.class);
//                    String id = dataSnapshot.getKey();
//
//                    for (int i = 0; i < requests.size(); i++) {
//                        if (requests.get(i).getClientNumber().equals(id)) {
//                            request.setClientNumber(id);
//                            requests.set(i, request);
//                            break;
//                        }
//                    }
//                    notifyDataChange.OnDataChanged(requests);
//                }
//
//                @Override
//                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//                    String id = dataSnapshot.getKey();
//
//                    for (int i = 0; i < requests.size(); i++) {
//                        if (requests.get(i).getClientNumber().equals(id)) {
//                            requests.remove(i);
//                            break;
//                        }
//                    }
//                    notifyDataChange.OnDataChanged(requests);
//                }
//
//                @Override
//                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    notifyDataChange.onFailure(databaseError.toException());
//                }
//            };
//            clientsRequestRef.addChildEventListener(requestsRefChildEventListener);
//        }
//    }
//                driversRef.push().setValue(driver).addOnSuccessListener(new OnSuccessListener() {
//                    @Override
//                    public void onSuccess(Object o) {
//                        Toast.makeText(context, "נרשמת בהצלחה", Toast.LENGTH_LONG).show();
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(context, "בקשתך נכשלה", Toast.LENGTH_LONG).show();
//                    }
//                });


    @Override
    public void addDriver(Driver driver, Context context) {
        try {
                driversRef.push().setValue(driver).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(context, "הבקשה נשלחה בהצלחה", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "בקשתך נכשלה", Toast.LENGTH_LONG).show();
                    }
                });
           {
                Toast.makeText(context, "שם משתמש כבר קיים במערכת", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
