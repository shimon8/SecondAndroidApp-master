package com.project.secondapp.controller.model.backend;

import android.content.Context;
import android.location.Location;
import android.view.View;

import com.project.secondapp.controller.model.entities.Driver;
import com.project.secondapp.controller.model.entities.Drivingstatus;
import com.project.secondapp.controller.model.entities.Travel;

import java.util.List;

public interface Backend {
    void addDriver(Driver driver,Context context);

}
