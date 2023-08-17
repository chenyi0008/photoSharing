package com.example.photosharing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.photosharing.api.ImmerseFragment;
import com.example.photosharing.api.PersonFragment;
import com.example.photosharing.api.TestFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FrameLayout frameLayout;
    private ImmerseFragment immerseFragment;
    private PersonFragment personFragment;
    private TestFragment testFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        immerseFragment = new ImmerseFragment();
        personFragment = new PersonFragment();
        testFragment = new TestFragment();

        frameLayout = findViewById(R.id.home_fragment);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(itemSelectedListener);

        switchFragment(personFragment);
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
        transaction.replace(R.id.home_fragment, fragment).commitNow();
    }
}