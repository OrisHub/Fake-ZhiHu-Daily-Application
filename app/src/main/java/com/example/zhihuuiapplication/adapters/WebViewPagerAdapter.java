package com.example.zhihuuiapplication.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.zhihuuiapplication.R;

import java.util.ArrayList;
import java.util.List;

public class WebViewPagerAdapter extends PagerAdapter {
    private List<String> urls = new ArrayList<>();
    private Context context;

    public WebViewPagerAdapter(Context context, List<String> urls) {
        this.context = context;
        this.urls.addAll(urls);
    }

    @Override
    public int getCount() {
        return urls.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = View.inflate(context, R.layout.web_vp_item, null);

        WebView webView = view.findViewById(R.id.web_vp_item);
        webView.loadUrl(urls.get(position));

        container.addView(view);
        return view;
    }

    public void addUrl(String url){
        urls.add(url);
        notifyDataSetChanged();
    }

}
