package com.ensaf.nour.gestion_conges.employee.leaveReq;

public class LeaveRequestUnit {

    private String startDate;
    private String endDate;
    private boolean isAnswered;
    private boolean isAccepted;

    public LeaveRequestUnit(String startDate, String endDate, boolean isAnswered, boolean isAccepted) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.isAnswered = isAnswered;
        this.isAccepted = isAccepted;
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
}
