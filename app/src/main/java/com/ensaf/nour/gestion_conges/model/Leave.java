package com.ensaf.nour.gestion_conges.model;

import java.util.Date;

public class Leave {

    private String id;
    private String employeeID;
    private Date start;
    private Date end;
    private  int  TOTAL_DAYS;
    private boolean answered = false;
    private boolean accepted = false;

    public Leave() {}

    public Leave(String employeeID, Date start, Date end, int TOTAL_DAYS) {
        this.employeeID = employeeID;
        this.start = start;
        this.end = end;
        this.TOTAL_DAYS = TOTAL_DAYS;
    }

    public Leave(String employeeID, Date start, Date end, int TOTAL_DAYS, boolean isAnswered, boolean isAccepted) {
        this.employeeID = employeeID;
        this.start = start;
        this.end = end;
        this.TOTAL_DAYS = TOTAL_DAYS;
        this.answered = isAnswered;
        this.accepted = isAccepted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public int getTOTAL_DAYS() {
        return TOTAL_DAYS;
    }

    public void setTOTAL_DAYS(int TOTAL_DAYS) {
        this.TOTAL_DAYS = TOTAL_DAYS;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public String toString() {
        return "Leave{" +
                "employee=" + employeeID +
                ", start=" + start +
                ", end=" + end +
                ", TOTAL_DAYS=" + TOTAL_DAYS +
                ", isAnswered=" + answered +
                ", isAccepted=" + accepted +
                '}';
    }
}
