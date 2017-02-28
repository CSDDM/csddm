package com.example.csddm.entity;

/**
 * Created by dell on 2017/2/25.
 */

public class Song {
    private String songid;
    private String songname;
    private double time;
    private String style;
    private String label;

    public String getSongid() {
        return songid;
    }

    public void setSongid(String songid) {
        this.songid = songid;
    }

    public String getSongname() {
        return songname;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String[] getLabel() {
        return label.split(",");
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
