package com.example.zhihuuiapplication.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.zhihuuiapplication.R;
import com.example.zhihuuiapplication.WebViewActivity;
import com.example.zhihuuiapplication.bean.Bean;
import com.example.zhihuuiapplication.bean.ImageUtil;

import java.util.List;

public class MyPagerAdapter extends PagerAdapter {
    private List<Bean.TopStoriesBean> beans;
    private Context context;

    public MyPagerAdapter(Context context, List<Bean.TopStoriesBean> beans) {
        this.context = context;
       // Log.e("woggle",beans.toString());
        this.beans = beans;
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
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
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        Log.e("woggle",beans.toString());
        assert beans!=null;
        final int position_true = position % beans.size();
        View view = View.inflate(context, R.layout.vp_item, null);

        ImageView imageView = view.findViewById(R.id.image_vp);
        TextView tv_title = view.findViewById(R.id.title_vp);
        TextView tv_hint = view.findViewById(R.id.hint_vp);
        TextView gradientBG = view.findViewById(R.id.gradientbg);

        ImageUtil.show((Activity) context, imageView, beans.get(position_true).getImage());
        GradientDrawable drawable = (GradientDrawable) gradientBG.getBackground();
        int[] colors = {Color.parseColor("#" + beans.get(position_true).getImage_hue().substring(2)), Color.parseColor("#" + beans.get(position_true).getImage_hue().substring(2)), 0x00FFFFFF};
        drawable.setColors(colors);
        gradientBG.setBackground(drawable);
        tv_hint.setText(beans.get(position_true).getHint());
        String title=beans.get(position_true).getTitle();
        if(title.length()>=20){
            title=title.substring(0,20)+"……";
        }
        tv_title.setText(title);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                for (int i = 0; i <= 4; i++) {
                    bundle.putString("url" + i, beans.get(i).getUrl());
                }
                bundle.putInt("position", position_true);
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        container.addView(view);
        return view;
    }
}