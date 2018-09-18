package com.roonit.jikinge.models;

import java.util.Date;

public class Conseil {
    private String titre;
    private String contenu;
    private Date timestamp;

    public Conseil() {
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Conseil(String titre, String contenu, Date timestamp) {
        this.titre = titre;
        this.contenu = contenu;
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
}
