package com.cproz.pantomath_admin.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.telephony.mbms.MbmsErrors;
import android.text.Editable;
import android.text.TextWatcher;

import com.cproz.pantomath_admin.AllDoubts.AllDoubts;
import com.cproz.pantomath_admin.AllDoubts.AllDoubtsAdapter;
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

public class ReportedDoubts extends AppCompatActivity {


    Toolbar toolbar;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeReported;
    AllDoubtsAdapter allDoubtsAdapter;
    private List<HomeDoubtData> DoubtList2;
    private HomeDoubtData homeDoubtData;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reported_doubts);
        Initialization();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);

        recyclerView.setLayoutManager(gridLayoutManager);
        DataFromFirestore();

        swipeReported.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeReported.setEnabled(false);
                DataFromFirestore();
                swipeReported.setRefreshing(false);
            }
        });

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        swipeReported.setColorSchemeResources(R.color.blue);

    }

    private void Initialization() {

        toolbar = findViewById(R.id.DashToolBar);
        recyclerView = findViewById(R.id.RecyclerViewReportedDoubt);
        swipeReported = findViewById(R.id.swipeReported);
        DoubtList2 = new ArrayList<>();

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







                    if (querySnapshot.getString("Status").equals("Reported")){
                        homeDoubtData = new HomeDoubtData(querySnapshot.getString("AnsPhotoUrl1"), querySnapshot.getString("AnsPhotoUrl2"), querySnapshot.getString("AnsText"),
                                querySnapshot.getString("AudioUrl"), querySnapshot.getString("Board"), querySnapshot.getString("Chapter"),
                                querySnapshot.getString("Email"), querySnapshot.getString("FileUrl"), querySnapshot.getString("Link"),
                                querySnapshot.getString("Name"), querySnapshot.getString("Photo1url"), querySnapshot.getString("Photo2url"),
                                querySnapshot.getString("ProfileImageURL"), querySnapshot.getString("QText"), querySnapshot.getString("STD"),
                                querySnapshot.getString("Status"), querySnapshot.getString("Subject"), querySnapshot.getString("Teacher"), querySnapshot.getString("Uid")
                                , querySnapshot.getDate("DateTime"),"",querySnapshot.getDate("QuestionDate"));

                        DoubtList2.add(homeDoubtData);
                    }




                    allDoubtsAdapter = new AllDoubtsAdapter(ReportedDoubts.this, DoubtList2);


                    recyclerView.setItemViewCacheSize(40);

                    recyclerView.setAdapter(allDoubtsAdapter);


                }
                swipeReported.setEnabled(true);
            }
        });






    }

}