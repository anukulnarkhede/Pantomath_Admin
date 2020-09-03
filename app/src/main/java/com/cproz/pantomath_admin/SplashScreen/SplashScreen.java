package com.cproz.pantomath_admin.SplashScreen;



import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cproz.pantomath_admin.Home.Dashboard;
import com.cproz.pantomath_admin.Login.Login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {


    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    String email = user != null ? user.getEmail() : null;
    private DocumentReference ref = firebaseFirestore.collection("Users/Students/StudentsInfo/" ).document(String.valueOf(email));

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null){
            System.out.println(firebaseUser);
            startActivity(new Intent(this, Dashboard.class));

        }
        else {

            startActivity(new Intent(this, Login.class));

        }



    }
}
