package com.example.photosharing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.photosharing.api.ImmerseFragment;
import com.example.photosharing.api.PersonFragment;
import com.example.photosharing.api.TestFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ModifyInformationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifyinformation);

    }

}