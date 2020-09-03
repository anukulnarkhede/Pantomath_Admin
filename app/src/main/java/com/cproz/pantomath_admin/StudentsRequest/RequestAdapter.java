package com.cproz.pantomath_admin.StudentsRequest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;


import com.cproz.pantomath_admin.ManageStudents.StudentData;
import com.cproz.pantomath_admin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.internal.http.RequestLine;

public class RequestAdapter extends RecyclerView.Adapter<RequestViewHolder>{

    Context mContext;
    List<StudentData> RequestList;
    FragmentManager supportFragmentManager;


    public RequestAdapter(Context mContext, List<StudentData> requestList, FragmentManager supportFragmentManager) {
        this.mContext = mContext;
        RequestList = requestList;
        this.supportFragmentManager = supportFragmentManager;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.request_card, parent, false);
        return new RequestViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final RequestViewHolder holder, final int position) {

        if (RequestList.get(position).getProfileURL().equals("")){
            holder.profilePicReq.setImageResource(R.drawable.profile_defult);
        }else if (!RequestList.get(position).getProfileURL().equals("")){
            Picasso.get().load(RequestList.get(position).getProfileURL()).into(holder.profilePicReq);
        }



        holder.UserNameReq.setText(RequestList.get(position).getNamex());


        Date dateTimex = RequestList.get(position).getSignupTime();
        assert dateTimex != null;
        long milix = dateTimex.getTime();
        final String datex = DateUtils.getRelativeTimeSpanString(milix).toString();


        holder.TimeTextReq.setText(datex);

        holder.BoardTagStudentReq.setText(RequestList.get(position).getClassx() +" "+RequestList.get(position).getBoardx());

        holder.Accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                firebaseFirestore.collection("Users/Students/StudentsInfo").document(RequestList.get(position).getEmailx()).update("User", "Active").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //mContext.startActivity(new Intent(mContext, StudentRequest.class));
                    }
                });
                holder.Accept.setText("Accepted");
                holder.Accept.setEnabled(false);
                holder.delete.setAlpha(0);
                holder.delete.setEnabled(false);

            }
        });



        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteRequest deleteRequest = new DeleteRequest();
                Bundle args = new Bundle();

                args.putString("Email", RequestList.get(position).getEmailx());
                deleteRequest.setArguments(args);
                deleteRequest.show(supportFragmentManager, "xsd");
//                holder.delete.setText("Deleted");
//                holder.delete.setEnabled(false);
            }
        });



    }

    @Override
    public int getItemCount() {
        return RequestList.size();
    }
}


class RequestViewHolder extends RecyclerView.ViewHolder{

    CircleImageView profilePicReq;
    TextView UserNameReq, TimeTextReq, BoardTagStudentReq;
    Button delete, Accept;


    public RequestViewHolder(@NonNull View itemView) {
        super(itemView);

        profilePicReq = itemView.findViewById(R.id.profilePicReq);
        UserNameReq = itemView.findViewById(R.id.UserNameReq);
        TimeTextReq = itemView.findViewById(R.id.TimeTextReq);
        BoardTagStudentReq = itemView.findViewById(R.id.BoardTagStudentReq);
        delete = itemView.findViewById(R.id.delete);
        Accept = itemView.findViewById(R.id.Accept);

    }








}