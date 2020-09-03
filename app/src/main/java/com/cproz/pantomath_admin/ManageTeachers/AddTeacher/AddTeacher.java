package com.cproz.pantomath_admin.ManageTeachers.AddTeacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.Toast;

import com.cproz.pantomath_admin.ManageTeachers.ManageTeachers;
import com.cproz.pantomath_admin.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class AddTeacher extends AppCompatActivity implements Spinner.OnItemSelectedListener{


    public static final String NUMBER = AddTeacher.NUMBER ;
    public static String SUBJECT = AddTeacher.SUBJECT;
    public static String BOARD = AddTeacher.BOARD;
    public static String CLASS = AddTeacher.CLASS;
    CheckBox Class9th, Class10th,SSC,CBSE,ICSE;
    Button next;
    Toolbar toolbar;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();


    Spinner spinner;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_teacher);
        Initialization();


        final Bundle bundle = getIntent().getExtras();

        assert bundle != null;
        //Toast.makeText(this, bundle.getString("Edit"), Toast.LENGTH_SHORT).show();


        if (Objects.equals(bundle.getString("Edit"), "Edit")){

            next.setText("Update Info");


        }

        if (next.getText().equals("Update Info")){

            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BoardAndClassSelection();
                    if (AddTeacher.SUBJECT.equals("")){
                        Toast.makeText(AddTeacher.this, "Please select subject", Toast.LENGTH_SHORT).show();
                    }else if (AddTeacher.CLASS == null || AddTeacher.BOARD == null){
                        Toast.makeText(AddTeacher.this, "Please select class, board & subject", Toast.LENGTH_SHORT).show();
                    }

                    else{

                        Toast.makeText(AddTeacher.this, AddTeacher.CLASS, Toast.LENGTH_SHORT).show();
                        UpdateAccount(bundle);
                    }
                }
            });

        }else{
            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    BoardAndClassSelection();
                    if (AddTeacher.SUBJECT.equals("")){
                        Toast.makeText(AddTeacher.this, "Please select subject", Toast.LENGTH_SHORT).show();
                    }else if (AddTeacher.CLASS == null || AddTeacher.BOARD == null){
                        Toast.makeText(AddTeacher.this, "Please select class, board & subject", Toast.LENGTH_SHORT).show();
                    }

                    else{

                        Intent intent = new Intent(AddTeacher.this, CreatAccTeacher.class);
                        intent.putExtra("Board", AddTeacher.BOARD);
                        intent.putExtra("Class", AddTeacher.CLASS);
                        intent.putExtra("Subject", AddTeacher.SUBJECT);
                        startActivity(intent);
                    }
                }
            });
        }



        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        final String[] Subjects = {"Algebra","Geometry", "Physics", "Chemistry", "Biology", "History", "Geography", "Languages"};

        final ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(Subjects));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.style_spinner,arrayList);
        spinner.setAdapter(arrayAdapter);






        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                AddTeacher.SUBJECT = Subjects[position];
                //Toast.makeText(AddTeacher.this, SUBJECT, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                AddTeacher.SUBJECT = "";
            }
        });







    }

    private void Initialization() {

        spinner = findViewById(R.id.SubjectSelection);
        Class9th = findViewById(R.id.Class9th);
        Class10th = findViewById(R.id.Class10th);
        SSC = findViewById(R.id.SSC);
        CBSE = findViewById(R.id.CBSE);
        ICSE = findViewById(R.id.ICSE);
        next = findViewById(R.id.NextAdd);
        toolbar = findViewById(R.id.AddTeacherToolBar);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void BoardAndClassSelection(){

        if (!Class10th.isChecked()&&!Class9th.isChecked()){
            Toast.makeText(this, "Select at least 1 Class", Toast.LENGTH_SHORT).show();
        }else if (Class9th.isChecked()&&Class10th.isChecked()){
            AddTeacher.CLASS = "9th & 10th";
        }else if (Class9th.isChecked()){
            AddTeacher.CLASS = "9th";
        }else if (Class10th.isChecked()){
            AddTeacher.CLASS = "10th";
        }

        if (!CBSE.isChecked()&&!SSC.isChecked()&&!ICSE.isChecked()){
            Toast.makeText(this, "Select at least 1 Board", Toast.LENGTH_SHORT).show();
        }else if (CBSE.isChecked() && SSC.isChecked() && ICSE.isChecked()){
            AddTeacher.BOARD = "SSC, CBSE & ICSE";
        }else if (SSC.isChecked()&&CBSE.isChecked()){
            AddTeacher.BOARD = "SSC & CBSE";
        }else if (CBSE.isChecked()&&ICSE.isChecked()){
            AddTeacher.BOARD = "CBSE & ICSE";
        }else if (SSC.isChecked()&&ICSE.isChecked()){
            AddTeacher.BOARD = "SSC & ICSE";
        }else if (SSC.isChecked()){
            AddTeacher.BOARD = "SSC";
        }else if (CBSE.isChecked()){
            AddTeacher.BOARD = "CBSE";
        }else if (ICSE.isChecked()){
            AddTeacher.BOARD = "ICSE";
        }


    }


    public void UpdateAccount(Bundle bundle){
        firebaseFirestore.collection("Users/Teachers/Teacherinfo/")
                .document(Objects.requireNonNull(bundle.getString("Email"))).update("STD", AddTeacher.CLASS,
                "Board", AddTeacher.BOARD, "Subject", AddTeacher.SUBJECT).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                startActivity(new Intent(AddTeacher.this, ManageTeachers.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddTeacher.this, "Update Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}