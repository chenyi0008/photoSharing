package com.example.photosharing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.photosharing.fragment.ImmerseFragment;
import com.example.photosharing.fragment.PersonFragment;
import com.example.photosharing.fragment.TestFragment;
import com.example.photosharing.model.ShareDetailItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private ImmerseFragment immerseFragment;
    private PersonFragment personFragment;
    private TestFragment testFragment;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        personFragment = new PersonFragment();
        testFragment = new TestFragment();
        immerseFragment = new ImmerseFragment();

        frameLayout = findViewById(R.id.home_fragment);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(itemSelectedListener);

        switchFragment(immerseFragment);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener itemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();
            if (itemId == R.id.immerse) {
                switchFragment(immerseFragment);
                return true;
            } else if (itemId == R.id.test) {
                switchFragment(testFragment);
                return true;
            } else if (itemId == R.id.person) {
                switchFragment(personFragment);
                return true;
            }
            return false;
        }
    };

    private void switchFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.home_fragment, fragment).commit();
    }
}