package com.project.secondapp.controller.model.backend;

import android.content.Context;
import android.location.Location;
import com.project.secondapp.controller.model.entities.Driver;
import com.project.secondapp.controller.model.entities.Drivingstatus;
import com.project.secondapp.controller.model.entities.Travel;

import java.util.List;

public interface Backend {
    void addDriver(Driver driver, Context context);

    Driver getDriver(Driver driver);

    List<Travel> getAllRequest();

    List<Travel> getRequest(Location driverLocation, int numRequest);

    List<Travel> getRequest(Location driverLocation, int numRequest, double distance);

    List<Travel> getRequest(Location driverLocation, int numRequest, Drivingstatus status);

    List<Travel> getRequest(Location driverLocation, int numRequest, int distance, Drivingstatus status);

    void changeStatus(String requestID, Driver driver, Drivingstatus status, Context context);
}
