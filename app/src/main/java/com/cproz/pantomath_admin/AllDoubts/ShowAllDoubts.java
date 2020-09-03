package com.cproz.pantomath_admin.AllDoubts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.cproz.pantomath_admin.Home.HomeDoubtData;
import com.cproz.pantomath_admin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ShowAllDoubts extends AppCompatActivity {

    RecyclerView recyclerView;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

    ImageView Back;
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
        setContentView(R.layout.show_all_doubts);
        Initilization();

        swipeRefreshLayout.setColorSchemeResources(R.color.blue);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);

        recyclerView.setLayoutManager(gridLayoutManager);
        DoubtList2 = new ArrayList<>();

        final Bundle bundle = getIntent().getExtras();
        Cross.setVisibility(View.GONE);

        assert bundle != null;
//        Toast.makeText(this, bundle.getString("User"), Toast.LENGTH_SHORT).show();

        if (Objects.equals(bundle.getString("User"), "Student")){

            DataFromFirestoreStudent();
        }else if (Objects.equals(bundle.getString("User"), "Teacher")){
            DataFromFirestoreTeacher();
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


                DoubtList2.clear();
                if (Objects.equals(bundle.getString("User"), "Student")){
                    DataFromFirestoreStudent();
                }else if (Objects.equals(bundle.getString("User"), "Teacher")){
                    DataFromFirestoreTeacher();
                }
                swipeRefreshLayout.setEnabled(false);

                swipeRefreshLayout.setRefreshing(false);
            }
        });


        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowAllDoubts.super.onBackPressed();
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


        recyclerView = findViewById(R.id.RecyclerViewShowAllDoubts);
        SearchView = findViewById(R.id.SearchEditTextShowAllDoubts);
        db = FirebaseFirestore.getInstance();
        Back = findViewById(R.id.backShowAllDoubts);
        Cross = findViewById(R.id.CrossShowall);
        cardView = findViewById(R.id.searchBarShowAllDoubts);
        swipeRefreshLayout = findViewById(R.id.refreshLayoutShowAllDoubts);



    }


    public void DataFromFirestoreStudent(){


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
                    Bundle bundle = getIntent().getExtras();


                    assert bundle != null;




                    if (Objects.equals(querySnapshot.getString("Email"), bundle.getString("Email"))&&!Objects.equals(querySnapshot.getString("Status"), "Reported")){
                        homeDoubtData = new HomeDoubtData(querySnapshot.getString("AnsPhotoUrl1"), querySnapshot.getString("AnsPhotoUrl2"), querySnapshot.getString("AnsText"),
                                querySnapshot.getString("AudioUrl"), querySnapshot.getString("Board"), querySnapshot.getString("Chapter"),
                                querySnapshot.getString("Email"), querySnapshot.getString("FileUrl"), querySnapshot.getString("Link"),
                                querySnapshot.getString("Name"), querySnapshot.getString("Photo1url"), querySnapshot.getString("Photo2url"),
                                querySnapshot.getString("ProfileImageURL"), querySnapshot.getString("QText"), querySnapshot.getString("STD"),
                                querySnapshot.getString("Status"), querySnapshot.getString("Subject"), querySnapshot.getString("Teacher"), querySnapshot.getString("Uid")
                                , querySnapshot.getDate("DateTime"),"",querySnapshot.getDate("QuestionDate"));

                        DoubtList2.add(homeDoubtData);
                    }







                    allDoubtsAdapter = new AllDoubtsAdapter(ShowAllDoubts.this, DoubtList2);


                    recyclerView.setItemViewCacheSize(40);

                    recyclerView.setAdapter(allDoubtsAdapter);


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


    public void DataFromFirestoreTeacher(){


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






                    Bundle bundle = getIntent().getExtras();


                    assert bundle != null;
                    if (Objects.equals(querySnapshot.getString("TeacherEmail"), bundle.getString("Email"))&& !Objects.equals(querySnapshot.getString("Status"), "Reported")){
                        homeDoubtData = new HomeDoubtData(querySnapshot.getString("AnsPhotoUrl1"), querySnapshot.getString("AnsPhotoUrl2"), querySnapshot.getString("AnsText"),
                                querySnapshot.getString("AudioUrl"), querySnapshot.getString("Board"), querySnapshot.getString("Chapter"),
                                querySnapshot.getString("Email"), querySnapshot.getString("FileUrl"), querySnapshot.getString("Link"),
                                querySnapshot.getString("Name"), querySnapshot.getString("Photo1url"), querySnapshot.getString("Photo2url"),
                                querySnapshot.getString("ProfileImageURL"), querySnapshot.getString("QText"), querySnapshot.getString("STD"),
                                querySnapshot.getString("Status"), querySnapshot.getString("Subject"), querySnapshot.getString("Teacher"), querySnapshot.getString("Uid")
                                , querySnapshot.getDate("DateTime"),"",querySnapshot.getDate("QuestionDate"));

                        DoubtList2.add(homeDoubtData);
                    }




                    allDoubtsAdapter = new AllDoubtsAdapter(ShowAllDoubts.this, DoubtList2);


                    recyclerView.setItemViewCacheSize(40);

                    recyclerView.setAdapter(allDoubtsAdapter);


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
        super.onBackPressed();
    }
}