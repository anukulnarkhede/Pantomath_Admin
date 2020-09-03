package com.cproz.pantomath_admin.ManageTeachers;

import java.util.Date;

public class TeacherData {

    String Board, Email,Name, STD,Subject,User,profileURl,uid,Number,Address, SuspendedBy;
    Date OnlineTime;

    public TeacherData(String board, String email, String name, String STD, String subject, String user,
                       String profileURl, String uid, Date OnlineTime, String Number, String Address, String SuspendedBy) {
        Board = board;
        Email = email;
        Name = name;
        this.STD = STD;
        Subject = subject;
        User = user;
        this.profileURl = profileURl;
        this.uid = uid;
        this.OnlineTime = OnlineTime;
        this.Number = Number;
        this.Address = Address;
        this.SuspendedBy = SuspendedBy;
    }

    public String getBoard() {
        return Board;
    }

    public String getEmail() {
        return Email;
    }

    public String getNameTeacher() {
        return Name;
    }

    public String getSTD() {
        return STD;
    }

    public String getSubject() {
        return Subject;
    }

    public String getUser() {
        return User;
    }

    public String getProfileURl() {
        return profileURl;
    }

    public String getUid() {
        return uid;
    }

    public Date getOnlineTime() {
        return OnlineTime;
    }

    public String getNumber() {
        return Number;
    }

    public String getAddress() {
        return Address;
    }

    public String getSuspendedBy() {
        return SuspendedBy;
    }
}

