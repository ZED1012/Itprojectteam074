package com.example.myapplication;

import android.app.Application;

public class ClientInfo extends Application {
    String firstName;
    String lastName;
    String email;
    String postCode;
    String group;
    boolean isGroup;
    boolean isClub;
    boolean isOrganisation;
    boolean isLeader;
    public void onCreate() {
        firstName = "";
        lastName = "";
        email = "";
        postCode = "";
        group = "";
        isGroup = false;
        isClub = false;
        isOrganisation = false;
        isLeader = false;
        super.onCreate();

    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public String getFirstName(){
        return this.firstName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public void setPostCode(String postCode){
        this.postCode = postCode;
    }
    public void setGroup(String group){
        this.group = group;
    }
    public void setClub(boolean isClub){
        this.isClub = isClub;
    }
    public void setOrganisation(boolean isOrganisation){
        this.isOrganisation = isOrganisation;
    }
    public void setLeader(boolean isLeader){
        this.isLeader = isLeader;
    }


}
