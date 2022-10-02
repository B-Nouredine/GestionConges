package com.ensaf.nour.gestion_conges.model;

import java.util.Date;

public class Message {

    private String senderID;
    private String receiverID;
    private String  title;
    private String  message;
    private long dateMessage;

    public Message() {}

    public Message(String senderID, String receiverID, String title, String message, long  dateMessage) {
        this.senderID = senderID;
        this.receiverID = receiverID;
        this.title = title;
        this.message = message;
        this.dateMessage = dateMessage;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
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

    public long getDateMessage() {
        return dateMessage;
    }

    public void setDateMessage(long  dateMessage) {
        this.dateMessage = dateMessage;
    }

    @Override
    public String toString() {
        return "Message{" +
                ", receiver=" + receiverID +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", dateMessage='" + dateMessage + '\'' +
                '}';
    }
}
