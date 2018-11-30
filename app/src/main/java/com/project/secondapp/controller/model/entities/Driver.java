package com.project.secondapp.controller.model.entities;

public class Driver {
    String id;
    String password;
    String LastName;
    String FirstName;
    String email;
    String phone;
    String UserName;

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    //region getter and setter
    public String getId() {
        return id;
    }


    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String Password) {
        password = Password;
    }
    //endregion
}