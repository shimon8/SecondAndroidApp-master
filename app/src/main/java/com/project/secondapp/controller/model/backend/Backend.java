package com.project.secondapp.controller.model.backend;

import android.content.Context;

import com.project.secondapp.controller.model.entities.Driver;
import com.project.secondapp.controller.model.entities.Travel;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.in;

public interface Backend {
    // ----- helpers methods -----

    /**
     * Add new driver to the firebase
     *
     * @param driver the driver to add
     */

    void addDriver(Driver driver, Context context);

    /**
     * Check the secured Login with the firebase
     *
     * @param UserName the UserName to check
     * @param Password the encrypted Password to check
     */
    void checkLogin(String UserName, int Password, Context context);

    /**
     * Returen all the free travels
     *
     * @return ArrayList<Travel> the match travels list
     */
    ArrayList<Travel> getAllDrive();

    /**
     * Returen all the Busy travels
     *
     * @return ArrayList<Travel> the match travels list
     */
    ArrayList<Travel> getAllFinishDrive();

    /**
     * Returen all the Finished travels
     *
     * @return ArrayList<Travel> the match travels list
     */
    ArrayList<Travel> getAllContacts();

    /**
     * Finish Drive in the firebase
     *
     *
     */
    void FinishDrive(String IdDrive);
    /**
     * Take Drive in the firebase
     *
     *
     */
    void TakeDrive(String IdDrive);
    /**
     * Initilize the Travels Lists
     *
     * @param radius Driver Prefrence radius to filter on it
     * @return List of filtering travels
     */
    ArrayList<Travel> initTravel(int radius);
}
