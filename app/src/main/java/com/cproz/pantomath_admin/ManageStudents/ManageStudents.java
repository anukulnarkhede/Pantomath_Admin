package com.cproz.pantomath_admin.ManageStudents;

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
import android.widget.EditText;
import android.widget.ImageView;

import com.cproz.pantomath_admin.About.About;
import com.cproz.pantomath_admin.AllDoubts.AllDoubts;
import com.cproz.pantomath_admin.Feedback.Feedback;
import com.cproz.pantomath_admin.Home.Dashboard;
import com.cproz.pantomath_admin.Login.LogoutPopUp;
import com.cproz.pantomath_admin.ManageTeachers.ManageTeachers;
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

public class ManageStudents extends AppCompatActivity {


    ImageView searchIcon;
    private FirebaseFirestore db;
    ImageView Cross;
    EditText SearchView;
    StudentAdapter studentAdapter;
    private List<StudentData> StudentList;
    StudentData studentData;
    SwipeRefreshLayout swipeRefreshLayout;
    CardView cardView;
    RecyclerView recyclerView;

    NavigationView navigationView;
    DrawerLayout drawerLayout;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_students);
        Initialization();

        Cross.setVisibility(View.GONE);

        db = FirebaseFirestore.getInstance();
        StudentList = new ArrayList<>();
        swipeRefreshLayout.setColorSchemeResources(R.color.blue);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);

        recyclerView.setLayoutManager(gridLayoutManager);
        navigationView.getMenu().findItem(R.id.ManageStudents).setChecked(true);
        StudentList.clear();
        dataFromFirestore();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                StudentList.clear();
                dataFromFirestore();
                swipeRefreshLayout.setEnabled(false);
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
                        Intent intent = new Intent(ManageStudents.this, Dashboard.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;
                    case R.id.AllDoubts:
                        intent = new Intent(ManageStudents.this, AllDoubts.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;

                    case R.id.ManageTeachers:
                        intent = new Intent(ManageStudents.this, ManageTeachers.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                        break;

                    case R.id.ManageStudents:
                        intent = new Intent(ManageStudents.this, ManageStudents.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;

                    case R.id.StudentRequests:
                        intent = new Intent(ManageStudents.this, StudentRequest.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;


                    case R.id.Notifications:
                        intent = new Intent(ManageStudents.this, Notifications.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;

                    case R.id.Settings:
                        intent = new Intent(ManageStudents.this, Feedback.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        break;

                    case R.id.Logout:

//                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//                        firebaseAuth.signOut();
//                        startActivity(new Intent(ManageStudents.this, Login.class));
                        LogoutPopUp log = new LogoutPopUp();
                        log.show(getSupportFragmentManager(), "logout");
                        break;


                    case R.id.About:
                        intent = new Intent(ManageStudents.this, About.class);
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

        searchIcon = findViewById(R.id.hamIconStudent);
        Cross = findViewById(R.id.CrossStudent);
        SearchView = findViewById(R.id.SearchEditTextMStudent);
        cardView = findViewById(R.id.searchBarStudent);
        swipeRefreshLayout = findViewById(R.id.refreshLayoutStudent);
        recyclerView = findViewById(R.id.RecyclerViewStudent);

        navigationView = findViewById(R.id.HomeNavigationDrawerStudent);
        drawerLayout = findViewById(R.id.drawerlayoutStudent);


    }

    public void dataFromFirestore() {

        db.collection("Users/Students/StudentsInfo").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot querySnapshot : Objects.requireNonNull(task.getResult())){




                    if (!Objects.equals(querySnapshot.getString("User"), "Not Verified") && !Objects.equals(querySnapshot.getString("User"), "Deleted")){
                        studentData = new StudentData(querySnapshot.getString("Address"),querySnapshot.getString("Board")
                                ,querySnapshot.getString("Class"),querySnapshot.getString("Email"),
                                querySnapshot.getString("Name"),querySnapshot.getString("Number")
                                , querySnapshot.getString("User"),querySnapshot.getString("profileURL"),
                                querySnapshot.getString("uid"), querySnapshot.getDate("SignupTime"), querySnapshot.getString("SuspendedBy")  );

                        StudentList.add(studentData);
                    }

                    studentAdapter = new StudentAdapter(ManageStudents.this, StudentList);


                    recyclerView.setItemViewCacheSize(40);

                    recyclerView.setAdapter(studentAdapter);



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

        ArrayList<StudentData> filteredList = new ArrayList<>();
        for (StudentData item: StudentList){

            if (item.getBoardx().toLowerCase().contains(text.toLowerCase())||item.getClassx().toLowerCase().contains(text.toLowerCase())
                    ||item.getNamex().toLowerCase().contains(text.toLowerCase())||item.getEmailx().toLowerCase().contains(text.toLowerCase())
                    ||item.getAddress().toLowerCase().contains(text.toLowerCase())||item.getUserx().toLowerCase().contains(text.toLowerCase())){

                filteredList.add(item);
                studentAdapter.filteredList(filteredList);
                swipeRefreshLayout.setEnabled(false);


            }



        }
        if (filteredList.isEmpty()){

            studentAdapter.filteredList(filteredList);

        }






    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(ManageStudents.this, Dashboard.class));
        super.onBackPressed();
    }
}