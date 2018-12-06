package com.project.secondapp.controller.model.backend;

import android.content.Context;
import android.location.Location;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.Query;
import com.project.secondapp.controller.controller.MainActivity;
import com.project.secondapp.controller.model.entities.Driver;
import com.project.secondapp.controller.model.entities.Drivingstatus;
import com.project.secondapp.controller.model.entities.Travel;

import java.util.List;

public interface Backend {
    void addDriver(Driver driver,Context context);
    void checkLogin(String UserName, int Password, Context context);
}
