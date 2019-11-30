package com.example.zhihuuiapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.zhihuuiapplication.adapters.WebViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class WebViewActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private List<String> urls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        assert data != null;
        for (int i = 0; i <= data.size() - 2; i++) {
            urls.add(data.getString("url" + i));
        }
        int position = data.getInt("position");

        viewPager = findViewById(R.id.web_vp);
        viewPager.setAdapter(new WebViewPagerAdapter(this, urls));
        if (urls.size() == 5) {
            viewPager.setCurrentItem(position);
        } else {
            viewPager.setCurrentItem(position + 3);
        }

    }
}

