package com.project.secondapp.controller.model.datasource;

import android.annotation.TargetApi;
import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.project.secondapp.controller.model.backend.Backend;
import com.project.secondapp.controller.model.entities.Driver;
import com.project.secondapp.controller.model.entities.Drivingstatus;
import com.project.secondapp.controller.model.entities.Travel;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Firebase_DBManager implements Backend {

    private DatabaseReference clientsRequestRef = FirebaseDatabase.getInstance().getReference("clients");
    private DatabaseReference driversRef = FirebaseDatabase.getInstance().getReference("drivers");

    private List<Travel> requests;

    // ----- constructor -----
    public Firebase_DBManager() {
        requests = new ArrayList<>();
        this.notifyToRequsetsList(new NotifyDataChange<List<Travel>>() {
            @Override
            public void OnDataChanged(List<Travel> obj) {
                if (requests != obj) {
                    requests = obj;
                }
            }

            @Override
            public void onFailure(Exception exception) {
            }
        });
    }

    // ----- helpers methods -----

    /**
     * sort the client requests by distance from the driver
     *
     * @param driverLocation the driver location
     * @param _requests      the list of all client requests
     * @return new sorted list
     */
    private List<Travel> sortByDistance(Location driverLocation, List<Travel> _requests) {
        Map<Double, Travel> distanceMap = new HashMap<>();
        double distance;
        Location loc = new Location(LocationManager.GPS_PROVIDER);
        for (Travel request : _requests) {
            //loc.setLatitude(request.getSourceLatitude());
            //loc.setLongitude(request.getSourceLongitude());
            distance = driverLocation.distanceTo(loc);
            distanceMap.put(distance, request);
        }
        return new ArrayList<>(distanceMap.values());
    }


    // ------ implement's methods -----

    //    --- the driver methods  ---

    /**
     * add new driver
     *
     * @param driver  the driver with all details
     * @param context the activity context
     */

    @Override
    public void addDriver(Driver driver, final Context context) {
        try {
            if (true) {
                driversRef.push().setValue(driver).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(context, "נרשמת בהצלחה", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "בקשתך נכשלה", Toast.LENGTH_LONG).show();
                    }
                });
                /*
                driversRef.push().setValue(driver)
                        .addOnSuccessListener((OnSuccessListener<Object>) o -> Toast.makeText(context, "נוספת בהצלחה", Toast.LENGTH_LONG).show())
                        .addOnFailureListener(e -> Toast.makeText(context, "שגיאה בהתחברות", Toast.LENGTH_LONG).show());
            */} else {
                Toast.makeText(context, "שם משתמש כבר קיים במערכת", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public Driver getDriver(Driver driver) {
        return null;
    }

    /**
     * get driver by email and password
     *
     * @param driver the driver with email and password
     * @return if found new full driver else new empty driver
     */
    /*
    @Override
    public Driver getDriver(final Driver driver) {
        final Driver[] drivers = new Driver[1];
        drivers[0] = null;
        String email = driver.getEmail();
        String password = driver.getPassword();


        Query query = driversRef.orderByChild("email___password")
                .equalTo(email + "___" + password).limitToFirst(1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    drivers[0] = dataSnapshot.getChildren().iterator().next().getValue(Driver.class);
                else
                    drivers[0] = new Driver();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                drivers[0] = null;
            }
        });
        while (drivers[0] == null) ;
        return drivers[0];
    }

*/
    //    --- the client request methods  ---

    /**
     * get all request
     *
     * @return the requests list
     */
    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public List<Travel> getAllRequest() {
        return requests.stream()
                .filter(p -> p.getDrivingStatus() == Drivingstatus.BUSY)
                .collect(Collectors.toList());
    }


    @Override
    public List<Travel> getRequest(Location driverLocation, int numRequest) {
        return this.sortByDistance(driverLocation, requests).subList(0,
                this.requests.size() > numRequest ? numRequest : this.requests.size());
    }

    @Override
    public List<Travel> getRequest(final Location driverLocation, int numRequest,
                                   final double distance) {
        List<Travel> requestList = new LinkedList<>();
        Location loc = new Location(LocationManager.GPS_PROVIDER);
        for (Travel request : requests) {
            //loc.setLatitude(request.getSourceLatitude());
            //loc.setLongitude(request.getSourceLongitude());
            if (loc.distanceTo(driverLocation) / 1000 <= distance)
                requestList.add(request);
        }
        return requestList.subList(0, requestList.size() >= numRequest ? numRequest : requestList.size());
    }

    @Override
    public List<Travel> getRequest(Location driverLocation, int numRequest, Drivingstatus status) {
        List<Travel> requestList = new LinkedList<>();
        for (Travel request : requests)
            //if (request.getStatus() == status)
            requestList.add(request);
        return requestList.subList(0, requestList.size() >= numRequest ? numRequest : requestList.size());
    }

    @Override
    public List<Travel> getRequest(Location driverLocation, int numRequest,
                                   int distance, Drivingstatus status) {
        List<Travel> requestList = new LinkedList<>();
        Location loc = new Location(LocationManager.GPS_PROVIDER);
        for (Travel request : requests) {
            //loc.setLatitude(request.getSourceLatitude());
            //loc.setLongitude(request.getSourceLongitude());
            //if (loc.distanceTo(driverLocation)/1000 <= distance && request.getStatus() == status)
            requestList.add(request);
        }
        return requestList.subList(0, requestList.size() >= numRequest ? numRequest : requestList.size());
    }

    @Override
    public void changeStatus(String requestID, Driver driver, final Drivingstatus status,
                             final Context context) {
        clientsRequestRef.child(requestID).child("driverID").setValue(driver.getId());
        clientsRequestRef.child(requestID).child("status").setValue(status)
                .addOnSuccessListener(aVoid -> Toast.makeText(context, "the status update to: " + status, Toast.LENGTH_LONG).show())
                .addOnFailureListener(e -> Toast.makeText(context, "fail to update the status", Toast.LENGTH_LONG).show());
    }


// the listener to the requests database
// --------- listen to firebase requests changes  ---------

    public interface NotifyDataChange<T> {
        void OnDataChanged(T obj);

        void onFailure(Exception exception);

    }

    private ChildEventListener requestsRefChildEventListener;
    private ChildEventListener serviceListener;


    public void notifyToRequsetsList(final NotifyDataChange<List<Travel>> notifyDataChange) {
        if (notifyDataChange != null) {

            if (requestsRefChildEventListener != null) {
                if (serviceListener != null) {
                    notifyDataChange.onFailure(new Exception("first unNotify Travel list"));
                    return;
                } else {
                    serviceListener = new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            notifyDataChange.OnDataChanged(requests);
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {
                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    };
                    clientsRequestRef.addChildEventListener(serviceListener);
                    return;
                }
            }
            requests.clear();

            requestsRefChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Travel request = dataSnapshot.getValue(Travel.class);
                    //TODO here the conditions for relevant requests
                    //request.setId(dataSnapshot.getKey());
                    requests.add(request);
                    notifyDataChange.OnDataChanged(requests);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    Travel request = dataSnapshot.getValue(Travel.class);
                    String id = dataSnapshot.getKey();

                    for (int i = 0; i < requests.size(); i++) {
                        //if (requests.get(i).getId().equals(id)) {
                        //request.setId(id);
                        requests.set(i, request);
                        break;
                        // }
                    }
                    notifyDataChange.OnDataChanged(requests);
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                /*
                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                    String id = dataSnapshot.getKey();

                    for (int i = 0; i < requests.size(); i++) {
                        if (requests.get(i).getId().equals(id)) {
                            requests.remove(i);
                            break;
                        }
                    }
                    notifyDataChange.OnDataChanged(requests);
                }
*/
                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    notifyDataChange.onFailure(databaseError.toException());
                }
            };
            clientsRequestRef.addChildEventListener(requestsRefChildEventListener);
        }
    }
}
