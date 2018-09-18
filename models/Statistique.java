package com.roonit.jikinge.models;

public class Statistique {
    private int mort;
    private int atteint;
    private int gueri;
    private int famille;
    private int suspect;

    public Statistique() {
    }

    public Statistique(int mort, int atteint, int gueri, int famille, int suspect) {
        this.mort = mort;
        this.atteint = atteint;
        this.gueri = gueri;
        this.famille = famille;
        this.suspect = suspect;
    }

    public int getMort() {
        return mort;
    }

    public void setMort(int mort) {
        this.mort = mort;
    }

    public int getAtteint() {
        return atteint;
    }

    public void setAtteint(int atteint) {
        this.atteint = atteint;
    }

    public int getGueri() {
        return gueri;
    }

    public void setGueri(int gueri) {
        this.gueri = gueri;
    }

    public int getFamille() {
        return famille;
    }

    public void setFamille(int famille) {
        this.famille = famille;
    }

    public int getSuspect() {
        return suspect;
    }

    public void setSuspect(int suspect) {
        this.suspect = suspect;
    }
}
