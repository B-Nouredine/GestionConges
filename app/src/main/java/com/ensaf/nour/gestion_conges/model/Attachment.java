package com.ensaf.nour.gestion_conges.model;

public class Attachment {

    private String name;
    private String uri;
    private String userID;
    private long dateUpload;

    public Attachment() {}

    public Attachment(String name, String uri, String userID, long dateUpload) {
        if (name.trim().equals(""))
            name = "No_name";
        this.name = name;
        this.uri = uri;
        this.userID = userID;
        this.dateUpload = dateUpload;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public long getDateUpload() {
        return dateUpload;
    }

    public void setDateUpload(long dateUpload) {
        this.dateUpload = dateUpload;
    }
}
