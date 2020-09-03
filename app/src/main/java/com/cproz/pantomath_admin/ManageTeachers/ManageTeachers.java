package com.cproz.pantomath_admin.ManageTeachers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.cproz.pantomath_admin.About.About;
import com.cproz.pantomath_admin.AllDoubts.AllDoubts;
import com.cproz.pantomath_admin.Feedback.Feedback;
import com.cproz.pantomath_admin.Home.Dashboard;
import com.cproz.pantomath_admin.Login.LogoutPopUp;
import com.cproz.pantomath_admin.ManageStudents.ManageStudents;
import com.cproz.pantomath_admin.Notifications.Notifications;
import com.cproz.pantomath_admin.R;
import com.cproz.pantomath_admin.StudentsRequest.StudentRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ManageTeachers extends AppCompatActivity {

    ImageView searchIcon;
    private FirebaseFirestore db;
    ImageView Cross;
    EditText SearchView;
    TeacherAdapter teacherAdapter;
    private List<TeacherData> TeacherList;
    TeacherData teacherData;
    SwipeRefreshLayout swipeRefreshLayout;
    CardView cardView;
    RecyclerView recyclerView;
    Button AddTeacher;
    NavigationView navigationView;
    DrawerLayout drawerLayout;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_teachers);
        db = FirebaseFirestore.getInstance();
        Initialization();



        navigationView.getMenu().findItem(R.id.ManageTeachers).setChecked(true);
        Cross.setVisibility(View.GONE);

        AddTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ManageTeachers.this, com.cproz.pantomath_admin.ManageTeachers.AddTeacher.AddTeacher.class);
                intent.putExtra("Xyz", "Xyz");
                startActivity(intent);
            }
        });

        TeacherList = new ArrayList<>();
        swipeRefreshLayout.setColorSchemeResources(R.color.blue);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);

        recyclerView.setLayoutManager(gridLayoutManager);
        TeacherList.clear();
        swipeRefreshLayout.setEnabled(true);
        loadTeachers();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                swipeRefreshLayout.setEnabled(false);
                TeacherList.clear();
                loadTeachers();

                swipeRefreshLayout.setRefreshing(false);
                //swipeRefreshLayout.setEnabled(true);
            }
        });


        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                    drawerLayout.closeDrawer(GravityCompat.START);
                }else
                {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });



        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.ActivityDashboard:
                        Intent intent = new Intent(ManageTeachers.this, Dashboard.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                    case R.id.AllDoubts:
                        intent = new Intent(ManageTeachers.this, AllDoubts.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;

                    case R.id.ManageTeachers:

                        navigationView.getMenu().findItem(R.id.ManageTeachers).setChecked(true);
                        break;

                    case R.id.ManageStudents:
                        intent = new Intent(ManageTeachers.this, ManageStudents.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;

                    case R.id.StudentRequests:
                        intent = new Intent(ManageTeachers.this, StudentRequest.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;


                    case R.id.Notifications:
                        intent = new Intent(ManageTeachers.this, Notifications.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;

                    case R.id.Settings:
                        intent = new Intent(ManageTeachers.this, Feedback.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;

                    case R.id.Logout:

//                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//                        firebaseAuth.signOut();
//                        startActivity(new Intent(ManageTeachers.this, Login.class));
                        LogoutPopUp log = new LogoutPopUp();
                        log.show(getSupportFragmentManager(), "logout");
                        break;

                    case R.id.About:
                        intent = new Intent(ManageTeachers.this, About.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);


                return true;
            }
        });




        SearchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {


                searchIcon.setImageResource(R.drawable.back_search);

                    searchIcon.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            SearchView.getText().clear();


                            SearchView.clearFocus();
                            SearchView.getText().clear();
                            Cross.setVisibility(View.GONE);
//                            InputMethodManager imm = (InputMethodManager) Context.getSystemService(Context.INPUT_METHOD_SERVICE);
//                            assert imm != null;
                            //imm.hideSoftInputFromWindow(getWindow().getWindowManager().getDefaultDisplay().get, 0);
                            searchIcon.setImageResource(R.drawable.hamburger);
                            searchIcon.setEnabled(true);
                            swipeRefreshLayout.setEnabled(true);
                            searchIcon.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                                        drawerLayout.closeDrawer(GravityCompat.START);
                                    }else
                                    {
                                        drawerLayout.openDrawer(GravityCompat.START);
                                    }
                                }
                            });
                        }
                    });




                SearchView.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @SuppressLint("ClickableViewAccessibility")
                    @Override
                    public void afterTextChanged(Editable s) {
                        //ProfilePictureHome.setVisibility(View.GONE);
                        //ProfilePictureHome.setImageResource(R.drawable.cross);
                        Cross.setVisibility(View.VISIBLE);
                        Cross.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                SearchView.getText().clear();
                                //ProfilePictureHome.setVisibility(View.GONE);
                                Cross.setVisibility(View.GONE);


                            }
                        });



                    }
                });







                return false;
            }
        });







    }

    private void Initialization() {

        searchIcon = findViewById(R.id.hamIconTeacher);
        Cross = findViewById(R.id.CrossTeacher);
        SearchView = findViewById(R.id.SearchEditTextMTeacher);
        cardView = findViewById(R.id.searchBarTeacher);
        swipeRefreshLayout = findViewById(R.id.refreshLayoutTeacher);
        recyclerView = findViewById(R.id.RecyclerViewTeachers);
        AddTeacher = findViewById(R.id.AddTeacher);
        navigationView = findViewById(R.id.HomeNavigationDrawerTeacher);
        drawerLayout = findViewById(R.id.drawerlayoutTeacher);


    }

    public void loadTeachers(){
        db.collection("Users/Teachers/Teacherinfo").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot querySnapshot : Objects.requireNonNull(task.getResult())){


                    teacherData = new TeacherData(querySnapshot.getString("Board"),querySnapshot.getString("Email"), querySnapshot.getString("Name"),querySnapshot.getString("STD"),
                            querySnapshot.getString("Subject"),
                            querySnapshot.getString("User"),
                            querySnapshot.getString("profileURl"),querySnapshot.getString("uid"),querySnapshot.getDate("OnlineTime"),querySnapshot.getString("Number"),
                            querySnapshot.getString("Address"), querySnapshot.getString("SuspendedBy"));

                    TeacherList.add(teacherData);



                    teacherAdapter = new TeacherAdapter(ManageTeachers.this, TeacherList);


                    recyclerView.setItemViewCacheSize(40);


                    recyclerView.setAdapter(teacherAdapter);


                }
                swipeRefreshLayout.setEnabled(true);
            }
        });

        SearchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                filter(s.toString());



            }
        });


    }

    @SuppressLint("ResourceAsColor")
    private void filter(String text) {

        recyclerView.setBackgroundColor(Color.parseColor("#ffffff"));

        ArrayList<TeacherData> filteredList = new ArrayList<>();
        for (TeacherData item: TeacherList){

            if (item.getBoard().toLowerCase().contains(text.toLowerCase())||item.getSTD().toLowerCase().contains(text.toLowerCase())
                    ||item.getNameTeacher().toLowerCase().contains(text.toLowerCase())||item.getEmail().toLowerCase().contains(text.toLowerCase())
                    ||item.getSubject().toLowerCase().contains(text.toLowerCase())||item.getUser().toLowerCase().contains(text.toLowerCase())){

                filteredList.add(item);
                teacherAdapter.filteredList(filteredList);
                swipeRefreshLayout.setEnabled(false);


            }



        }
        if (filteredList.isEmpty()){

            teacherAdapter.filteredList(filteredList);

        }






    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ManageTeachers.this, Dashboard.class));
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        //loadTeachers();
        super.onResume();
    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {

        super.onActivityReenter(resultCode, data);
    }
}