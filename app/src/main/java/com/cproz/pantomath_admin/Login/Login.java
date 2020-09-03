package com.cproz.pantomath_admin.Login;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Build;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cproz.pantomath_admin.Home.Dashboard;
import com.cproz.pantomath_admin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    EditText Email, Password;
    Button Login;
    TextView ErrorText, ForgetPassword;

    String EmailString, PasswordString;
    private FirebaseAuth firebaseAuth;
    ProgressBar progressBar;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Initialisation();


        progressBar.setVisibility(View.GONE);
        firebaseAuth = FirebaseAuth.getInstance();
        //Initialization of variables
        Login.setEnabled(true);
        ErrorText.setVisibility(View.GONE);

        Email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ErrorText.setVisibility(View.GONE);
                Email.setBackgroundResource(R.drawable.text_view_bg);
                Password.setBackgroundResource(R.drawable.text_view_bg);
            }
        });

        Password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ErrorText.setVisibility(View.GONE);
                Email.setBackgroundResource(R.drawable.text_view_bg);
                Password.setBackgroundResource(R.drawable.text_view_bg);
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                validation();
            }
        });


        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        assert connectivityManager != null;
        Network networkInfo = connectivityManager.getActiveNetwork();
        if (networkInfo == null){
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    private void validation() {

        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(100, true);

        EmailString = Email.getText().toString().trim();
        PasswordString = Password.getText().toString();

        if (EmailString.isEmpty()&&PasswordString.isEmpty()){
            ErrorText.setVisibility(View.VISIBLE);
            Email.setBackgroundResource(R.drawable.error_text_field_bg);
            Password.setBackgroundResource(R.drawable.error_text_field_bg);
            Email.requestFocus();
            progressBar.setVisibility(View.GONE);

        }
        else if (EmailString.isEmpty()){
            ErrorText.setVisibility(View.VISIBLE);
            Email.setBackgroundResource(R.drawable.error_text_field_bg);
            Email.requestFocus();
            Password.setBackgroundResource(R.drawable.text_view_bg);
            progressBar.setVisibility(View.GONE);
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(EmailString).matches()){
            ErrorText.setVisibility(View.VISIBLE);
            Password.setBackgroundResource(R.drawable.text_view_bg);
            ErrorText.setText("Make sure your email is correct.");
            Email.setBackgroundResource(R.drawable.error_text_field_bg);
            Email.requestFocus();
            progressBar.setVisibility(View.GONE);

        }
        else if (PasswordString.isEmpty()){
            ErrorText.setVisibility(View.VISIBLE);
            ErrorText.setText("Please enter the password");
            Email.setBackgroundResource(R.drawable.text_view_bg);
            Password.setBackgroundResource(R.drawable.error_text_field_bg);
            Password.requestFocus();
            progressBar.setVisibility(View.GONE);
        }
        else
        {
            Login.setEnabled(false);
            Authentication(EmailString, PasswordString);

        }

    }

    private void Authentication(final String Email, final String Password) {

        firebaseAuth.signInWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    startActivity(new Intent(Login.this, Dashboard.class));//new intent for change activity

                }
                else{
                    Login.setEnabled(true);
                    progressBar.setVisibility(View.GONE);
//                    Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();//toast for invalid entries;;

                    ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

                    assert connectivityManager != null;
                    Network networkInfo = connectivityManager.getActiveNetwork();
                    if (networkInfo == null){
                        Toast.makeText(Login.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();//toast for invalid entries;;
                    }

//                    System.out.println(Email + " " + Password);
                }
            }
        });


    }

    private void Initialisation() {

        progressBar = findViewById(R.id.progressBarLogin);
        Email = findViewById(R.id.EmailText);
        Password = findViewById(R.id.passwordLogin);
        ErrorText = findViewById(R.id.errorTextLogin);

        Login = findViewById(R.id.LoginButt);

    }

    @Override
    public void onBackPressed() {

    }
}