package com.example.zhihuuiapplication.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.zhihuuiapplication.R;
import com.example.zhihuuiapplication.WebViewActivity;
import com.example.zhihuuiapplication.bean.Bean;
import com.example.zhihuuiapplication.bean.ImageUtil;

import java.util.ArrayList;
import java.util.List;


public class ListAdapter extends RecyclerView.Adapter {
    private final Context context;
    private List<Bean> orginBeans = new ArrayList<>();
    private List<Bean.StoriesBean> storiesBeans = new ArrayList<>();
    private final LayoutInflater inflater;
    private final static int ITEM_TYPE_FAKE = -1;
    private final static int ITEM_TYPE_HEADER = 0;
    private final static int ITEM_TYPE_CARD = 1;


    public ListAdapter(Context context, List<Bean> beans) {
        this.context = context;
        this.orginBeans.addAll(beans);
        inflater = LayoutInflater.from(context);
        storiesBeans.addAll(beans.get(0).getStories());
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return ITEM_TYPE_FAKE;
        } else if (position == 1) {
            return ITEM_TYPE_HEADER;
        } else {
            return ITEM_TYPE_CARD;
        }

    }

    @NonNull
    @Override
//    获取控件
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == ITEM_TYPE_HEADER) {
            itemView = LayoutInflater.from(context).inflate(R.layout.vp, parent, false);
            return new HeaderViewHolder(itemView);
        } else if (viewType == ITEM_TYPE_CARD) {
            itemView = LayoutInflater.from(context).inflate(R.layout.recycler_layout, parent, false);
            return new CardViewHolder(itemView);
        } else {
            itemView = LayoutInflater.from(context).inflate(R.layout.fake_vp_item, parent, false);
            return new FakeViewHolder(itemView);
        }
    }


    @Override
//    加数据，绑定
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
            ((HeaderViewHolder) holder).bindData(orginBeans.get(1).getTop_stories());
        } else if (holder instanceof FakeViewHolder) {
            ((FakeViewHolder) holder).bindData();
        } else {
            ((CardViewHolder) holder).bindData(storiesBeans.get(position - 1), position);
        }
    }

    @Override
//    获取需渲染item数量
    public int getItemCount() {
        return orginBeans.size() == 1 ? storiesBeans.size() : storiesBeans.size() + 1;
    }

    //    展现形式确立
    public class CardViewHolder extends RecyclerView.ViewHolder {
        private final CardView cardView;

        CardViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cv);
        }

        @SuppressLint("SetTextI18n")
        void bindData(Bean.StoriesBean bean, final int position) {
            ImageView imageView = cardView.findViewById(R.id.image_rv);
            TextView tv_title = cardView.findViewById(R.id.title_rv);
            TextView tv_hint = cardView.findViewById(R.id.hint_rv);
            TextView tv_time = cardView.findViewById(R.id.time_rv);
            TextView tv_line = cardView.findViewById(R.id.line_rv);

            ImageUtil.show((Activity) context, imageView, bean.getImages().get(0));
            tv_hint.setText(bean.getHint());
            String title=bean.getTitle();
            if(title.length()>=22){
                title=title.substring(0,22)+"……";
            }
            tv_title.setText(title);
            if (bean.getType() == 1 & position >= 3) {
                String date = orginBeans.get(2 + (position - 1 - orginBeans.get(1).getStories().size()) / 5).getDate();
                tv_time.setText(date.substring(4, 6) + "月" + date.substring(6) + "日");
                tv_time.setTextColor(0xFFAAAAAA);
                tv_line.setBackgroundColor(0xFFAAAAAA);
            } else {
                tv_time.setTextColor(0x00FFFFFF);
                tv_line.setBackgroundColor(0x00FFFFFF);
            }

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    for (int i = 0; i <= storiesBeans.size() - 3; i++) {
                        bundle.putString("url" + (i), storiesBeans.get(i+1).getUrl());
                    }
                    bundle.putInt("position", position-2);
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
    }

    public class FakeViewHolder extends RecyclerView.ViewHolder {
        FakeViewHolder(View itemView) {
            super(itemView);
        }
        void bindData() {
        }
    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private final ViewPager viewPager;
        private TextView[] tvs=new TextView[5];
        HeaderViewHolder(View itemView) {
            super(itemView);
            viewPager = itemView.findViewById(R.id.vp);
            tvs[0]=itemView.findViewById(R.id.vp1);
            tvs[1]=itemView.findViewById(R.id.vp2);
            tvs[2]=itemView.findViewById(R.id.vp3);
            tvs[3]=itemView.findViewById(R.id.vp4);
            tvs[4]=itemView.findViewById(R.id.vp5);
        }

        void bindData(List<Bean.TopStoriesBean> vpBeans) {
            viewPager.setAdapter(new MyPagerAdapter(context, vpBeans));
            viewPager.setCurrentItem(40000);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    int position_ture=position%5;
                    for (int i=0;i<=4;i++){
                        if(i==position_ture){
                            tvs[i].setTextColor(Color.parseColor("#000000"));
                        }else {
                            tvs[i].setTextColor(Color.parseColor("#FFFFFF"));
                        }
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }


    public void addItems(Bean bean) {
        orginBeans.add(bean);
        storiesBeans.addAll(bean.getStories());
        notifyDataSetChanged();
    }


    public void clearAll() {
        orginBeans.clear();
        storiesBeans.clear();
    }
}
