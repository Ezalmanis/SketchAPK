package com.example.sketchapk;

public class Dbimage {
    public String filename;
    public String tags[];

    public Dbimage(String s) {
        String twofields[] = s.split(" ");
        this.filename = twofields[0];
        twofields[1] = twofields[1].trim();
        tags = twofields[1].split(",");
    }
}