package com.cproz.pantomath_admin.ManageStudents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cproz.pantomath_admin.AllDoubts.AllDoubts;
import com.cproz.pantomath_admin.AllDoubts.ShowAllDoubts;
import com.cproz.pantomath_admin.Home.Dashboard;
import com.cproz.pantomath_admin.ManageTeachers.BlockTeacherPopUp;
import com.cproz.pantomath_admin.ManageTeachers.ManageTeachers;
import com.cproz.pantomath_admin.ManageTeachers.TeacherDetails;
import com.cproz.pantomath_admin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentDetails extends AppCompatActivity {

    CircleImageView ProfilePic;
    TextView toolbar_name_Student, StatusOFtracher,NoOftotalDoubts, StudentNameDetails,Email, ClassSD
            ,Board, Phone, Address, SuspendedBy, DoubtsSolved ;
    Button ShowAllDoubt, ActivateButt, BlockButt;
    Toolbar toolbar;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_details);
        Initialization();
        ActivateButt.setVisibility(View.GONE);
        BlockButt.setVisibility(View.GONE);

        final Bundle bundle = getIntent().getExtras();
        assert bundle != null;

        //SubjectTeacher.setText(bundle.getString("Subject"));
        NoOfDoubts(bundle);

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        if (Objects.equals(bundle.getString("profileImageUrl"), "")){
            ProfilePic.setImageResource(R.drawable.profile_defult);
        }else if (!Objects.equals(bundle.getString("profileImageUrl"), "")){
            Picasso.get().load(bundle.getString("profileImageUrl")).into(ProfilePic);
        }

        toolbar_name_Student.setText(bundle.getString("UserName"));
        StatusOFtracher.setText(bundle.getString("UserStatus"));

        if (Dashboard.USERDECESION.equals("Admin")){
            BlockButt.setVisibility(View.GONE);
            ActivateButt.setVisibility(View.GONE);

            StatusOFtracher.setTextColor(Color.parseColor("#FF2829"));
            if (Objects.equals(bundle.getString("UserStatus"), "Suspended")){

                StatusOFtracher.setTextColor(Color.parseColor("#FF2829"));
                SuspendedBy.setVisibility(View.VISIBLE);
                SuspendedBy.setText("This account is suspended by " + bundle.getString("SuspendedBy"));

            }else if (Objects.equals(bundle.getString("UserStatus"), "Active")){
                StatusOFtracher.setTextColor(Color.parseColor("#009F37"));

            }
        }else if (Dashboard.USERDECESION.equals("SuperUser")) {
            if (Objects.equals(bundle.getString("UserStatus"), "Suspended")) {
                ActivateButt.setVisibility(View.VISIBLE);
                StatusOFtracher.setTextColor(Color.parseColor("#FF2829"));
                SuspendedBy.setVisibility(View.VISIBLE);
                SuspendedBy.setText("This account is suspended by " + bundle.getString("SuspendedBy"));

            } else if (Objects.equals(bundle.getString("UserStatus"), "Active")) {
                StatusOFtracher.setTextColor(Color.parseColor("#009F37"));
                BlockButt.setVisibility(View.VISIBLE);
            }
        }


        ActivateButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Active("Active", bundle);


            }
        });


        BlockButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BlockStudentPopUp blockStudentPopUp = new BlockStudentPopUp();
                Bundle args = new Bundle();

                args.putString("Email", bundle.getString("Email"));
                blockStudentPopUp.setArguments(args);
                blockStudentPopUp.show(getSupportFragmentManager(), "xsd");

            }
        });


        StudentNameDetails.setText(bundle.getString("UserName"));

        Email.setText(bundle.getString("Email"));

        ClassSD.setText(bundle.getString("Class"));

        ShowAllDoubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentDetails.this, ShowAllDoubts.class);
                intent.putExtra("Email", bundle.getString("Email"));
                intent.putExtra("User", "Student");

                startActivity(intent);
            }
        });



        Phone.setText(bundle.getString("Number"));



        Board.setText(bundle.getString("Board"));

        Address.setText(bundle.getString("Address"));


    }


    private void Initialization() {

        ProfilePic = findViewById(R.id.ProfilePic_Student);
        toolbar_name_Student = findViewById(R.id.toolbar_name_Student);
        StatusOFtracher = findViewById(R.id.StatusOFStudent);
        NoOftotalDoubts = findViewById(R.id.NoOftotalDoubtsSD);
        StudentNameDetails = findViewById(R.id.StudentNameDetails);
        Email = findViewById(R.id.EmailStudent);
        ClassSD = findViewById(R.id.ClassTeacherSD);
        Board = findViewById(R.id.BoardSD);
        Phone = findViewById(R.id.PhoneSD);
        Address = findViewById(R.id.AddressSD);
        ShowAllDoubt = findViewById(R.id.ShowAllDoubtsSD);
        toolbar = findViewById(R.id.StudentDetailToolBar);
        ActivateButt = findViewById(R.id.ActivateButtSD);
        BlockButt = findViewById(R.id.BolckButtSD);

        SuspendedBy = findViewById(R.id.SuspendedbySD);
        DoubtsSolved = findViewById(R.id.DoubtsSolvedSD);

    }




    public void NoOfDoubts(Bundle bundle){
        final List<String> Listx = new ArrayList<>();
        firebaseFirestore.collection("Doubts").whereEqualTo("Email", bundle.getString("Email"))
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot querySnapshot : Objects.requireNonNull(task.getResult())){





                    Listx.add(querySnapshot.getData().toString());







                }
                Listx.size();
                NoOftotalDoubts.setText(String.valueOf(Listx.size()));
                if (Listx.size()<=1){
                    DoubtsSolved.setText("Doubt asked");
                }else {
                    Listx.size();
                    DoubtsSolved.setText("Doubts asked");
                }

            }
        });
    }



    public  void Active(String action , Bundle bundle){
        firebaseFirestore.collection("Users/Students/StudentsInfo").document(Objects.requireNonNull(bundle.getString("Email"))).update("User",action)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        BlockButt.setVisibility(View.VISIBLE);
                        ActivateButt.setVisibility(View.GONE);
                        StatusOFtracher.setText("Active");
                        StatusOFtracher.setTextColor(Color.parseColor("#009F37"));
                        startActivity(new Intent(StudentDetails.this, ManageStudents.class));
                    }
                });
    }


}