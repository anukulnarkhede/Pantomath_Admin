package com.cproz.pantomath_admin.Notifications;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.cproz.pantomath_admin.AllDoubts.DoubtDetails;
import com.cproz.pantomath_admin.Home.HomeDoubtData;
import com.cproz.pantomath_admin.R;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder> {

    private Context mContext;
    private List<HomeDoubtData> DoubtList;

    public NotificationAdapter(Context mContext, List<HomeDoubtData> doubtList) {
        this.mContext = mContext;
        DoubtList = doubtList;
    }





    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.notification_card, parent, false);
        return new NotificationViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull final NotificationViewHolder holder, int position) {







        Date dateTime = DoubtList.get(position).getDateTime();
        assert dateTime != null;
        long mili = dateTime.getTime();

        final Date QuestionTime = DoubtList.get(position).getQuestionDate();




        holder.cardBg.setOnClickListener(new View.OnClickListener() {
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
                intent.putExtra("QuestionDate", QuestionTime);
                intent.putExtra("uid", DoubtList.get(holder.getAdapterPosition()).getUid());



                mContext.startActivity(intent);
            }
        });


        assert QuestionTime != null;
        long milix = QuestionTime.getTime();

        //TimeDifference = String.valueOf(mili-milix );


        String sourceString = "<b>" + toTitleCase(DoubtList.get(position).getTeacher())  + "</b> " + " has solved<br>" +
                "<b>" + toTitleCase(DoubtList.get(position).getNameHome()) + "</b>" + "'s doubt in<br>" + "<b>" + holder.TimeDifference((long) (mili-milix)/1000) + "</b>" ;

        holder.NotificationText.setText(Html.fromHtml(sourceString));





        final String DateTime = DateUtils.getRelativeTimeSpanString(mili).toString();


        holder.TimeText.setText(DateTime);

        if (DoubtList.get(position).getTeacherImageUrl().equals("")){
            holder.profile.setImageResource(R.drawable.profile_defult);
        }else if (!DoubtList.get(position).getProfileImageURL().equals("")){
            Picasso.get().load(DoubtList.get(position).getTeacherImageUrl()).into(holder.profile);
        }


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
}



class NotificationViewHolder extends RecyclerView.ViewHolder {

    CircleImageView profile;
    TextView NotificationText, TimeText;
    ConstraintLayout cardBg;

    public NotificationViewHolder(@NonNull View itemView) {
        super(itemView);

        profile = itemView.findViewById(R.id.profilePicNotification);
        NotificationText = itemView.findViewById(R.id.NotificationText);
        TimeText = itemView.findViewById(R.id.timetext);
        cardBg = itemView.findViewById(R.id.CardBgNotification);
    }

    public String TimeDifference(long seconds){

        int minuts = 60;
        int hours = 60 * minuts;
        int days = 24*hours;
        int week = 7 * days;
        long seconds2 = seconds+3600;

        if (seconds < minuts){
            return "seconds.";
        }
        else
            if (60<seconds && seconds<120){
            return ("1 minute.");
        }else
            if (120 < seconds && seconds < hours){
            return String.valueOf(seconds/minuts) + " minutes.";
        }else

            if (7200 < seconds2 && seconds2 < 3*days){
                return String.valueOf(seconds2/hours) + " hours.";
            }else

                if (3*days < seconds2 && seconds2 < week){
                        return String.valueOf(seconds2/days) + " days.";
                    }else
                        if (week < seconds2 && seconds2 < 14*days){
                            return "1 week.";
                        }else
                            if (14*days < seconds2 && seconds2 < week*4){
                                return String.valueOf(seconds2/week) + " weeks.";
                            }

                            return "a while";


    }

}
