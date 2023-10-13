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
    boolean isPeak;
    String peakSpecify;
    int roleId;



    public String getPeakSpecify() {
        return peakSpecify;
    }

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
        roleId = 0;
        isPeak = false;
        peakSpecify = "";
        super.onCreate();

    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public void setGroup(String group) {
        this.group = group;
    }
    public void setIsGroup(boolean isGroup){
        this.isGroup = isGroup;
    }
    public void setClub(boolean isClub) {
        this.isClub = isClub;
    }

    public void setOrganisation(boolean isOrganisation) {
        this.isOrganisation = isOrganisation;
    }

    public void setLeader(boolean isLeader) {
        this.isLeader = isLeader;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getGroup() {
        return group;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public boolean isClub() {
        return isClub;
    }

    public boolean isOrganisation() {
        return isOrganisation;
    }

    public boolean isLeader() {
        return isLeader;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setPeak(boolean peak) {
        isPeak = peak;
    }

    public void setSpecify(String peakSpecify) {
        this.peakSpecify = peakSpecify;
    }

    public boolean isPeak() {
        return isPeak;
    }


}
