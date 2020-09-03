package com.cproz.pantomath_admin.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.anychart.AnyChartView;
import com.cproz.pantomath_admin.About.About;
import com.cproz.pantomath_admin.AllDoubts.AllDoubts;
import com.cproz.pantomath_admin.Feedback.Feedback;
import com.cproz.pantomath_admin.Login.LogoutPopUp;
import com.cproz.pantomath_admin.ManageStudents.ManageStudents;
import com.cproz.pantomath_admin.ManageTeachers.ManageTeachers;
import com.cproz.pantomath_admin.Notifications.Notifications;
import com.cproz.pantomath_admin.R;
import com.cproz.pantomath_admin.StudentsRequest.StudentRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Dashboard extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Button ShowAllDoubts, ManageTeachers, ManageStudents, ShowAllRequestes;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseFirestore db;

    public static String USERDECESION = Dashboard.USERDECESION;

    ConstraintLayout constFullscreen, ReportedCard;
    Button ProfileCross, ShowAllReported;
    LinearLayout FullScreenBgButt;
    TextView userStatuz, NameText, InstituteTitle, EmailPopup, noOfReports;
    CircleImageView ProfilePopup;

    TextView AllDoubtCount, NoOfSolved, NoOfUnsolved,noOfTeacher,noOfStudent,noOfRequest,NoOfDeleted;

    AnyChartView paiChart;
    SwipeRefreshLayout swipeRefreshLayout;

    CircleImageView ProfilePictureDash;


    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    String email = Objects.requireNonNull(user).getEmail();

    private DocumentReference ref = firebaseFirestore.collection("Users/Admin/AdminInfo/" ).document(String.valueOf(email));





    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        db = FirebaseFirestore.getInstance();





        Initialization();
        NumberOftotalDoubts();
        NumberOfSolvedDoubts();
        NumberOfUnsolvedDoubts();
        NumberOfTeachers();
        NumberOfStudents();
        PendingRequest();
        NoOfDeletedReq();
        ReportedDoubt();



        constFullscreen.setVisibility(View.GONE);


        ShowAllReported.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(0,0);
                startActivity(new Intent(Dashboard.this, ReportedDoubts.class));
            }
        });



        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {

                String ProfileUrl = documentSnapshot.getString("ProfileImage");

                NameText.setText(documentSnapshot.getString("Name"));
                InstituteTitle.setText(documentSnapshot.getString("Institute"));
                EmailPopup.setText(documentSnapshot.getString("Email"));



                assert ProfileUrl != null;
                if (ProfileUrl == null){
                    Toast.makeText(Dashboard.this, "No profile photo", Toast.LENGTH_SHORT).show();
                }else
                if (ProfileUrl.equals("")){
                    ProfilePictureDash.setImageResource(R.drawable.profile_defult);
                    ProfilePopup.setImageResource(R.drawable.profile_defult);

                }else if (!ProfileUrl.equals("")){
                    Picasso.get().load(ProfileUrl).into(ProfilePictureDash);
                    Picasso.get().load(ProfileUrl).into(ProfilePopup);
                }




                USERDECESION = documentSnapshot.getString("User");


                userStatuz.setText(USERDECESION);




            }
        });


        ProfilePictureDash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               constFullscreen.setVisibility(View.VISIBLE);
                FullScreenBgButt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        constFullscreen.setVisibility(View.GONE);
                    }
                });
            }
        });

        ProfileCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                constFullscreen.setVisibility(View.VISIBLE);
            }
        });


        swipeRefreshLayout.setColorSchemeResources(R.color.blue);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                overridePendingTransition(0, 0);
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });


        navigationView.getMenu().findItem(R.id.ActivityDashboard).setChecked(true);


        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

         drawerLayout.addDrawerListener(toggle);
         toggle.syncState();







         ManageTeachers.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(Dashboard.this, com.cproz.pantomath_admin.ManageTeachers.ManageTeachers.class));
             }
         });

         ManageStudents.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(Dashboard.this, com.cproz.pantomath_admin.ManageStudents.ManageStudents.class));
             }
         });

         ShowAllDoubts.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(Dashboard.this, AllDoubts.class));
             }
         });

         ShowAllRequestes.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(Dashboard.this, StudentRequest.class));
             }
         });



//        navigationView.setCheckedItem(R.id.ActivityDashboard);


         navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                 switch (menuItem.getItemId()){
                     case R.id.ActivityDashboard:
                         navigationView.getMenu().findItem(R.id.ActivityDashboard).setChecked(true);
                         break;
                     case R.id.AllDoubts:
                         Intent intent;
                         intent = new Intent(Dashboard.this, AllDoubts.class);
                         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                         startActivity(intent);
                         break;

                     case R.id.ManageTeachers:
                         intent = new Intent(Dashboard.this, ManageTeachers.class);
                         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                         startActivity(intent);
                         break;

                     case R.id.ManageStudents:
                         intent = new Intent(Dashboard.this, ManageStudents.class);
                         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                         startActivity(intent);
                         break;

                     case R.id.StudentRequests:
                         intent = new Intent(Dashboard.this, StudentRequest.class);
                         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                         startActivity(intent);
                         break;


                     case R.id.Notifications:
                         intent = new Intent(Dashboard.this, Notifications.class);
                         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                         startActivity(intent);
                         break;

                     case R.id.Settings:
                         intent = new Intent(Dashboard.this, Feedback.class);
                         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                         startActivity(intent);
                         break;


                     case R.id.Logout:

//                         FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//                         firebaseAuth.signOut();
//                         startActivity(new Intent(Dashboard.this, Login.class));
                            LogoutPopUp log = new LogoutPopUp();
                            log.show(getSupportFragmentManager(), "logout");
                         break;



                     case R.id.About:
                         intent = new Intent(Dashboard.this, About.class);
                         intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                         startActivity(intent);
                         break;
                 }
                 drawerLayout.closeDrawer(GravityCompat.START);


                 return true;
             }
         });



    }

    private void Initialization() {

        toolbar = findViewById(R.id.DashToolBar);
        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.HomeNavigationDrawer);
        ShowAllDoubts = findViewById(R.id.showAllDoubts);
        ManageStudents = findViewById(R.id.manageStudent);
        ManageTeachers = findViewById(R.id.manageTeacher);
        AllDoubtCount = findViewById(R.id.DoubtCountHome);
        ShowAllRequestes = findViewById(R.id.ShowAllRequest);
        NoOfSolved = findViewById(R.id.noOfsolved);
        NoOfUnsolved = findViewById(R.id.noOfUnsolved);
        noOfTeacher = findViewById(R.id.noOfTeacher);
        noOfStudent = findViewById(R.id.noOfStudent);
        noOfRequest = findViewById(R.id.noOfRequest);
        swipeRefreshLayout = findViewById(R.id.swipeDash);
        NoOfDeleted = findViewById(R.id.NoOfDeleted);
        ProfilePictureDash = findViewById(R.id.ProfilePictureDash);
        FullScreenBgButt = findViewById(R.id.FullScreenBgButt);
        ProfileCross = findViewById(R.id.crossProf);
        userStatuz = findViewById(R.id.userStatuz);
        NameText = findViewById(R.id.NameText);
        InstituteTitle = findViewById(R.id.InstituteTitle);
        EmailPopup = findViewById(R.id.EmailPopup);
        ProfilePopup = findViewById(R.id.ProfilePopup);
        constFullscreen = findViewById(R.id.constFullscreen);
        noOfReports = findViewById(R.id.noOfReports);
        ShowAllReported = findViewById(R.id.ShowAllReported);
        ReportedCard = findViewById(R.id.ReportedCard);

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);

        }else{
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        return false;
    }



//    public void noOfAllDoubts(){
//        db.collection("Doubts").document().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                for (QueryDocumentSnapshot querySnapshot : Objects.requireNonNull(task.getResult())){}
//            }
//        });
//    }

    public void NumberOftotalDoubts(){

        final List<String> Listx = new ArrayList<>();
        db.collection("Doubts").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot querySnapshot : Objects.requireNonNull(task.getResult())){





                Listx.add(querySnapshot.getData().toString());







                }
                Listx.size();
                AllDoubtCount.setText(String.valueOf(Listx.size()));

            }
        });


    }



    public void NumberOfSolvedDoubts(){

        final List<String> Listx = new ArrayList<>();
        final List<String> Listz = new ArrayList<>();
        db.collection("Doubts").whereEqualTo("Status", "Solved").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot querySnapshot : Objects.requireNonNull(task.getResult())){





                    Listx.add(querySnapshot.getData().toString());








                }




                Listx.size();
                NoOfSolved.setText(String.valueOf(Listx.size()));

            }
        });


    }




    public void NumberOfUnsolvedDoubts(){

        final List<String> Listx = new ArrayList<>();
        db.collection("Doubts").whereEqualTo("Status", "Unsolved").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot querySnapshot : Objects.requireNonNull(task.getResult())){





                    Listx.add(querySnapshot.getData().toString());







                }
                Listx.size();
                NoOfUnsolved.setText(String.valueOf(Listx.size()));

            }
        });


    }



    public void NumberOfTeachers(){

        final List<String> Listx = new ArrayList<>();
        db.collection("Users").document("Teachers").collection("Teacherinfo").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot querySnapshot : Objects.requireNonNull(task.getResult())){





                    Listx.add(querySnapshot.getData().toString());







                }
                Listx.size();
                noOfTeacher.setText(String.valueOf(Listx.size()));

            }
        });


    }



    public void NumberOfStudents(){

        final List<String> Listx = new ArrayList<>();
        db.collection("Users").document("Students").collection("StudentsInfo").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot querySnapshot : Objects.requireNonNull(task.getResult())){




                    if (!Objects.equals(querySnapshot.getString("User"), "Not Verified")&&!Objects.equals(querySnapshot.getString("User"), "Deleted")){
                        Listx.add(querySnapshot.getData().toString());
                    }


                }
                Listx.size();
                noOfStudent.setText(String.valueOf(Listx.size()));

            }
        });


    }



    public void PendingRequest(){

        final List<String> Listx = new ArrayList<>();
        db.collection("Users").document("Students").collection("StudentsInfo").whereEqualTo("User", "Not Verified").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot querySnapshot : Objects.requireNonNull(task.getResult())){





                    Listx.add(querySnapshot.getData().toString());







                }
                Listx.size();
                noOfRequest.setText(String.valueOf(Listx.size()));

            }
        });


    }



    public void NoOfDeletedReq(){

        final List<String> Listx = new ArrayList<>();
        db.collection("Users").document("Students").collection("StudentsInfo").whereEqualTo("User", "Deleted").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot querySnapshot : Objects.requireNonNull(task.getResult())){





                    Listx.add(querySnapshot.getData().toString());







                }
                Listx.size();

                if (Listx.size()<=1){
                    NoOfDeleted.setText(Listx.size() + " request deleted " );
                }
                else {
                    Listx.size();
                    NoOfDeleted.setText(Listx.size() + " requests deleted " );
                }


            }
        });


    }


    public void ReportedDoubt(){
        final List<String> Listx = new ArrayList<>();
        db.collection("Doubts").whereEqualTo("Status", "Reported").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot querySnapshot : Objects.requireNonNull(task.getResult())){





                    Listx.add(querySnapshot.getData().toString());







                }
                Listx.size();
                if (Listx.size() == 0){
                    ReportedCard.setVisibility(View.GONE);
                }else{
                    ReportedCard.setVisibility(View.VISIBLE);
                }

                noOfReports.setText(String.valueOf(Listx.size()));

            }
        });
    }



}