package com.cproz.pantomath_admin.ManageTeachers.AddTeacher;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.cproz.pantomath_admin.ManageTeachers.ManageTeachers;
import com.cproz.pantomath_admin.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class CreatAccTeacher extends AppCompatActivity implements Spinner.OnItemSelectedListener {


    EditText Name, Email, Password, ConfirmPassword, Phone;
    CircleImageView ProfilePic;
    Toolbar toolbar;
    Spinner spinner;
    Button CreatAccount;
    Uri mCropImageUri;
    String decision;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    FirebaseStorage firebaseStorage;
    ProgressBar progressBar;
    String Citys;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creat_acc_teacher);
        Initialization();

        final String[] City = {"Aurangabad", "Nashik"};

        final ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(City));

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.style_spinner,arrayList);
        spinner.setAdapter(arrayAdapter);



        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Citys = City[position];
                //Toast.makeText(AddTeacher.this, SUBJECT, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Citys = "";
            }
        });


        ProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectImageClick(v);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        progressBar.setVisibility(View.GONE);
        firebaseStorage = FirebaseStorage.getInstance();


        CreatAccount.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if (mCropImageUri == null){
                    Toast.makeText(CreatAccTeacher.this, "Please select profile picture", Toast.LENGTH_SHORT).show();
                }else if (Email.getText().toString().isEmpty()&&Password.getText().toString().isEmpty()&&ConfirmPassword.getText().toString().isEmpty()){
                    Name.setError("All Fields are required");
                    Name.requestFocus();

                }else if (!Password.getText().toString().equals(ConfirmPassword.getText().toString())){
                    Password.setError("Password and Confirm Password must be same");
                    Password.requestFocus();
                }
                else{
                    CreatAccount.setEnabled(false);
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(100, true);

                    //SignupNow(final String Email, String Password, String Board, String Class, final String Subject, final String Namex, final String City)

                    String Names = Name.getText().toString();
                    String Emails = Email.getText().toString();
                    String Passwords = Password.getText().toString();
                    String Boards = AddTeacher.BOARD;
                    final String Classes = AddTeacher.CLASS;
                    String Subjects = AddTeacher.SUBJECT;
                    String Numbers = Phone.getText().toString();





                    SignupNow(Emails, Passwords, Boards, Classes, Subjects,Names, Citys, Numbers);

                }

            }
        });


    }

    private void Initialization() {

        Name = findViewById(R.id.Name);
        Email = findViewById(R.id.EmailAddTeacher);
        Password = findViewById(R.id.PasswordAddTeacher);
        ConfirmPassword = findViewById(R.id.ConfirmPasswordAddTeacher);
        ProfilePic = findViewById(R.id.ProfilePicture_profile);
        toolbar = findViewById(R.id.AddTeacherProfileToolBar);
        spinner = findViewById(R.id.SelectCitySpinner);
        CreatAccount = findViewById(R.id.CreatAccTeacher);
        progressBar = findViewById(R.id.progressBarx);
        Phone = findViewById(R.id.PhoneNumber);

    }

    @SuppressLint("SetTextI18n")
    public void GalleryOrCamera(String actionx) {
        String action;
        action = actionx;

        if (action == null) {
            System.out.println("Wait");
        } else if (action.equals("gallery")) {
            decision = "gallery";


        } else if (action.equals("camera")) {
            decision = "camera";
        }
    }



    public void onSelectImageClick(View view) {
        //CropImage.startPickImageActivity(this);
        CropImage.activity().start(this);


    }




    @Override
    @SuppressLint({"NewApi", "SetTextI18n"})
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // handle result of pick image chooser
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri imageUri = CropImage.getPickImageResultUri(this, data);

            // For API >= 23 we need to check specifically that we have permissions to read external storage.
            if (CropImage.isReadExternalStoragePermissionsRequired(this, imageUri)) {
                // request permissions and handle the result in onRequestPermissionsResult()
                mCropImageUri = imageUri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            } else {
                // no permissions required or already grunted, can start crop image activity
                startCropImageActivity(imageUri);
            }
        }

        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                mCropImageUri = result.getUri();

                ProfilePic.setImageURI(mCropImageUri);
                //SelectPhotoText.setText("Change Photo");








            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (mCropImageUri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // required permissions granted, start crop image activity
            startCropImageActivity(mCropImageUri);
        }
        else {
            Toast.makeText(this, "Cancelling, required permissions are not granted", Toast.LENGTH_LONG).show();
        }
    }


    private void startCropImageActivity(Uri imageUri) {
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }


    public void SignupNow(final String Email, String Password, final String Board, final String Class, final String Subject, final String Namex, final String City, final String Number){
        firebaseAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){

                    final String userId = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail();

                    //profile picture upload........\
                    StorageReference storageReference = firebaseStorage.getReference();

                    final StorageReference reference = storageReference.child("ProfilePictures/" + userId + "/profile.jpg");


                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(CreatAccTeacher.this.getContentResolver(), mCropImageUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    assert bitmap != null;
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
                    byte[] data = baos.toByteArray();


                    final Date SignupTime = new Date();

                    reference.putBytes(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String ProfileURL = uri.toString();
                                    SignupInfoCarrier signupInfoCarrier = new SignupInfoCarrier(
                                            toTitleCase(Namex), Email,Number,City,"Active",Board,Class, userId, ProfileURL
                                            , SignupTime, Subject
                                    );
                                    firebaseFirestore.document("Users/Teachers/Teacherinfo/" + userId ).set(signupInfoCarrier).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {


                                            //startActivity(new Intent(CreatAccTeacher.this, ManageTeachers.class));
                                            Intent intent = new Intent(CreatAccTeacher.this, ManageTeachers.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(intent);
                                        }
                                    })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(CreatAccTeacher.this, "Signup Failed", Toast.LENGTH_SHORT).show();
                                                    System.out.println("Document upload failed");
                                                    progressBar.setVisibility(View.GONE);
                                                    CreatAccount.setEnabled(true);
                                                }
                                            });
                                }
                            });
                        }
                    });







                }
                else
                {
                    Toast.makeText(CreatAccTeacher.this, "Signup Failed", Toast.LENGTH_SHORT).show();
                    System.out.println("password login failed");
                    CreatAccount.setEnabled(true);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreatAccTeacher.this, "Signup Failed", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed() {
        progressBar.setVisibility(View.GONE);
        super.onBackPressed();
    }
}