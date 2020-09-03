package com.cproz.pantomath_admin.ManageTeachers;

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
import android.widget.Toast;

import com.cproz.pantomath_admin.AllDoubts.ShowAllDoubts;
import com.cproz.pantomath_admin.Home.Dashboard;
import com.cproz.pantomath_admin.ManageTeachers.AddTeacher.AddTeacher;
import com.cproz.pantomath_admin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherDetails extends AppCompatActivity {

    CircleImageView ProfilePic;
    TextView toolbar_name_teacher, StatusOFtracher,NoOftotalDoubts, TeacherNameDetails,EmailTeacher,SubjectTeacher, ClassTeacher
    ,BoardTeacher, PhoneTeacher, AddressTeacher, SuspendedBy, DoubtsSolved ;
    Button ShowAllDoubt, ActivateButt, BlockButt, EditInfo;
    Toolbar toolbar;
    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teacher_details);
        Initialization();




        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        ActivateButt.setVisibility(View.GONE);
        BlockButt.setVisibility(View.GONE);

        final Bundle bundle = getIntent().getExtras();
        assert bundle != null;


        SubjectTeacher.setText(bundle.getString("Subject"));
        NoOfDoubts(bundle);

//        Toast.makeText(this, Dashboard.USERDECESION, Toast.LENGTH_SHORT).show();





        if (Objects.equals(bundle.getString("profileImageUrl"), "")){
            ProfilePic.setImageResource(R.drawable.profile_defult);
        }else if (!Objects.equals(bundle.getString("profileImageUrl"), "")){
            Picasso.get().load(bundle.getString("profileImageUrl")).into(ProfilePic);
        }

        toolbar_name_teacher.setText(bundle.getString("Name"));

        StatusOFtracher.setText(bundle.getString("UserStatus"));


        if (Dashboard.USERDECESION.equals("Admin")){
            ActivateButt.setVisibility(View.GONE);
            EditInfo.setVisibility(View.GONE);
            BlockButt.setVisibility(View.GONE);
            StatusOFtracher.setTextColor(Color.parseColor("#009F37"));

            if (Objects.equals(bundle.getString("UserStatus"), "Suspended")){

                StatusOFtracher.setTextColor(Color.parseColor("#FF2829"));
                SuspendedBy.setVisibility(View.VISIBLE);
                SuspendedBy.setText("This account is suspended by " + bundle.getString("SuspendedBy"));

            }else if (Objects.equals(bundle.getString("UserStatus"), "Active")){
                StatusOFtracher.setTextColor(Color.parseColor("#009F37"));

            }
        }
        else if (Dashboard.USERDECESION.equals("SuperUser")){
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


        TeacherNameDetails.setText(bundle.getString("Name"));

        EmailTeacher.setText(bundle.getString("Email"));

//        ClassTeacher.setText(bundle.getString("Class"));

        if (Objects.equals(bundle.getString("Board"), "Both")){
            BoardTeacher.setText("SSC and CBSE");
        }else{
            BoardTeacher.setText(bundle.getString("Board"));
        }

        if (Objects.equals(bundle.getString("Class"), "Both")){
            ClassTeacher.setText("9th and 10th");
        }
        else{
            ClassTeacher.setText(bundle.getString("Class"));
        }




            ShowAllDoubt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TeacherDetails.this, ShowAllDoubts.class);
                    intent.putExtra("Email", bundle.getString("Email"));
                    intent.putExtra("User", "Teacher");

                    startActivity(intent);
                }
            });


            ActivateButt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Active("Active", bundle);


                }
            });

            BlockButt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    BlockTeacherPopUp blockTeacherPopUp = new BlockTeacherPopUp();
                    Bundle args = new Bundle();

                    args.putString("Email", bundle.getString("Email"));
                    blockTeacherPopUp.setArguments(args);
                    blockTeacherPopUp.show(getSupportFragmentManager(), "xsd");

                }
            });

            PhoneTeacher.setText(bundle.getString("Number"));



            AddressTeacher.setText(bundle.getString("Address"));




            EditInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(TeacherDetails.this, AddTeacher.class);
                    intent.putExtra("Edit", "Edit");
                    intent.putExtra("Email", bundle.getString("Email"));
                    startActivity(intent);
                }
            });



    }

    private void Initialization() {

        ProfilePic = findViewById(R.id.ProfilePic_Teacher);
        toolbar_name_teacher = findViewById(R.id.toolbar_name_teacher);
        StatusOFtracher = findViewById(R.id.StatusOFtracher);
        NoOftotalDoubts = findViewById(R.id.NoOftotalDoubts);
        TeacherNameDetails = findViewById(R.id.TeacherNameDetails);
        EmailTeacher = findViewById(R.id.EmailTeacher);
        SubjectTeacher = findViewById(R.id.SubjectTeacher);
        ClassTeacher = findViewById(R.id.ClassTeacher);
        BoardTeacher = findViewById(R.id.BoardTeacher);
        PhoneTeacher = findViewById(R.id.PhoneTeacher);
        AddressTeacher = findViewById(R.id.AddressTeacher);
        ShowAllDoubt = findViewById(R.id.ShowAllDoubtsx);
        toolbar = findViewById(R.id.DoubtDetailsToolBar);
        ActivateButt = findViewById(R.id.ActivateButt);
        BlockButt = findViewById(R.id.BolckButt);
        EditInfo = findViewById(R.id.EditButt);
        SuspendedBy = findViewById(R.id.Suspendedby);
        DoubtsSolved = findViewById(R.id.DoubtsSolved);

    }

    public void NoOfDoubts(Bundle bundle){
        final List<String> Listx = new ArrayList<>();
        firebaseFirestore.collection("Doubts").whereEqualTo("TeacherEmail", bundle.getString("Email"))
                .orderBy("DateTime", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot querySnapshot : Objects.requireNonNull(task.getResult())){





                    Listx.add(querySnapshot.getData().toString());







                }
                Listx.size();
                NoOftotalDoubts.setText(String.valueOf(Listx.size()));
                if (Listx.size()<=1){
                    DoubtsSolved.setText("Doubt solved.");
                }else {
                    Listx.size();
                    DoubtsSolved.setText("Doubts solved.");
                }

            }
        });
    }


    public  void Active(String action , Bundle bundle){
        firebaseFirestore.collection("Users/Teachers/Teacherinfo").document(Objects.requireNonNull(bundle.getString("Email"))).update("User",action)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        BlockButt.setVisibility(View.VISIBLE);
                        ActivateButt.setVisibility(View.GONE);
                        StatusOFtracher.setText("Active");
                        StatusOFtracher.setTextColor(Color.parseColor("#009F37"));
                        startActivity(new Intent(TeacherDetails.this, ManageTeachers.class));
                    }
                });
    }

}