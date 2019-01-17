package com.project.secondapp.controller.model.datasource;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.project.secondapp.controller.controller.MainApp;
import com.project.secondapp.controller.model.backend.Backend;
import com.project.secondapp.controller.model.backend.BackendFactory;
import com.project.secondapp.controller.model.entities.Driver;
import com.project.secondapp.controller.model.entities.Travel;

import java.util.ArrayList;
import java.util.List;


public class Firebase_DBManager implements Backend {
    public final ArrayList<Travel> travels = new ArrayList<Travel>();
    public final ArrayList<Travel> Finsihtravels = new ArrayList<Travel>();

    // ----- constructors -----

    public Firebase_DBManager() {
        requests = new ArrayList<>();
        initTravel();
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
        initTravel();
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


// the listener to the requests database
// --------- listen to firebase requests changes  ---------

    public interface NotifyDataChange<T> {
        void OnDataChanged(T obj);

        void onFailure(Exception exception);

    }

    private ChildEventListener requestsRefChildEventListener;
    private ChildEventListener serviceListener;

    public ArrayList<Travel> getAllDrive() {
        initTravel();
        return travels;
    }
    public ArrayList<Travel> getAllFinishDrive() {
        initTravel();
        return Finsihtravels;
    }

    @Override
    public void TakeDrive(String myDrive) {
        clientsRequestRef.child(myDrive).child("drivingStatus").setValue("BUSY");
    }

    public void initTravel() {
        clientsRequestRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               travels.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Travel travel = snapshot.getValue(Travel.class);
                    travel.setId(snapshot.getKey());
                    if(travel.getDrivingStatus().toString()=="FREE") {
                        travels.add(travel);
                    }
                    else
                    {
                        Finsihtravels.add(travel);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    public void notifyToRequsetsList(ChildEventListener listener) {
        clientsRequestRef.addChildEventListener(listener);
    }

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

    @Override
    public void addDriver(Driver driver, Context context) {
        try {
            if (!driverExist(driver.getUserName())) {
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
            } else {
                {
                    Toast.makeText(context, "שם משתמש כבר קיים במערכת", Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void checkLogin(String UserName, int Password, Context context) {
        driversRef.orderByChild("userName").equalTo(UserName).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            DataSnapshot DSuser = dataSnapshot.getChildren().iterator().next();
                            Driver d = DSuser.getValue(Driver.class);
                            if (d.getPassword() == Password) {
                                Intent mainApp = new Intent(context, MainApp.class);
                                context.startActivity(mainApp);
                            } else {
                                Toast.makeText(context, "פרטים שגויים, מלא שנית", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(context, "פרטים שגויים, מלא שנית", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        return;
                    }
                });
    }
}
