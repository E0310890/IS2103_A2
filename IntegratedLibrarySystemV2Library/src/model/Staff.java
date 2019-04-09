package model;

import java.io.Serializable;

public class Staff implements Serializable {

    private Long staffID;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;

    public Staff() {
    }
    
    public Staff(Long staffID, String firstName, String lastName, String userName, String password) {
        this.staffID = staffID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
    }

    public Staff(String firstName, String lastName, String userName, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
    }

    public Long getStaffID() {
        return staffID;
    }

    public void setStaffID(Long staffID) {
        this.staffID = staffID;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Staff ID: " + getStaffID() + " | Full Name: " + getFirstName() + " " + getLastName();
    }
}