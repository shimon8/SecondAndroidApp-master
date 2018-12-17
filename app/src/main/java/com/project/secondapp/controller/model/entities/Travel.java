package com.project.secondapp.controller.model.entities;

import android.location.Location;

public class Travel {
    String        id;
    Drivingstatus drivingStatus;
    mLocation     current;
    mLocation     destination;
    String        stratDrving;
    String        endDriving;
    String        clientName;
    String        clientNumber;
    String        clientEmail;
   //region getter and setter
    public Drivingstatus getDrivingStatus() {
        return drivingStatus;
    }

    public void setDrivingStatus(Drivingstatus drivingStatus) {
        this.drivingStatus = drivingStatus;
    }

    public Location getCurrent() {
        return current;
    }

    public void setCurrent(mLocation current) {
        this.current = current;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(mLocation destination) {
        this.destination = destination;
    }

    public String getStratDrving() {
        return stratDrving;
    }

    public void setStratDrving(String stratDrving) {
        this.stratDrving = stratDrving;
    }

    public String getEndDriving() {
        return endDriving;
    }

    public void setEndDriving(String endDriving) {
        this.endDriving = endDriving;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(String clientNumber) {
        this.clientNumber = clientNumber;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
//endregion
}
