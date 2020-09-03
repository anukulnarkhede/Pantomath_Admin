package com.cproz.pantomath_admin.StudentsRequest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.cproz.pantomath_admin.About.About;
import com.cproz.pantomath_admin.AllDoubts.AllDoubts;
import com.cproz.pantomath_admin.Feedback.Feedback;
import com.cproz.pantomath_admin.Home.Dashboard;
import com.cproz.pantomath_admin.Login.LogoutPopUp;
import com.cproz.pantomath_admin.ManageStudents.ManageStudents;
import com.cproz.pantomath_admin.ManageStudents.StudentData;
import com.cproz.pantomath_admin.ManageTeachers.ManageTeachers;
import com.cproz.pantomath_admin.Notifications.Notifications;
import com.cproz.pantomath_admin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StudentRequest extends AppCompatActivity {

    RecyclerView recyclerView;

    NavigationView navigationView;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    FirebaseFirestore firebaseFirestore;
    List<StudentData> RequestList;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView NoOfPendingReq;
    StudentData studentData;
    FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_request);
        Initialization();
        dataFromFirestore();
        swipeRefreshLayout.setColorSchemeResources(R.color.blue);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);




        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.getMenu().findItem(R.id.StudentRequests).setChecked(true);
        recyclerView.setLayoutManager(gridLayoutManager);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                RequestList.clear();
                dataFromFirestore();
                PendingRequest();
                swipeRefreshLayout.setEnabled(false);
                swipeRefreshLayout.setRefreshing(false);
                //swipeRefreshLayout.setEnabled(true);
            }
        });



        db = FirebaseFirestore.getInstance();

        PendingRequest();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.ActivityDashboard:
                        Intent intent = new Intent(StudentRequest.this, Dashboard.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                    case R.id.AllDoubts:
                        intent = new Intent(StudentRequest.this, AllDoubts.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;

                    case R.id.ManageTeachers:
                        intent = new Intent(StudentRequest.this, ManageTeachers.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;

                    case R.id.ManageStudents:
                        intent = new Intent(StudentRequest.this, ManageStudents.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;

                    case R.id.StudentRequests:
                        navigationView.getMenu().findItem(R.id.StudentRequests).setChecked(true);

                        break;


                    case R.id.Notifications:
                        intent = new Intent(StudentRequest.this, Notifications.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;

                    case R.id.Settings:
                        intent = new Intent(StudentRequest.this, Feedback.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;

                    case R.id.Logout:

//                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//                        firebaseAuth.signOut();
//                        startActivity(new Intent(StudentRequest.this, Login.class));
                        LogoutPopUp log = new LogoutPopUp();
                        log.show(getSupportFragmentManager(), "logout");
                        break;


                    case R.id.About:
                        intent = new Intent(StudentRequest.this, About.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);


                return true;
            }
        });






    }

    private void dataFromFirestore() {
        firebaseFirestore.collection("Users/Students/StudentsInfo").orderBy("SignupTime", Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot querySnapshot : Objects.requireNonNull(task.getResult())){





                            if (Objects.equals(querySnapshot.getString("User"), "Not Verified")){
                                studentData = new StudentData(querySnapshot.getString("Address"),querySnapshot.getString("Board")
                                        ,querySnapshot.getString("Class"),querySnapshot.getString("Email"),
                                        querySnapshot.getString("Name"),querySnapshot.getString("Number")
                                        , querySnapshot.getString("User"),querySnapshot.getString("profileURL"),
                                        querySnapshot.getString("uid"), querySnapshot.getDate("SignupTime"), querySnapshot.getString("SuspendedBy")  );

                                RequestList.add(studentData);
                            }

                            RequestAdapter requestAdapter = new RequestAdapter(StudentRequest.this, RequestList,  getSupportFragmentManager());


                            recyclerView.setItemViewCacheSize(40);

                            recyclerView.setAdapter(requestAdapter);



                        }
                        swipeRefreshLayout.setEnabled(true);
                    }
                });

    }

    private void Initialization() {

        navigationView = findViewById(R.id.ReqNavigationDrawerReq);
        drawerLayout = findViewById(R.id.drawerlayoutreq);
        recyclerView = findViewById(R.id.RecyclerViewReq);
        toolbar = findViewById(R.id.DashToolBarReq);
        swipeRefreshLayout = findViewById(R.id.RequestRefresh);
        RequestList = new ArrayList<>();
        firebaseFirestore = FirebaseFirestore.getInstance();
        NoOfPendingReq = findViewById(R.id.NoOfPendingReq);



    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(StudentRequest.this, Dashboard.class));
        super.onBackPressed();
    }


    public void PendingRequest(){

        final List<String> Listx = new ArrayList<>();
        db.collection("Users").document("Students").collection("StudentsInfo").whereEqualTo("User", "Not Verified").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot querySnapshot : Objects.requireNonNull(task.getResult())){





                    Listx.add(querySnapshot.getData().toString());







                }
                Listx.size();
                NoOfPendingReq.setText(Listx.size() + " Pending");


            }
        });


    }




}