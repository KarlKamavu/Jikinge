package com.roonit.jikinge.models;

import java.util.Date;

public class Suggestion {

    private String auteur;
    private String titre;
    private String suggestion;
    private Date  timestamp;

    public Suggestion() {
    }

    public Suggestion(String auteur, String titre, String suggestion, Date timestamp) {
        this.auteur = auteur;
        this.titre = titre;
        this.suggestion = suggestion;
        this.timestamp = timestamp;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(String suggestion) {
        this.suggestion = suggestion;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
