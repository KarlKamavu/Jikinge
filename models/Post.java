package com.roonit.jikinge.models;

import java.util.Date;

public class Post {

    private String image_url,user_id,message;
    private Date timestamp;

    public Post() {
    }

    public Post(String image_url, String user_id, String message, Date timestamp) {
        this.image_url = image_url;
        this.user_id = user_id;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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
}
