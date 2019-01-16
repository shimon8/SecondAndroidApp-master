package com.project.secondapp.controller.model.backend;

import android.content.Context;

import com.project.secondapp.controller.model.entities.Driver;
import com.project.secondapp.controller.model.entities.Travel;

import java.util.ArrayList;
import java.util.List;

public interface Backend {
    void addDriver(Driver driver,Context context);
    void checkLogin(String UserName, int Password, Context context);
    ArrayList<Travel> getAllDrive();
    ArrayList<Travel> getAllFinishDrive();
    void TakeDrive(Travel myDrive);
}
