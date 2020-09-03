package com.cproz.pantomath_admin.Home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.cproz.pantomath_admin.R;

import java.util.Timer;
import java.util.TimerTask;

public class ProfilePopup extends DialogFragment{

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.profile_pop_up, container,false);




        return root;
    }

}
