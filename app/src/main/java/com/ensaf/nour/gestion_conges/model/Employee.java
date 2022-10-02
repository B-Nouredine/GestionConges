package com.ensaf.nour.gestion_conges.model;

import java.util.Date;

public class Employee {

    //exclude key from toString
    private String  firstName ;
    private String  lastName ;
    private String  phone ;
    private String  mission ;
    private String  username ;
    private String  password ;
    private Date startDate ;
    private String profilePic;

    public Employee(){}

    public Employee(String firstName, String lastName, String phone, String mission, String username, String password, Date startDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.mission = mission;
        this.username = username;
        this.password = password;
        this.startDate = startDate;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", mission='" + mission + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", startDate=" + startDate +
                '}';
    }
}
