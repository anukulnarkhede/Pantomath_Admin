package com.cproz.pantomath_admin.ManageTeachers;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;


import com.anychart.graphics.vector.Circle;
import com.cproz.pantomath_admin.R;
import com.google.firebase.firestore.auth.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherViewHolder> {

    private Context mContext;
    public List<TeacherData> TeacherList ;

    public TeacherAdapter(Context mContext, List<TeacherData> teacherList) {
        this.mContext = mContext;
        TeacherList = teacherList;
    }

    @NonNull
    @Override
    public TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.teacher_card, parent, false);
        return new TeacherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TeacherViewHolder holder, final int position) {


        if (TeacherList.get(position).getProfileURl().equals("")){
            holder.ProfileImage.setImageResource(R.drawable.profile_defult);
        }
        else if (!TeacherList.get(position).getProfileURl().equals("")){
                Picasso.get().load(TeacherList.get(position).getProfileURl()).into(holder.ProfileImage);
            }

        holder.SubjectTag.setText(TeacherList.get(position).getSubject());

        holder.Status.setText(TeacherList.get(position).getUser());

        if (TeacherList.get(position).getUser().equals("Active")){
            holder.Status.setTextColor(Color.parseColor("#009F37"));
        }else if (TeacherList.get(position).getUser().equals("Suspended")){
            holder.Status.setTextColor(Color.parseColor("#FF2829"));
        }

        holder.UserName.setText(TeacherList.get(position).getNameTeacher());


        Date dateTime = TeacherList.get(position).getOnlineTime();




        holder.cardBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, TeacherDetails.class);
                intent.putExtra("Board", TeacherList.get(position).getBoard());
                intent.putExtra("Email", TeacherList.get(position).getEmail());
                intent.putExtra("profileImageUrl", TeacherList.get(position).getProfileURl());
                intent.putExtra("Class", TeacherList.get(position).getSTD());
                intent.putExtra("UserStatus", TeacherList.get(position).getUser());
                intent.putExtra("uid", TeacherList.get(position).getUid());
                intent.putExtra("OnlineTime", TeacherList.get(position).getOnlineTime());
                intent.putExtra("Name", TeacherList.get(position).getNameTeacher());
                intent.putExtra("Number", TeacherList.get(position).getNumber());
                intent.putExtra("Address", TeacherList.get(position).getAddress());
                intent.putExtra("Subject", TeacherList.get(position).getSubject());
                intent.putExtra("SuspendedBy", TeacherList.get(position).getSuspendedBy());





                mContext.startActivity(intent);
            }
        });

        try
        {
            assert dateTime != null;
            long mili = dateTime.getTime();

            final String datex = DateUtils.getRelativeTimeSpanString(mili).toString();

            holder.TimeText.setText(datex);
        }catch (Exception e){
            System.out.println(e);
        }


    }

    @Override
    public int getItemCount() {
        return TeacherList.size();
    }

    public void filteredList(ArrayList<TeacherData> filteredList) {

        TeacherList = filteredList;
        notifyDataSetChanged();

    }
}



class TeacherViewHolder extends RecyclerView.ViewHolder{

    TextView UserName, TimeText, Status, SubjectTag;
    CircleImageView ProfileImage;
    ConstraintLayout cardBg;

    public TeacherViewHolder(@NonNull View itemView) {
        super(itemView);
        ProfileImage = itemView.findViewById(R.id.profilePicTeacher);
        UserName = itemView.findViewById(R.id.UserNameTeacher);
        TimeText = itemView.findViewById(R.id.timetextTeacher);
        Status = itemView.findViewById(R.id.StatusTeacher);
        SubjectTag = itemView.findViewById(R.id.SubjectTagTeacher);
        cardBg = itemView.findViewById(R.id.CardBgTeacher);

    }
}