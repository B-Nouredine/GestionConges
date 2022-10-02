package com.ensaf.nour.gestion_conges.messages;

public class MessagesUnit {

    private String employeeID;
    private String employeeName;
    private String employeeEmail;
    private  String lastMessage;
    private  String profilePic;
    private String employeeMission;

    public MessagesUnit(String employeeID, String employeeName, String employeeEmail, String lastMessage, String profilePic, String employeeMission) {
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.employeeEmail = employeeEmail;
        this.lastMessage = lastMessage;
        this.profilePic = profilePic;
        this.employeeMission = employeeMission;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getEmployeeMission() {
        return employeeMission;
    }

    public void setEmployeeMission(String employeeMission) {
        this.employeeMission = employeeMission;
    }
}
