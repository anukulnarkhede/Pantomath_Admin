package com.cproz.pantomath_admin.AllDoubts;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.cproz.pantomath_admin.Home.CancelReport;
import com.cproz.pantomath_admin.Home.Dashboard;
import com.cproz.pantomath_admin.Home.Image;
import com.cproz.pantomath_admin.Home.ViewPagerAdapterx;
import com.cproz.pantomath_admin.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoubtDetails extends AppCompatActivity {

    Toolbar toolbar;
    CircleImageView StudentProfilePic, TeacherProfilePic;
    TextView StudentUserName, TeacherUserName, TimeText1, TimeText2, QuestionText, AnswerText, Solved, classBoardSubject;
    ViewPager viewPager, viewPagerAns;
    ImageView SolvedIcon, upArrow;
    Button SubjectTag,Play, Pause, DeleteDoubt;
    SeekBar seekBar;
    Chronometer chronometer;
    LinearLayoutCompat linearLayout, AnslinearLayoutCompat,AudioPlayer;
    ConstraintLayout constraintLayout;
    MediaPlayer mediaPlayer;
    Handler handler = new Handler();
    Runnable runnable;
    String AudioUrl;
    Button CancelReportButt;
    Intent intent;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doubt_details);



        Initialisation();
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        Objects.requireNonNull(toolbar.getNavigationIcon()).setColorFilter(getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_ATOP);








        CancelReportButt.setVisibility(View.GONE);

        final Bundle bundle = getIntent().getExtras();
        assert bundle != null;



        constraintLayout.setVisibility(View.GONE);
        upArrow.setVisibility(View.GONE);
        SolvedIcon.setImageResource(R.drawable.ic_round_check_circle_24_grey);
        SolvedIcon.setBackgroundResource(R.drawable.square_small_bg_grey);
        Solved.setText("Unsolved");
        Solved.setTextColor(Color.parseColor("#999999"));

        if (Dashboard.USERDECESION.equals("Admin")){
            DeleteDoubt.setVisibility(View.GONE);
            if (Objects.equals(bundle.getString("Status"), "Solved")){
                constraintLayout.setVisibility(View.VISIBLE);
                upArrow.setVisibility(View.VISIBLE);
                SolvedIcon.setImageResource(R.drawable.ic_round_check_circle_24);
                SolvedIcon.setBackgroundResource(R.drawable.square_small_bg);
                Solved.setText("Solved");
                CancelReportButt.setVisibility(View.GONE);
                Solved.setTextColor(Color.parseColor("#0476D9"));
            }
            else
            if (Objects.equals(bundle.getString("Status"), "Unsolved")){
                constraintLayout.setVisibility(View.GONE);
                upArrow.setVisibility(View.GONE);
                SolvedIcon.setImageResource(R.drawable.ic_round_check_circle_24_grey);
                SolvedIcon.setBackgroundResource(R.drawable.square_small_bg_grey);
                Solved.setText("Unsolved");
                CancelReportButt.setVisibility(View.GONE);
                Solved.setTextColor(Color.parseColor("#999999"));
            }else
            if (Objects.equals(bundle.getString("Status"), "Reported")){
                constraintLayout.setVisibility(View.GONE);
                upArrow.setVisibility(View.GONE);
                SolvedIcon.setVisibility(View.GONE);
                Solved.setVisibility(View.GONE);
                CancelReportButt.setVisibility(View.GONE);
                Solved.setTextColor(Color.parseColor("#999999"));

            }
        }else if (Dashboard.USERDECESION.equals("SuperUser")){
            if (Objects.equals(bundle.getString("Status"), "Solved")){
                constraintLayout.setVisibility(View.VISIBLE);
                upArrow.setVisibility(View.VISIBLE);
                SolvedIcon.setImageResource(R.drawable.ic_round_check_circle_24);
                SolvedIcon.setBackgroundResource(R.drawable.square_small_bg);
                Solved.setText("Solved");
                CancelReportButt.setVisibility(View.GONE);
                Solved.setTextColor(Color.parseColor("#0476D9"));
            }
            else
            if (Objects.equals(bundle.getString("Status"), "Unsolved")){
                constraintLayout.setVisibility(View.GONE);
                upArrow.setVisibility(View.GONE);
                SolvedIcon.setImageResource(R.drawable.ic_round_check_circle_24_grey);
                SolvedIcon.setBackgroundResource(R.drawable.square_small_bg_grey);
                Solved.setText("Unsolved");
                CancelReportButt.setVisibility(View.GONE);
                Solved.setTextColor(Color.parseColor("#999999"));
            }else
            if (Objects.equals(bundle.getString("Status"), "Reported")){
                constraintLayout.setVisibility(View.GONE);
                upArrow.setVisibility(View.GONE);
                SolvedIcon.setVisibility(View.GONE);
                Solved.setVisibility(View.GONE);
                CancelReportButt.setVisibility(View.VISIBLE);
                Solved.setTextColor(Color.parseColor("#999999"));

            }
        }






        switch (Objects.requireNonNull(bundle.getString("Subject"))){
            case "Algebra":

                classBoardSubject.setTextColor(Color.parseColor("#FF2829"));

                break;

            case "Geometry":

                classBoardSubject.setTextColor(Color.parseColor("#9A0D91"));
                break;

            case "Physics":

                classBoardSubject.setTextColor(Color.parseColor("#0078FF"));
                break;

            case "Chemistry":

                classBoardSubject.setTextColor(Color.parseColor("#FF9B00"));
                break;

            case "Biology":

                classBoardSubject.setTextColor(Color.parseColor("#FF1ADD"));
                break;

            case "History":

                classBoardSubject.setTextColor(Color.parseColor("#813912"));
                break;

            case "Geography":

                classBoardSubject.setTextColor(Color.parseColor("#009F37"));
                break;

            case "Languages":

                classBoardSubject.setTextColor(Color.parseColor("#5550B6"));
                break;
        }


        DeleteDoubt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    DoubtDelete doubtDelete = new DoubtDelete();
                    Bundle args = new Bundle();
                    args.putString("uid", bundle.getString("uid"));
                    doubtDelete.setArguments(args);
                    doubtDelete.show(getSupportFragmentManager(), "xsd");
            }
        });







        String BoardClassSub = bundle.getString("Subject") + " . " + bundle.getString("STD") + " " + bundle.getString("Board");
        classBoardSubject.setText(BoardClassSub);

        intent = new Intent(DoubtDetails.this, Image.class);

        StudentUserName.setText(toTitleCase(Objects.requireNonNull(bundle.getString("Name"))));

        if (Objects.equals(bundle.getString("Link"), "")){
            AnswerText.setText(bundle.getString("AnsText"));
        }
        else
        {
            String AnswerTextx = bundle.getString("AnsText") + "\n\nHere's a link for you: " + bundle.getString("Link");

            AnswerText.setText(AnswerTextx);
        }





        if (Objects.equals(bundle.getString("ProfileImage"), "")){
            StudentProfilePic.setImageResource(R.drawable.profile_defult);
        }
        else if (!Objects.equals(bundle.getString("ProfileImage"), "")){
            Picasso.get().load(bundle.getString("ProfileImage")).into(StudentProfilePic);
        }

        if (Objects.equals(bundle.getString("TeacherImage"), "")){
            TeacherProfilePic.setImageResource(R.drawable.profile_defult);
        }
        else
        if (!Objects.equals(bundle.getString("TeacherImage"), "")){
            Picasso.get().load(bundle.getString("TeacherImage")).into(TeacherProfilePic);
        }

        QuestionText.setText(bundle.getString("QuestionText"));





        if (bundle.getString("AudioUrl").equals("")){
            AudioPlayer.setVisibility(View.GONE);
        }else if (!bundle.getString("AudioUrl").equals("")){
            AudioPlayer.setVisibility(View.VISIBLE);
            Play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StreamAudio(bundle);
                    Play.setVisibility(View.GONE);
                    Pause.setVisibility(View.VISIBLE);
                }
            });


        }

        Pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.pause();
                handler.removeCallbacks(runnable);
                Play.setVisibility(View.VISIBLE);
                Pause.setVisibility(View.GONE);
            }
        });







        try {
            Date QuestionTime = (Date) bundle.get("QuestionDate");

            assert QuestionTime != null;
            long milie = QuestionTime.getTime();

            final String QuestionTimex = DateUtils.getRelativeTimeSpanString(milie).toString();
            TimeText1.setText(QuestionTimex);
        }catch (Exception e){
            System.out.println(e);
        }



        try {
            Date dateTimex = (Date) bundle.get("DateTime");

            assert dateTimex != null;
            long milix = dateTimex.getTime();

            final String DateTime = DateUtils.getRelativeTimeSpanString(milix).toString();


            TimeText2.setText(DateTime);
        }
        catch (Exception e){
            System.out.println("error occured "+e);
        }






        CancelReportButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelReport cancelReport = new CancelReport();
                Bundle args = new Bundle();
                args.putString("uid", bundle.getString("uid"));
                cancelReport.setArguments(args);
                cancelReport.show(getSupportFragmentManager(), "xsd");
            }
        });


        TeacherUserName.setText(bundle.getString("Teacher"));


        String[] imageUrls = new String[0];


        if (Objects.equals(bundle.getString("QuestionImage1Url"), "") && Objects.equals(bundle.getString("QuestionImage2Url"), "")){
            viewPager.setVisibility(View.GONE);
            linearLayout.setVisibility(View.GONE);

        }
        else if (!Objects.equals(bundle.getString("QuestionImage1Url"), "") && Objects.equals(bundle.getString("QuestionImage2Url"), "")){
            viewPager.setVisibility(View.VISIBLE);

            imageUrls = new String[] {
                    bundle.getString("QuestionImage1Url")
            };

            ViewPagerAdapterx viewPagerAdapterx = new ViewPagerAdapterx(this, imageUrls);
            viewPager.setAdapter(viewPagerAdapterx);
            linearLayout.setVisibility(View.GONE);

        }
        else if (!Objects.equals(bundle.getString("QuestionImage1Url"), "") && !Objects.equals(bundle.getString("QuestionImage2Url"), "")){


            viewPager.setVisibility(View.VISIBLE);

            imageUrls = new String[] {
                    bundle.getString("QuestionImage1Url"),  bundle.getString("QuestionImage2Url")
            };

            ViewPagerAdapterx viewPagerAdapterx = new ViewPagerAdapterx(this, imageUrls);
            viewPager.setAdapter(viewPagerAdapterx);
            linearLayout.setVisibility(View.VISIBLE);


        }



        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0){
                    linearLayout.setBackgroundResource(R.drawable.ic_dot_first_photo);



                    intent.putExtra("Photo1", bundle.getString("QuestionImage1Url"));




                }
                else if (position == 1){
                    linearLayout.setBackgroundResource(R.drawable.ic_dots_second_photo);

                    intent.putExtra("Photo1", bundle.getString("QuestionImage2Url"));



                }





            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        String[] imageUrls1 = new String[0];


        if (Objects.equals(bundle.getString("AnsImage1Url"), "") && Objects.equals(bundle.getString("AnsImage2Url"), "")){
            viewPagerAns.setVisibility(View.GONE);
            AnslinearLayoutCompat.setVisibility(View.GONE);

        }
        else if (!Objects.equals(bundle.getString("AnsImage1Url"), "") && Objects.equals(bundle.getString("AnsImage2Url"), "")){
            viewPagerAns.setVisibility(View.VISIBLE);

            imageUrls1 = new String[] {
                    bundle.getString("AnsImage1Url")
            };

            ViewPagerAdapterx viewPagerAdapterx = new ViewPagerAdapterx(this, imageUrls1);
            viewPagerAns.setAdapter(viewPagerAdapterx);
            AnslinearLayoutCompat.setVisibility(View.GONE);

        }
        else if (!Objects.equals(bundle.getString("AnsImage1Url"), "") && !Objects.equals(bundle.getString("AnsImage2Url"), "")){


            viewPagerAns.setVisibility(View.VISIBLE);

            imageUrls1 = new String[] {
                    bundle.getString("AnsImage1Url"),  bundle.getString("AnsImage2Url")
            };

            ViewPagerAdapterx viewPagerAdapterx = new ViewPagerAdapterx(this, imageUrls1);
            viewPagerAns.setAdapter(viewPagerAdapterx);
            AnslinearLayoutCompat.setVisibility(View.VISIBLE);


        }

        viewPagerAns.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0){
                    AnslinearLayoutCompat.setBackgroundResource(R.drawable.ic_dot_first_photo);
                }
                else if (position == 1){
                    AnslinearLayoutCompat.setBackgroundResource(R.drawable.ic_dots_second_photo);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



    }



    public void Initialisation(){
        toolbar = findViewById(R.id.DoubtDetailsToolBar);
        StudentProfilePic = findViewById(R.id.ProfilePictureDoubtsDetails);
        TeacherProfilePic = findViewById(R.id.ProfilePictureTeacherDoubtsDetails);
        StudentUserName = findViewById(R.id.NameHolderDoubtDetails);
        TeacherUserName = findViewById(R.id.NameHolderDoubtDetailsTeacher);
        TimeText1 = findViewById(R.id.timeTextDD);
        TimeText2 = findViewById(R.id.timeTextDDTeacher);
        QuestionText = findViewById(R.id.QuestionTextDD);
        AnswerText = findViewById(R.id.AnswerTextDD);
        Solved = findViewById(R.id.solvedTextDD);
        viewPager = findViewById(R.id.imageSliderDD);
        SolvedIcon = findViewById(R.id.solvedDD);
        linearLayout = findViewById(R.id.linearLayout_dotsIndicator_DD);
        constraintLayout = findViewById(R.id.cardHomeDD);
        upArrow = findViewById(R.id.UpArrow);
        viewPagerAns = findViewById(R.id.AnsimageSliderDD);
        AnslinearLayoutCompat = findViewById(R.id.AnslinearLayout_dotsIndicator_DD);
        AudioPlayer = findViewById(R.id.TimerRecordDD);
        seekBar = findViewById(R.id.audioSeekBarDD);
        Play = findViewById(R.id.PlayPauseDD);
        Pause = findViewById(R.id.PauseDD);
        chronometer = findViewById(R.id.timerDD);
        classBoardSubject = findViewById(R.id.toolbar_SubjectClassBoard);
        DeleteDoubt = findViewById(R.id.DeleteDoubt);
        CancelReportButt = findViewById(R.id.CancelReport);

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


    public void StreamAudio(Bundle bundle){



        runnable = new Runnable() {
            @Override
            public void run() {
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                handler.postDelayed(this, 00);


            }
        };

        AudioUrl = bundle.getString("AudioUrl");
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


        try {
            mediaPlayer.setDataSource(AudioUrl);
            //loding
            mediaPlayer.prepare();
            int duration = mediaPlayer.getDuration();
            @SuppressLint("DefaultLocale") String time = String.format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(duration),
                    TimeUnit.MILLISECONDS.toSeconds(duration) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration))
            );
            chronometer.setText(time);

        } catch (IOException e) {
            e.printStackTrace();
        }


        mediaPlayer.start();
        seekBar.setMax(mediaPlayer.getDuration()-90);
        handler.postDelayed(runnable, 0);
        mediaPlayer.setOnCompletionListener(cListener);





    }



    MediaPlayer.OnCompletionListener cListener = new MediaPlayer.OnCompletionListener(){

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void onCompletion(MediaPlayer mp){

            Pause.setVisibility(View.GONE);
            Play.setVisibility(View.VISIBLE);
            //seekBar.setProgress(1);

        }
    };




    @Override
    protected void onResume() {


        super.onResume();


    }

    @Override
    protected void onPause() {
        try {
            mediaPlayer.stop();
        }catch (Exception e){
            System.out.println(e);
        }
        super.onPause();
    }



}