package com.roonit.jikinge.models;

import java.util.Date;

public class Post {
    private String titre;
    private String contenu;
    private String auteurID;
    private String image;
    private Date timestamp;

    public Post() {
    }

    public Post(String titre,String contenu, String auteurID, String image, Date timestamp) {
        this.titre = titre;
        this.contenu = contenu;
        this.auteurID = auteurID;
        this.image = image;
        this.timestamp = timestamp;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getAuteurID() {
        return auteurID;
    }

    public void setAuteurID(String auteurID) {
        this.auteurID = auteurID;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
