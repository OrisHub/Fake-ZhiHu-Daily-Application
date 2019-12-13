package com.example.zhihuuiapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.zhihuuiapplication.adapters.ListAdapter;
import com.example.zhihuuiapplication.bean.Bean;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private List<Bean> beans = new ArrayList<>();
    private ListAdapter listAdapter;
    private OkHttpClient client = new OkHttpClient();
    private int k = 2;
    private String root = "https://news-at.zhihu.com/api/3/news/before/";
    private Calendar lastOnLordTime;
    private Boolean IF_THE_BLOCK_CLEARED=true;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar tb =findViewById(R.id.tb);
        setSupportActionBar(tb);
        TextView tbDay=tb.findViewById(R.id.TBday);
        TextView tbMonth=tb.findViewById(R.id.TBmonth);
        tbDay.setText(getDate(0).substring(6));
        final String[] num = {"壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖", "拾", "拾壹", "拾贰"};
        tbMonth.setText(num[Integer.parseInt(getDate(0).substring(4,6))-1]+"月");


        RecyclerView rv = findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv.setLayoutManager(linearLayoutManager);

        List<String> strings = new ArrayList<>();
        strings.add("");
        Bean.StoriesBean storiesBean = new Bean.StoriesBean("", "", "", "", "", 0, 0, strings);
        Bean.TopStoriesBean topStoriesBean = new Bean.TopStoriesBean("", "", "", "", "", "", 0, 0);
        List<Bean.StoriesBean> storiesBeans = new ArrayList<>();
        List<Bean.TopStoriesBean> topStoriesBeans = new ArrayList<>();
        storiesBeans.add(storiesBean);
        topStoriesBeans.add(topStoriesBean);
        Bean bean = new Bean("", storiesBeans, topStoriesBeans);
        beans.add(bean);

        listAdapter = new ListAdapter(this, beans);
        rv.setAdapter(listAdapter);

        final SwipeRefreshLayout refreshLayout = findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
                refreshLayout.setRefreshing(false);
            }
        });

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int totalItemCount;
            private int lastVisibleItem;

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    totalItemCount = layoutManager.getItemCount();
                    lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                    if (lastVisibleItem>=totalItemCount-1) {
                        onLoadMore();
                    }
                }else {
                    new Thread(){
                        @Override
                        public void run() {
                            Looper.prepare();
                            Toast.makeText(MainActivity.this, "很抱歉,程序出现异常,即将退出.",
                                    Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }.start();
                }

            }

            private void onLoadMore() {
                if(IF_THE_BLOCK_CLEARED) {
                    addData();
                    lastOnLordTime=Calendar.getInstance();
                    IF_THE_BLOCK_CLEARED=false;
                }else {
                    if(Calendar.getInstance().getTimeInMillis()-lastOnLordTime.getTimeInMillis()>=500){
                        IF_THE_BLOCK_CLEARED=true;
                    }
                }
            }

        });
        initData();
    }

    public void addData() {
        getDataFromServer(root + getDate(k ),root + getDate(k+1 ),root + getDate(k+2 ));
        k += 3;
    }


    private void initData() {
        if (beans.size() == 1) {
            getDataFromServer("https://news-at.zhihu.com/api/4/news/latest",root + getDate(0),root + getDate(1));
            k=2;
        }

    }

    private void refreshData() {
        Bean bean0 = beans.get(0);
        beans.clear();
        beans.add(bean0);
        listAdapter.clearAll();
        listAdapter.addItems(bean0);
        initData();
    }

    private void getDataFromServer(final String url1,final String url2,final String url3) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bundle data=new Bundle();
                    data.putString("bean1",MainActivity.this.run(url1));
                    data.putString("bean2",MainActivity.this.run(url2));
                    data.putString("bean3",MainActivity.this.run(url3));
                    Message message = handler.obtainMessage(1, data);
                    handler.sendMessage(message);
                } catch (IOException e) {
                    new Thread(){
                        @Override
                        public void run() {
                            Looper.prepare();
                            Toast.makeText(MainActivity.this, "网络请求失败，请检查网络设置",
                                    Toast.LENGTH_SHORT).show();
                            Looper.loop();
                        }
                    }.start();
                }
            }
        }).start();
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
                    Toast.makeText(MainActivity.this, "网络请求异常，请检查网络设置",
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
                    for(int i=1;i<=3;i++){
                        Bean bean = new Gson().fromJson(bundle.getString("bean"+i), Bean.class);
                        bean.getStories().get(0).setType(1);
                        beans.add(bean);
                        listAdapter.addItems(bean);
                    }
                }
            }
            return false;
        }
    });

    private String getDate(int otherdate) {
        //获取当前需要加载的数据的日期
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        c.setTime(new Date());
        c.add(Calendar.DAY_OF_MONTH, -otherdate);//otherdate天前的日子
        //将日期转化为20170520这样的格式
        @SuppressLint("SimpleDateFormat") String date = new SimpleDateFormat("yyyyMMdd").format(c.getTime());
        return date;
    }

}
