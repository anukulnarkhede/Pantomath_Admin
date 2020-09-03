package com.cproz.pantomath_admin.StudentsRequest;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.cproz.pantomath_admin.ManageStudents.ManageStudents;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class DeleteRequest extends AppCompatDialogFragment {

    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();



    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete")
                .setMessage("Are you sure you want to Delete this Request?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        assert getArguments() != null;
                        firebaseFirestore.collection("Users/Students/StudentsInfo").document(Objects.requireNonNull(getArguments()
                                .getString("Email"))).update("User","Deleted")
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @SuppressLint("SetTextI18n")
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {



                                    }
                                });

                        startActivity(new Intent(getContext(), StudentRequest.class));


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
