package com.example.photosharing;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
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
        transaction.replace(R.id.home_fragment, fragment).commitNow();
    }


    public void openDetailActivity(View view) {
        // 创建Intent
        Intent intent = new Intent(this, ShareDetailActivity.class);

        // 创建一个示例 ShareDetailItem 对象，你可以替换成实际的数据
        String username = "John";
        String timestamp = "2023-08-23 10:30 AM";
        String content = "This is a test post.";
        List<String> images = Arrays.asList(
                "https://guet-lab.oss-cn-hangzhou.aliyuncs.com/api/2023/08/14/cebe9e74-bf70-41f8-bf7e-d4de0607cdce.jpg",
                "https://guet-lab.oss-cn-hangzhou.aliyuncs.com/api/2023/08/14/c437187d-6160-4f24-b6fc-cdba561faacc.jpg"
        );
        String shareId = "4877";
        String userId = "1691822872735125504";
        ShareDetailItem item = new ShareDetailItem(shareId, userId);

        // 将 ShareDetailItem 对象传递给分享详情页面
        intent.putExtra("friend_circle_item", item);

        // 启动分享详情页面
        startActivity(intent);
    }
}