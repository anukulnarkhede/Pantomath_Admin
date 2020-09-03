package com.cproz.pantomath_admin.AllDoubts;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.cproz.pantomath_admin.Home.HomeDoubtData;
import com.cproz.pantomath_admin.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllDoubtsAdapter extends RecyclerView.Adapter<AllDoubtsViewHolder>{

    private Context mContext;
    private List<HomeDoubtData> DoubtList;

    public AllDoubtsAdapter(Context mContext, List<HomeDoubtData> doubtList) {
        this.mContext = mContext;
        DoubtList = doubtList;
    }



    @NonNull
    @Override
    public AllDoubtsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.all_doubts_card, parent, false);
        return new AllDoubtsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AllDoubtsViewHolder holder, int position) {


        holder.solved.setVisibility(View.GONE);

        if (DoubtList.get(position).getProfileImageURL().equals("")){
            holder.profileImage.setImageResource(R.drawable.profile_defult);
        }else if (!DoubtList.get(position).getProfileImageURL().equals("")){
            Picasso.get().load(DoubtList.get(position).getProfileImageURL()).into(holder.profileImage);
        }


        holder.QuestionText.setText(DoubtList.get(position).getQText());

        Date dateTimex = DoubtList.get(position).getDateTime();
        assert dateTimex != null;
        long milix = dateTimex.getTime();
        final String datex = DateUtils.getRelativeTimeSpanString(milix).toString();

        holder.TimeText.setText(datex);


        holder.
                UserName.setText(toTitleCase(DoubtList.get(position).getNameHome()));



        String BoardClasstext = DoubtList.get(position).getSubject() + " . " + DoubtList.get(position).getSTD() + " " + DoubtList.get(position).getBoard();
        holder.BoardClass.setText(BoardClasstext);



        if (DoubtList.get(position).getStatus().equals("Solved")){
            holder.solved.setVisibility(View.VISIBLE);
        }


        switch (DoubtList.get(position).getSubject()){
            case "Algebra":

                holder.BoardClass.setTextColor(Color.parseColor("#FF2829"));

                break;

            case "Geometry":

                holder.BoardClass.setTextColor(Color.parseColor("#9A0D91"));
                break;

            case "Physics":

                holder.BoardClass.setTextColor(Color.parseColor("#0078FF"));
                break;

            case "Chemistry":

                holder.BoardClass.setTextColor(Color.parseColor("#FF9B00"));
                break;

            case "Biology":

                holder.BoardClass.setTextColor(Color.parseColor("#FF1ADD"));
                break;

            case "History":

                holder.BoardClass.setTextColor(Color.parseColor("#813912"));
                break;

            case "Geography":

                holder.BoardClass.setTextColor(Color.parseColor("#009F37"));
                break;

            case "Languages":

                holder.BoardClass.setTextColor(Color.parseColor("#5550B6"));
                break;
        }






        final Date QuestionTime = DoubtList.get(position).getQuestionDate();
        assert QuestionTime != null;
        long milixx = QuestionTime.getTime();
        final String QuestionTimex = DateUtils.getRelativeTimeSpanString(milixx).toString();


        holder.CardBgAllDoubts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DoubtDetails.class);
                intent.putExtra("QuestionImage1Url", DoubtList.get(holder.getAdapterPosition()).getPhoto1url());
                intent.putExtra("QuestionImage2Url", DoubtList.get(holder.getAdapterPosition()).getPhoto2url());
                intent.putExtra("AnsImage1Url", DoubtList.get(holder.getAdapterPosition()).getAnsPhotoUrl1());
                intent.putExtra("AnsImage2Url", DoubtList.get(holder.getAdapterPosition()).getAnsPhotoUrl2());
                intent.putExtra("AnsText", DoubtList.get(holder.getAdapterPosition()).getAnsText());
                intent.putExtra("AudioUrl", DoubtList.get(holder.getAdapterPosition()).getAudioUrl());
                intent.putExtra("QuestionText", DoubtList.get(holder.getAdapterPosition()).getQText());
                intent.putExtra("Board", DoubtList.get(holder.getAdapterPosition()).getBoard());
                intent.putExtra("Chapter", DoubtList.get(holder.getAdapterPosition()).getChapter());
                intent.putExtra("DateTime", DoubtList.get(holder.getAdapterPosition()).getDateTime());
                intent.putExtra("Email", DoubtList.get(holder.getAdapterPosition()).getEmailHome());
                intent.putExtra("FileUrl", DoubtList.get(holder.getAdapterPosition()).getFileUrl());
                intent.putExtra("Link", DoubtList.get(holder.getAdapterPosition()).getLink());
                intent.putExtra("Name", DoubtList.get(holder.getAdapterPosition()).getNameHome());
                intent.putExtra("ProfileImage", DoubtList.get(holder.getAdapterPosition()).getProfileImageURL());
                intent.putExtra("STD", DoubtList.get(holder.getAdapterPosition()).getSTD());
                intent.putExtra("Status", DoubtList.get(holder.getAdapterPosition()).getStatus());
                intent.putExtra("Subject", DoubtList.get(holder.getAdapterPosition()).getSubject());
                intent.putExtra("Teacher", DoubtList.get(holder.getAdapterPosition()).getTeacher());
                intent.putExtra("TeacherImage", DoubtList.get(holder.getAdapterPosition()).getTeacherImageUrl());
                intent.putExtra("QuestionDate", DoubtList.get(holder.getAdapterPosition()).getQuestionDate());
                intent.putExtra("uid", DoubtList.get(holder.getAdapterPosition()).getUid());



                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return DoubtList.size();
    }





    public static String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder(input.length());
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }



    public void filteredList(ArrayList<HomeDoubtData> filteredList) {

        DoubtList = filteredList;
        notifyDataSetChanged();

    }
}



class AllDoubtsViewHolder extends RecyclerView.ViewHolder{

    ConstraintLayout CardBgAllDoubts;
    TextView QuestionText, TimeText, BoardClass, UserName;
    CircleImageView profileImage;
    ImageView solved;

    public AllDoubtsViewHolder(@NonNull View itemView) {

        super(itemView);

        CardBgAllDoubts = itemView.findViewById(R.id.CardBgAllDoubts);
        QuestionText = itemView.findViewById(R.id.QuestionText);
        TimeText = itemView.findViewById(R.id.timetextAllDoubts);
        BoardClass = itemView.findViewById(R.id.textView3);
        UserName = itemView.findViewById(R.id.textView5);
        profileImage = itemView.findViewById(R.id.profilePicAldoubts);
        solved = itemView.findViewById(R.id.solvedAllDoubts);


    }
}