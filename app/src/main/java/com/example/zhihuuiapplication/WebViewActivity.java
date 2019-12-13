package com.example.zhihuuiapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.zhihuuiapplication.adapters.WebViewPagerAdapter;
import com.example.zhihuuiapplication.bean.Bean;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WebViewActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private List<String> urls = new ArrayList<>();
    private int position;
    private WebViewPagerAdapter webViewPagerAdapter;
    private OkHttpClient client = new OkHttpClient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        Intent intent = getIntent();
        final Bundle data = intent.getExtras();
        if(data!=null) {
            for (int i = 0; i <= data.size() - 2; i++) {
                urls.add(data.getString("url" + i));
            }
            position = data.getInt("position");
            viewPager = findViewById(R.id.web_vp);
            webViewPagerAdapter=new WebViewPagerAdapter(this, urls);
            viewPager.setAdapter(webViewPagerAdapter);
            viewPager.setCurrentItem(position);

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    if(data.size()>5&position==webViewPagerAdapter.getCount()-1){
                        addData();
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }else {
            new Thread(){
                @Override
                public void run() {
                    Looper.prepare();
                    Toast.makeText(WebViewActivity.this, "网络请求失败，请检查网络设置",
                            Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }.start();
            Intent intent2 = new Intent(this, MainActivity.class);
            this.startActivity(intent2);
        }


    }

    private void addData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bundle data=new Bundle();
                    data.putString("bean",WebViewActivity.this.run("https://news-at.zhihu.com/api/3/news/before/"+getDate(position/5+1)));
                    Message message = handler.obtainMessage(1, data);
                    handler.sendMessage(message);
                } catch (IOException e) {
                    new Thread(){
                        @Override
                        public void run() {
                            Looper.prepare();
                            Toast.makeText(WebViewActivity.this, "网络请求失败，请检查网络设置",
                                    Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }.start();
                }
            }
        }).start();
        position+=5;
    }

    public String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        if (response.body() != null) {
            return response.body().string();
        }else {
            new Thread(){
                @Override
                public void run() {
                    Looper.prepare();
                    Toast.makeText(WebViewActivity.this, "网络请求异常，请检查网络设置",
                            Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }.start();
            return null;
        }
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                Bundle bundle = (Bundle) msg.obj;
                if (bundle != null) {
                    Bean bean = new Gson().fromJson(bundle.getString("bean"), Bean.class);
                    for(int i=0;i<=4;i++){
                        webViewPagerAdapter.addUrl(bean.getStories().get(i).getUrl());
                    }
                }
            }
            return false;
        }
    });

    @SuppressLint("SimpleDateFormat")
    private String getDate(int otherdate) {
        //获取当前需要加载的数据的日期
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_MONTH, -otherdate);//otherdate天前的日子
        //将日期转化为20170520这样的格式
        return new SimpleDateFormat("yyyyMMdd").format(c.getTime());
    }
}

