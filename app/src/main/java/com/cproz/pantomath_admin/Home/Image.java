package com.cproz.pantomath_admin.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.widget.ImageView;

import com.cproz.pantomath_admin.R;
import com.jsibbold.zoomage.ZoomageView;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class Image extends AppCompatActivity {

    ZoomageView imageView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image);




        final Bundle bundle = getIntent().getExtras();
        assert bundle != null;

        imageView = findViewById(R.id.imageView9);

        Picasso.get().load(bundle.getString("Photo1")).into(imageView);



    }
}