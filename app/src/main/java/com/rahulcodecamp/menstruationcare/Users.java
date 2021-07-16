package com.rahulcodecamp.menstruationcare;

public class Users {  // we need this model class to crate database having these below mentioned field

    String uid;
    String name;
    String email;
    String imageUri;

    public Users() {

    }

    public Users(String uid, String name, String email, String imageUri) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.imageUri = imageUri;
    }



    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
