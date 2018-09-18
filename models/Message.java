package com.roonit.jikinge.models;

import java.util.Date;

public class Message {
    private String message;
    private Date timestamp;
    private String sender;

    public Message() {
    }

    public Message(String message, Date timestamp, String sender) {
        this.message = message;
        this.timestamp = timestamp;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
