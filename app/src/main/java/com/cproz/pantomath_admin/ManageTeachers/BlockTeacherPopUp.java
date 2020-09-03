package com.cproz.pantomath_admin.ManageTeachers;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.cproz.pantomath_admin.Notifications.Notifications;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class BlockTeacherPopUp extends AppCompatDialogFragment {


    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();

    String email = Objects.requireNonNull(user).getEmail();

    private DocumentReference ref = firebaseFirestore.collection("Users/Admin/AdminInfo/" ).document(email);

    public static String SUSPENDEDBY = BlockTeacherPopUp.SUSPENDEDBY;

    Boolean Flag = false;

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Suspend")
                .setMessage("Are you sure you want to Suspend this account?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {




                        ref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                String SuspendedBy = documentSnapshot.getString("Name");
                                assert getArguments() != null;
                                firebaseFirestore.collection("Users/Teachers/Teacherinfo").document(Objects.requireNonNull(getArguments()
                                        .getString("Email"))).update("User","Suspended", "SuspendedBy", SuspendedBy)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                            }
                                        });
                            }

                        });





                        startActivity(new Intent(getContext(), ManageTeachers.class));







                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        return builder.create();
    }


}
