package com.cproz.pantomath_admin.ManageStudents;

import androidx.annotation.NonNull;

import java.util.Date;

public class StudentData {

    String Address, Board, Class, Email, Name, Number, User, profileURL,uid, SuspendedBy;
    Date SignupTime;


    public StudentData(String address,
                       String board,
                       String aClass,
                       String email,
                       String name,
                       String number,
                       String user,
                       String profileURL,
                       String uid,
                       Date signupTime,
                       String SuspendedBy) {


        Address = address;
        Board = board;
        Class = aClass;
        Email = email;
        Name = name;
        Number = number;
        User = user;
        this.profileURL = profileURL;
        this.uid = uid;
        SignupTime = signupTime;
        this.SuspendedBy = SuspendedBy;

    }


    public String getAddress() {
        return Address;
    }

    public String getBoardx() {
        return Board;
    }

    public String getSuspendedBy() {
        return SuspendedBy;
    }

    public String getClassx() {
        return Class;
    }

    public String getEmailx() {
        return Email;
    }

    public String getNamex() {
        return Name;
    }

    public String getNumber() {
        return Number;
    }

    public String getUserx() {
        return User;
    }

    public String getProfileURL() {
        return profileURL;
    }

    public String getUidx() {
        return uid;
    }

    public Date getSignupTime() {
        return SignupTime;
    }
}
