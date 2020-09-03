package com.cproz.pantomath_admin.ManageTeachers.AddTeacher;


import java.util.Date;

public class SignupInfoCarrier {

    public String Name, Email, Number, Address, User, Board, STD, uid, profileURl, Subject;
    public Date SignupTime;

    public SignupInfoCarrier(String name, String email, String number, String address, String user, String Board, String STD, String uid, String profileURl
            , Date SignupTime, String Subject) {
        this.Name = name;
        this.Email = email;
        this.Number = number;
        this.Address = address;
        this.STD = STD;
        this.Board = Board;
        this.User = user;
        this.uid = uid;
        this.profileURl = profileURl;
        this.SignupTime = SignupTime;
        this.Subject = Subject;

    }

}
