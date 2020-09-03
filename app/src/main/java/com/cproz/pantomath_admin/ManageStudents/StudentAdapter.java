package com.cproz.pantomath_admin.ManageStudents;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.cproz.pantomath_admin.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class StudentAdapter extends RecyclerView.Adapter<StudentViewHolder> {

    Context mContext;
    List<StudentData> StudentList;


    public StudentAdapter(Context mContext, List<StudentData> studentList) {
        this.mContext = mContext;
        StudentList = studentList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.student_card, parent, false);
        return new StudentViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, final int position) {

        holder.UserName.setText(StudentList.get(position).getNamex());
        holder.BoardTag.setText(StudentList.get(position).getClassx() + " " +StudentList.get(position).getBoardx());
        holder.Status.setText(StudentList.get(position).getUserx());

        if (StudentList.get(position).getUserx().equals("Verified")){
            holder.Status.setTextColor(Color.parseColor("#009F37"));
        }
        else if (StudentList.get(position).getUserx().equals("Not Verified")){
            holder.Status.setTextColor(Color.parseColor("#FF2829"));
        }
        else if (StudentList.get(position).getUserx().equals("Suspended")){
            holder.Status.setTextColor(Color.parseColor("#FF2829"));
        }


        if (StudentList.get(position).getProfileURL().equals("")){
            holder.ProfileImage.setImageResource(R.drawable.profile_defult);
        }
        else if (!StudentList.get(position).getProfileURL().equals("")){
                Picasso.get().load(StudentList.get(position).getProfileURL()).into(holder.ProfileImage);
            }


        holder.cardBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, StudentDetails.class);
                intent.putExtra("UserName", StudentList.get(position).getNamex());
                intent.putExtra("Board", StudentList.get(position).getBoardx());
                intent.putExtra("Class", StudentList.get(position).getClassx());
                intent.putExtra("UserStatus", StudentList.get(position).getUserx());
                intent.putExtra("Number", StudentList.get(position).getNumber());
                intent.putExtra("Address", StudentList.get(position).getAddress());
                intent.putExtra("uid", StudentList.get(position).getUidx());
                intent.putExtra("Email", StudentList.get(position).getEmailx());
                intent.putExtra("SignupTime", StudentList.get(position).getSignupTime());
                intent.putExtra("profileImageUrl", StudentList.get(position).getProfileURL());
                intent.putExtra("SuspendedBy", StudentList.get(position).getSuspendedBy());

                mContext.startActivity(intent);
//                notifyDataSetChanged();


            }
        });




    }

    @Override
    public int getItemCount() {
        return StudentList.size();
    }

    public void filteredList(ArrayList<StudentData> filteredList) {

        StudentList = filteredList;
        notifyDataSetChanged();

    }
}


class StudentViewHolder extends RecyclerView.ViewHolder{

    TextView UserName, Status, BoardTag;
    CircleImageView ProfileImage;
    ConstraintLayout cardBg;

    public StudentViewHolder(@NonNull View itemView) {
        super(itemView);
        ProfileImage = itemView.findViewById(R.id.profilePicStudent);
        UserName = itemView.findViewById(R.id.UserNameStudent);
        Status = itemView.findViewById(R.id.StatusStudent);
        BoardTag = itemView.findViewById(R.id.BoardTagStudent);
        cardBg = itemView.findViewById(R.id.CardBgStudent);
    }
}
