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
    public  ArrayList<Travel> travels = new ArrayList<Travel>();
    public  ArrayList<Travel> finishTravels = new ArrayList<Travel>();
    public  ArrayList<Travel> contactsList = new ArrayList<Travel>();

    // ----- constructors -----

    public Firebase_DBManager() {
        requests = new ArrayList<>();
        initTravel();

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
        return finishTravels;
    }

    public ArrayList<Travel> getAllContacts() {
        initTravel();
        return contactsList;
    }

    @Override
    public void FinishDrive(String IdDrive) {
        clientsRequestRef.child(IdDrive).child("drivingStatus").setValue("FINISH");
        initTravel();
    }

    @Override
    public void TakeDrive(String myDrive) {
        clientsRequestRef.child(myDrive).child("drivingStatus").setValue("BUSY");
        initTravel();
    }

    @Override
    public ArrayList<Travel> initTravel(int radius) {
    ArrayList<Travel>myFilter= new ArrayList<Travel>();
        for (Travel travel : travels) {
            if (travel.getCurrent().distanceTo(travel.getDestination())/100000<radius) {
                myFilter.add(travel);
            }
        }

      return myFilter;
    }


    public void initTravel() {
        clientsRequestRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                travels.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Travel travel = snapshot.getValue(Travel.class);
                    travel.setId(snapshot.getKey());
                    if (travel.getDrivingStatus().toString() == "FREE") {
                        travels.add(travel);
                    } else if (travel.getDrivingStatus().toString() == "FINISH") {
                        contactsList.add(travel);
                    } else {
                        finishTravels.add(travel);
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
