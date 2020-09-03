package com.cproz.pantomath_admin.AllDoubts;

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
import com.cproz.pantomath_admin.Feedback.Feedback;
import com.cproz.pantomath_admin.Home.Dashboard;
import com.cproz.pantomath_admin.Home.HomeDoubtData;
import com.cproz.pantomath_admin.Login.LogoutPopUp;
import com.cproz.pantomath_admin.ManageStudents.ManageStudents;
import com.cproz.pantomath_admin.ManageTeachers.ManageTeachers;
import com.cproz.pantomath_admin.Notifications.Notifications;
import com.cproz.pantomath_admin.R;
import com.cproz.pantomath_admin.StudentsRequest.StudentRequest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllDoubts extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();




    ImageView searchIcon;


    ImageView noResults;

    String ProfileImageURL = "";


    NavigationView navigationView;

    DrawerLayout drawerLayout;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();


    FirebaseUser user = firebaseAuth.getCurrentUser();


    private List<HomeDoubtData> DoubtList2;
    private HomeDoubtData homeDoubtData;
    private FirebaseFirestore db;
    CircleImageView ProfilePictureHome;
    ImageView Cross;
    EditText SearchView;
    AllDoubtsAdapter allDoubtsAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    CardView cardView;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_doubts);
        Initilization();






        Cross.setVisibility(View.GONE);
        swipeRefreshLayout.setColorSchemeResources(R.color.blue);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);

        recyclerView.setLayoutManager(gridLayoutManager);
        DoubtList2 = new ArrayList<>();




        navigationView.getMenu().findItem(R.id.AllDoubts).setChecked(true);
        DataFromFirestore();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                swipeRefreshLayout.setEnabled(false);
                DoubtList2.clear();
                DataFromFirestore();

                swipeRefreshLayout.setRefreshing(false);
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
                        Intent intent = new Intent(AllDoubts.this, Dashboard.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    case R.id.AllDoubts:
                        navigationView.getMenu().findItem(R.id.AllDoubts).setChecked(true);
                        break;

                    case R.id.ManageTeachers:
                        intent = new Intent(AllDoubts.this, ManageTeachers.class);
                        intent.setFlags( Intent.FLAG_ACTIVITY_CLEAR_TASK );
                        startActivity(intent);
                        break;

                    case R.id.ManageStudents:
                        intent = new Intent(AllDoubts.this, ManageStudents.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK );
                        startActivity(intent);
                        break;

                    case R.id.StudentRequests:
                        intent = new Intent(AllDoubts.this, StudentRequest.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        break;
                    case R.id.Notifications:
                        intent = new Intent(AllDoubts.this, Notifications.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK );
                        startActivity(intent);
                        break;

                    case R.id.Settings:
                        intent = new Intent(AllDoubts.this, Feedback.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK );
                        startActivity(intent);
                        break;

                    case R.id.Logout:
                        LogoutPopUp log = new LogoutPopUp();
                        log.show(getSupportFragmentManager(), "logout");
                        break;


                    case R.id.About:
                        intent = new Intent(AllDoubts.this, About.class);
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

    private void Initilization() {


        recyclerView = findViewById(R.id.RecyclerViewAllDoubts);
        SearchView = findViewById(R.id.SearchEditTextAllDoubts);
        db = FirebaseFirestore.getInstance();
        searchIcon = findViewById(R.id.hamIconAllDoubts);
        Cross = findViewById(R.id.Cross);
        cardView = findViewById(R.id.searchBarAllDoubts);
        navigationView = findViewById(R.id.HomeNavigationDrawerAllDoubts);
        drawerLayout = findViewById(R.id.drawerlayoutAllDoubts);
        swipeRefreshLayout = findViewById(R.id.refreshLayoutAllDoubts);



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        return false;
    }





    public void DataFromFirestore(){


        db.collection("Doubts").orderBy("DateTime", Query.Direction.DESCENDING).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot querySnapshot : Objects.requireNonNull(task.getResult())){


                    //Date date = new Date();





                    /*firebaseFirestore.collection("Users/Students/StudentsInfo/" ).document(String.valueOf(querySnapshot.getString("Email"))).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            HomeFragment.PROFILEURLX = documentSnapshot.getString("profileURL");


                        }
                    });*/







                    if (!Objects.equals(querySnapshot.getString("Status"), "Reported")){
                        homeDoubtData = new HomeDoubtData(querySnapshot.getString("AnsPhotoUrl1"), querySnapshot.getString("AnsPhotoUrl2"), querySnapshot.getString("AnsText"),
                                querySnapshot.getString("AudioUrl"), querySnapshot.getString("Board"), querySnapshot.getString("Chapter"),
                                querySnapshot.getString("Email"), querySnapshot.getString("FileUrl"), querySnapshot.getString("Link"),
                                querySnapshot.getString("Name"), querySnapshot.getString("Photo1url"), querySnapshot.getString("Photo2url"),
                                querySnapshot.getString("ProfileImageURL"), querySnapshot.getString("QText"), querySnapshot.getString("STD"),
                                querySnapshot.getString("Status"), querySnapshot.getString("Subject"), querySnapshot.getString("Teacher"), querySnapshot.getString("Uid")
                                ,querySnapshot.getDate("DateTime"),querySnapshot.getString("TeacherImageUrl"),querySnapshot.getDate("QuestionDate"));

                        DoubtList2.add(homeDoubtData);
                    }




                    allDoubtsAdapter = new AllDoubtsAdapter(AllDoubts.this, DoubtList2);


                    recyclerView.setItemViewCacheSize(40);

                    recyclerView.setAdapter(allDoubtsAdapter);


                }
                swipeRefreshLayout.setEnabled(false);
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

        ArrayList<HomeDoubtData> filteredList = new ArrayList<>();
        for (HomeDoubtData item: DoubtList2){

            if (item.getTeacher().toLowerCase().contains(text.toLowerCase())||item.getSubject().toLowerCase().contains(text.toLowerCase()) || item.getChapter().toLowerCase().contains(text.toLowerCase()) || item.getQText().toLowerCase().contains(text.toLowerCase())
                    || item.getAnsText().toLowerCase().contains(text.toLowerCase())||item.getBoard().toLowerCase().contains(text.toLowerCase())||item.getSTD().toLowerCase().contains(text.toLowerCase())||item.getNameHome().toLowerCase().contains(text.toLowerCase())
            ||item.getAnsText().toLowerCase().contains(text.toLowerCase())||item.getEmailHome().toLowerCase().contains(text.toLowerCase())){

                filteredList.add(item);
                allDoubtsAdapter.filteredList(filteredList);
                swipeRefreshLayout.setEnabled(false);
                //noResults.setVisibility(View.GONE);

            }



        }
        if (filteredList.isEmpty()){

            allDoubtsAdapter.filteredList(filteredList);
            //recyclerView.setBackgroundColor(R.drawable.notfound);
            //noResults.setVisibility(View.VISIBLE);


            //Toast.makeText(getContext(), "No results found", Toast.LENGTH_SHORT).show();
        }






    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AllDoubts.this, Dashboard.class));
        super.onBackPressed();
    }
}