package com.hku.msc.fragment.BasicInfo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hku.msc.MainActivity;
import com.hku.msc.R;
import com.hku.msc.view.MyPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentOverview extends Fragment {
    private static final String TAG = "Basic Info > Overview";

    private ViewPager pager;
    private List<View> views;

    //放标签页
    private List<TextView> tvs = new ArrayList<TextView>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.fragment_toolbar);
        toolbar.setTitle("Programme Overview");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//      自定义返回图标
        toolbar.setNavigationIcon(R.drawable.icon_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"toolbar onclick");
                ((MainActivity) getActivity()).goBackView("home");
            }
        });

        initTextView(view);
        initView(inflater);
        initViewPager(view);

        return view;
    }

    public void initTextView(View view) {
        TextView firstView = (TextView) view.findViewById(R.id.firstView);
        firstView.setTextColor(Color.BLUE);
        TextView secondView = (TextView) view.findViewById(R.id.secondView);
        TextView thirdView = (TextView) view.findViewById(R.id.thirdView);
        TextView forthView = (TextView) view.findViewById(R.id.forthView);
        //添加点击事件
        //OnClickListener click=new MyClickListener();
        firstView.setOnClickListener(new MyClickListener(0));
        secondView.setOnClickListener(new MyClickListener(1));
        thirdView.setOnClickListener(new MyClickListener(2));
        forthView.setOnClickListener(new MyClickListener(3));
        tvs.add(firstView);
        tvs.add(secondView);
        tvs.add(thirdView);
        tvs.add(forthView);
    }

    private class MyClickListener implements View.OnClickListener {

        private int index;

        public MyClickListener(int index) {
            this.index = index;
        }

        @Override
        public void onClick(View v) {
            //改变ViewPager当前显示页面
            pager.setCurrentItem(index);
        }
    }

    //初始化ViewPager中显示的数据
    public void initView(LayoutInflater li) {
        views = new ArrayList<View>();
//        LayoutInflater li = getLayoutInflater();
        views.add(li.inflate(R.layout.pageview_overview_first, null));
        views.add(li.inflate(R.layout.pageview_overview_second, null));
        views.add(li.inflate(R.layout.pageview_overview_third, null));
        views.add(li.inflate(R.layout.pageview_overview_forth, null));

    }


    public void initViewPager(View view) {
        pager = (ViewPager) view.findViewById(R.id.in_viewpager);
        PagerAdapter adapter = new MyPagerAdapter(views);
        pager.setAdapter(adapter);
//        pager.setChan
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int index) {
                for (int i = 0; i < tvs.size(); i++) {
                    if (i == index) {
                        tvs.get(i).setTextColor(Color.BLUE);
                    } else {
                        tvs.get(i).setTextColor(Color.rgb(55, 55, 55));
                    }
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }
}
