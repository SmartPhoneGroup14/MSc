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
import android.view.View;
import android.webkit.WebView;
import android.widget.ExpandableListView;

import com.hku.msc.fragment.FragmentAbout;
import com.hku.msc.fragment.FragmentHome;
import com.hku.msc.menu.ExpandableListAdapter;
import com.hku.msc.menu.MenuModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "MainActivity";

    //页面
    private FragmentHome fragmentHome;
    private FragmentAbout fragmentAbout;
    //当前页
    private Fragment currentFragment;


    ExpandableListAdapter expandableListAdapter;
    ExpandableListView expandableListView;
    List<MenuModel> headerList = new ArrayList<>();
    HashMap<MenuModel, List<MenuModel>> childList = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        initToolbar();
        initNavigationView();

        fragmentHome = new FragmentHome();
        switchFragment(fragmentHome);
    }

//    private void initToolbar() {
//        Log.i(TAG, "initToolbar");
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        //初始化抽屉的toggle 与滑动动作绑定
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//    }

    //初始化左侧菜单点击事件
    private void initNavigationView() {
        expandableListView = findViewById(R.id.expandableListView);
        prepareMenuData();
        populateExpandableList();

        // 抽屉点击事件监听
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

    //     提供给fragment toolbar 返回按钮调用，返回主页面/上级页面
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

    // 点击背景事件，隐藏左侧抽屉
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


//    创建MainActivity 的 toolbar 的右侧按钮
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
//        return super.onOptionsItemSelected(item);
//    }

    // 左侧抽屉按钮点击事件
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

    // 参考 : https://www.journaldev.com/19375/android-expandablelistview-navigationview
    // https://stackoverflow.com/questions/32419446/adding-expandablelistview-to-navigationview/32664433#32664433
    // https://www.androidhive.info/2013/07/android-expandable-list-view-tutorial/
    private void prepareMenuData() {

        MenuModel menuModel = new MenuModel("Android WebView Tutorial", true, false, "https://www.journaldev.com/9333/android-webview-example-tutorial");
        //Menu of Android Tutorial. No sub menus
        headerList.add(menuModel);

        if (!menuModel.hasChildren) {
            childList.put(menuModel, null);
        }

        menuModel = new MenuModel("Java Tutorials", true, true, ""); //Menu of Java Tutorials
        headerList.add(menuModel);
        List<MenuModel> childModelsList = new ArrayList<>();
        MenuModel childModel = new MenuModel("Core Java Tutorial", false, false, "https://www.journaldev.com/7153/core-java-tutorial");
        childModelsList.add(childModel);

        childModel = new MenuModel("Java FileInputStream", false, false, "https://www.journaldev.com/19187/java-fileinputstream");
        childModelsList.add(childModel);

        childModel = new MenuModel("Java FileReader", false, false, "https://www.journaldev.com/19115/java-filereader");
        childModelsList.add(childModel);


        if (menuModel.hasChildren) {
            Log.d("API123", "here");
            childList.put(menuModel, childModelsList);
        }

        childModelsList = new ArrayList<>();
        menuModel = new MenuModel("Python Tutorials", true, true, ""); //Menu of Python Tutorials
        headerList.add(menuModel);
        childModel = new MenuModel("Python AST – Abstract Syntax Tree", false, false, "https://www.journaldev.com/19243/python-ast-abstract-syntax-tree");
        childModelsList.add(childModel);

        childModel = new MenuModel("Python Fractions", false, false, "https://www.journaldev.com/19226/python-fractions");
        childModelsList.add(childModel);

        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }
    }

    private void populateExpandableList() {

        expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (headerList.get(groupPosition).isGroup) {
                    if (!headerList.get(groupPosition).hasChildren) {
                        WebView webView = findViewById(R.id.webView);
                        webView.loadUrl(headerList.get(groupPosition).url);
                        onBackPressed();
                    }
                }

                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                if (childList.get(headerList.get(groupPosition)) != null) {
                    MenuModel model = childList.get(headerList.get(groupPosition)).get(childPosition);
                    if (model.url.length() > 0) {
                        WebView webView = findViewById(R.id.webView);
                        webView.loadUrl(model.url);
                        onBackPressed();
                    }
                }

                return false;
            }
        });
    }

}