package com.ensaf.nour.gestion_conges.employee.leaveReq;

public class LeaveRequestUnit {

    private String startDate;
    private String endDate;
    private boolean isAnswered;
    private boolean isAccepted;
    private String employeeName;
    private String leaveID;

    public LeaveRequestUnit(String startDate, String endDate, boolean isAnswered, boolean isAccepted) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.isAnswered = isAnswered;
        this.isAccepted = isAccepted;
    }

    public LeaveRequestUnit(String startDate, String endDate, boolean isAnswered, boolean isAccepted, String employeeName) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.isAnswered = isAnswered;
        this.isAccepted = isAccepted;
        this.employeeName = employeeName;
    }

    public LeaveRequestUnit(String startDate, String endDate, boolean isAnswered, boolean isAccepted, String employeeName, String leaveID) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.isAnswered = isAnswered;
        this.isAccepted = isAccepted;
        this.employeeName = employeeName;
        this.leaveID = leaveID;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }

    public boolean isAccepted() {
        return isAccepted;
    }

    public void setAccepted(boolean accepted) {
        isAccepted = accepted;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getLeaveID() {
        return leaveID;
    }

    public void setLeaveID(String leaveID) {
        this.leaveID = leaveID;
    }
}
