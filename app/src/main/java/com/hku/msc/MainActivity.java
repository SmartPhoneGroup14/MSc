package com.hku.msc;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.hku.msc.fragment.FragmentAbout;
import com.hku.msc.fragment.FragmentHome;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "MainActivity";

    //页面
    private FragmentHome fragmentHome;
    private FragmentAbout fragmentAbout;
    //当前页
    private Fragment currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        initToolbar();
        initNavigationView();

        fragmentHome = new FragmentHome();
        switchFragment(fragmentHome);
    }

    private void initToolbar() {
        Log.i(TAG, "initToolbar");
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        //初始化抽屉
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();

    }

    private void initNavigationView() {
        //初始化左侧菜单点击事件
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    //切换fragment
    private void switchFragment(Fragment targetFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (targetFragment == null) {
            Log.e(TAG, "switchFragment targetFragment is null !");
            return;
        }
        if (currentFragment != null) {
            Log.i(TAG, "switchFragment 隐藏 fragment : " + currentFragment.getClass());
            fragmentTransaction.hide(currentFragment);
        }
        if (!targetFragment.isAdded()) {
            Log.i(TAG, "添加 fragment : " + targetFragment.getClass());
            fragmentTransaction.add(R.id.main_frame_layout, targetFragment);
        } else {
            Log.i(TAG, "显示 fragment : " + targetFragment.getClass());
            fragmentTransaction.show(targetFragment);
        }
        currentFragment = targetFragment;
        fragmentTransaction.commit();
    }

    public void goBackView(String currentView) {
        Log.i(TAG, "currentView : " + currentView);
        switch (currentView) {
            case "about":
                switchFragment(fragmentHome);
                break;
            default:
                switchFragment(fragmentHome);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        Log.i(TAG,"onCreateOptionsMenu");
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        Log.i(TAG, "onOptionsItemSelected");
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Log.i(TAG, "onNavigationItemSelected MenuItem : " + item.getTitle());

        // Handle navigation view item clicks here.
        int id = item.getItemId();
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (id == R.id.nav_home) {
            if (fragmentHome == null) {
                fragmentHome = new FragmentHome();
            }
//            toolbar.setTitle("Home");
            switchFragment(fragmentHome);

        } else if (id == R.id.nav_about) {
            if (fragmentAbout == null) {
                fragmentAbout = new FragmentAbout();
            }
//            toolbar.setTitle("Home > " + item.getTitle());
            switchFragment(fragmentAbout);

        } else if (id == R.id.nav_curriculum) {

        } else if (id == R.id.nav_admission) {

        } else if (id == R.id.nav_graduate) {

        } else if (id == R.id.nav_news) {

        } else if (id == R.id.nav_student) {

        }
//        item.setCheckable(true);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}