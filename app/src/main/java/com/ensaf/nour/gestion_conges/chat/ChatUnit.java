package com.ensaf.nour.gestion_conges.chat;

public class ChatUnit {

    String title;
    String message;
    long dateMs;
    String userSource;

    public ChatUnit(String userSource, String title, String message, long dateMs) {
        this.title = title;
        this.message = message;
        this.dateMs = dateMs;
        this.userSource = userSource;
    }

    public String getUserSource() {
        return userSource;
    }

    public void setUserSource(String userSource) {
        this.userSource = userSource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getDateMs() {
        return dateMs;
    }

    public void setDateMs(long dateMs) {
        this.dateMs = dateMs;
    }
}
