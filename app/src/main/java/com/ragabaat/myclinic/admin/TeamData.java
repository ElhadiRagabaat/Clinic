package com.ragabaat.myclinic.admin;

public class TeamData {

    private String name,bio,image,key;

    public TeamData() {
    }

    public TeamData(String name, String bio, String image, String key) {
        this.name = name;
        this.bio = bio;
        this.image = image;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
