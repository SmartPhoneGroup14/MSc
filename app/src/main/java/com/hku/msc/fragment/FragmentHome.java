package com.hku.msc.fragment;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.hku.msc.LocalImageHolderView;
import com.hku.msc.R;

import java.util.ArrayList;

public class FragmentHome extends Fragment implements AppBarLayout.OnOffsetChangedListener {
    private static final String TAG = "FragmentHome";

    private float totalHeight;      //总高度
    private float toolBarHeight;    //toolBar高度
    private float offSetHeight;     //总高度 -  toolBar高度  布局位移值

    AppBarLayout appBarLayout;
    private ConvenientBanner convenientBanner;
    private ArrayList<Integer> localImages = new ArrayList<Integer>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, TAG + " onCreateView");
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initToolbar(view);
        initAppBarLayout(view);
        initConvenientBanner(view);

        return view;
    }

    private void initToolbar(View view) {
        Log.i(TAG, "initToolbar");
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.home_toolbar);
        toolbar.setTitle("Home");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        // 给左上角图标加一个可以返回的图标
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 决定左上角图标是否可以点击，在4.0版本以前默认值为true，4.0以上默认值改为false
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);

        // 在Fragment中使用menu菜单, 使重写的onCreateOptionsMenu（）,onOptionsItemSelected（）生效
//        setHasOptionsMenu(true);

        // 绑定返回按钮 与 弹出左侧抽屉 ,尝试失败, 采用调用MainActivity 的方法进行fragment 切换
//        DrawerLayout drawer = (DrawerLayout) view.findViewById(R.id.main_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        if (drawer == null) {
//            Log.e(TAG, "drawer is null");
//        }
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
    }

    private void initAppBarLayout(View view) {
        // Home页上下滚动
        appBarLayout = (AppBarLayout) view.findViewById(R.id.app_bar);
        // 滚动事件,控制顶层图片渐隐
        appBarLayout.addOnOffsetChangedListener(this);

        totalHeight = getResources().getDimension(R.dimen.app_bar_height);
        toolBarHeight = getResources().getDimension(R.dimen.tool_bar_height);
        offSetHeight = totalHeight - toolBarHeight;
    }

    private void initConvenientBanner(View view) {
        //初始化翻页素材
        localImages.add(R.drawable.slider1);
        localImages.add(R.drawable.slider_admission);

        convenientBanner = (ConvenientBanner) view.findViewById(R.id.convenientBanner);
        convenientBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages)
        // 设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
//                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
        // 设置指示器的方向
        // .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
        // .setOnPageChangeListener(this)
        // 监听翻页事件
//                .setOnItemClickListener(this)
        ;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//        Log.d(TAG, "----> " + verticalOffset);
//        向上滑动得到的值是负的，初始值为0 就是展开状态。
//        verticalOffset能滑动最远距离为 AppBarLayout的高度 减去 CollapsingToolbarLayout折叠时的高度

        //滑动一次 得到渐变缩放值
        float alphaScale = (-verticalOffset) / offSetHeight;
        convenientBanner.getViewPager().setAlpha(1.0f - alphaScale);
    }

    // 开始自动翻页
    @Override
    public void onResume() {
        super.onResume();
        convenientBanner.startTurning(4000);
    }

    // 停止自动翻页
    @Override
    public void onPause() {
        super.onPause();
        convenientBanner.stopTurning();
    }

    //翻页点击事件
//    @Override
//    public void onItemClick(int position) {
////        Toast.makeText(this,"点击了第"+position+"个",Toast.LENGTH_SHORT).show();
//        Log.i(TAG, "convenientBanner onItenClick : " + position);
////        startActivity(new Intent(this, MainActivity.class));
////        convenientBanner.setCanLoop(!convenientBanner.isCanLoop());
//    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        Log.i(TAG, TAG + " onHiddenChanged hidden : " + hidden);
    }
}
