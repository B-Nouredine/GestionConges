package com.ensaf.nour.gestion_conges.admin.employees;

import java.util.Date;

public class EmployeeUnit {

    private String employeeID;
    private String  firstName ;
    private String  lastName ;
    private String  phone ;
    private String  mission ;
    private String  username ;
    private String startDate ;
    private String profilePic;

    public EmployeeUnit(String employeeID, String firstName, String lastName, String phone, String mission, String username, String startDate, String profilePic) {
        this.employeeID = employeeID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.mission = mission;
        this.username = username;
        this.startDate = startDate;
        this.profilePic = profilePic;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }
}
