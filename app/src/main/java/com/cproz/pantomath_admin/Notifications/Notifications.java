package com.cproz.pantomath_admin.Notifications;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.cproz.pantomath_admin.About.About;
import com.cproz.pantomath_admin.AllDoubts.AllDoubts;
import com.cproz.pantomath_admin.Home.Dashboard;
import com.cproz.pantomath_admin.Feedback.Feedback;
import com.cproz.pantomath_admin.Home.HomeDoubtData;
import com.cproz.pantomath_admin.Login.LogoutPopUp;
import com.cproz.pantomath_admin.ManageStudents.ManageStudents;
import com.cproz.pantomath_admin.ManageTeachers.ManageTeachers;
import com.cproz.pantomath_admin.R;
import com.cproz.pantomath_admin.StudentsRequest.StudentRequest;
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

public class Notifications extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {



    Toolbar toolbar;
    SwipeRefreshLayout swipeRefreshLayout;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseFirestore db;
    private List<HomeDoubtData> DoubtList1;

    private HomeDoubtData homeDoubtData;

    TextView AllDoubtCount, NoOfSolved, NoOfUnsolved,noOfTeacher,noOfStudent,noOfRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications);
        db = FirebaseFirestore.getInstance();

        Initilization();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);


        DoubtList1 = new ArrayList<>();

        DoubtList1.clear();
        LoadNotifications();


        swipeRefreshLayout.setColorSchemeResources(R.color.blue);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                DoubtList1.clear();
                LoadNotifications();


                swipeRefreshLayout.setEnabled(false);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        navigationView.getMenu().findItem(R.id.Notifications).setChecked(true);


        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.ActivityDashboard:
                        Intent intent = new Intent(Notifications.this, Dashboard.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                    case R.id.AllDoubts:
                        intent = new Intent(Notifications.this, AllDoubts.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;

                    case R.id.ManageTeachers:
                        intent = new Intent(Notifications.this, ManageTeachers.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;

                    case R.id.ManageStudents:
                        intent = new Intent(Notifications.this, ManageStudents.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;

                    case R.id.StudentRequests:
                        intent = new Intent(Notifications.this, StudentRequest.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;


                    case R.id.Notifications:
                        navigationView.getMenu().findItem(R.id.Notifications).setChecked(true);
                        break;

                    case R.id.Settings:
                        intent = new Intent(Notifications.this, Feedback.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;


                    case R.id.Logout:

                        LogoutPopUp log = new LogoutPopUp();
                        log.show(getSupportFragmentManager(), "logout");
                        break;


                    case R.id.About:
                        intent = new Intent(Notifications.this, About.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);


                return true;
            }
        });






    }

    private void Initilization() {

        navigationView = findViewById(R.id.HomeNavigationDrawerNotifications);
        toolbar = findViewById(R.id.NotificationToolBar);
        drawerLayout = findViewById(R.id.drawerlayoutNotifications);
        recyclerView = findViewById(R.id.recyclerNotification);
        swipeRefreshLayout = findViewById(R.id.swipeRefNotification);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }




    private void LoadNotifications() {


        db.collection("Doubts").whereEqualTo("Status", "Solved").orderBy("DateTime", Query.Direction.DESCENDING)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot querySnapshot : Objects.requireNonNull(task.getResult())){
                    homeDoubtData = new HomeDoubtData(querySnapshot.getString("AnsPhotoUrl1"),
                            querySnapshot.getString("AnsPhotoUrl2"),
                            querySnapshot.getString("AnsText"),
                            querySnapshot.getString("AudioUrl"),
                            querySnapshot.getString("Board"),
                            querySnapshot.getString("Chapter"),
                            querySnapshot.getString("Email"),
                            querySnapshot.getString("FileUrl"),
                            querySnapshot.getString("Link"),
                            querySnapshot.getString("Name"),
                            querySnapshot.getString("Photo1url"),
                            querySnapshot.getString("Photo2url"),
                            querySnapshot.getString("ProfileImageURL"),
                            querySnapshot.getString("QText"),
                            querySnapshot.getString("STD"),
                            querySnapshot.getString("Status"),
                            querySnapshot.getString("Subject"),
                            querySnapshot.getString("Teacher"),
                            querySnapshot.getString("Uid")
                            , querySnapshot.getDate("DateTime"),
                            "",
                            querySnapshot.getDate("QuestionDate"));

                    DoubtList1.add(homeDoubtData);

                    NotificationAdapter notificationAdapter = new NotificationAdapter(Notifications.this,DoubtList1);
                    recyclerView.setItemViewCacheSize(40);

                    recyclerView.setAdapter(notificationAdapter);


                }
                swipeRefreshLayout.setEnabled(true);
            }
        });


    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(Notifications.this, Dashboard.class));
        super.onBackPressed();
    }
}