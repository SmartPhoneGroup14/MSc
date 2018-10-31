package com.hku.msc;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;

import com.hku.msc.fragment.About.FragmentAbout;
import com.hku.msc.fragment.About.FragmentFaculty;
import com.hku.msc.fragment.About.FragmentMessage;
import com.hku.msc.fragment.BasicInfo.FragmentFees;
import com.hku.msc.fragment.BasicInfo.FragmentSchedule;
import com.hku.msc.fragment.BasicInfo.FragmentOverview;
import com.hku.msc.fragment.FragmentHome;
import com.hku.msc.fragment.News.FragmentEvents;
import com.hku.msc.fragment.News.FragmentNews;
import com.hku.msc.menu.ExpandableListAdapter;
import com.hku.msc.menu.MenuModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

    //页面
    private FragmentHome fragmentHome;
    //About页
    private FragmentAbout fragmentAbout;
    private FragmentFaculty fragmentFaculty;
    private FragmentMessage fragmentMessage;
    //Basic Info 页
    private FragmentOverview fragmentOverview;
    private FragmentSchedule fragmentSchedule;
    private FragmentFees fragmentFees;
    //News & Events
    private Fragment fragmentNews;
    private Fragment fragmentEvents;
    //当前页
    private Fragment currentFragment;

    //左侧抽屉,expandable list view
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
        // 抽屉ExpandableListView 初始化
        prepareMenuData();
        populateExpandableList();

        // 抽屉点击事件监听
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
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
    public void goBackView(String targetView) {
        Log.i(TAG, "goBackView : " + targetView);
        switch (targetView) {
            case "home":
                switchFragment(fragmentHome);
                break;
            default:
                switchFragment(fragmentHome);
        }
    }

    public void goForwardView(String targetView) {
        Log.i(TAG, "goForwardView : " + targetView);
        switch (targetView) {
            case "Overview":
                if (fragmentOverview == null) {
                    fragmentOverview = new FragmentOverview();
                }
                switchFragment(fragmentOverview);
                break;
            case "Schedule":
                if (fragmentSchedule == null) {
                    fragmentSchedule = new FragmentSchedule();
                }
                switchFragment(fragmentSchedule);
                break;
            case "Fees":
                if (fragmentFees == null) {
                    fragmentFees = new FragmentFees();
                }
                switchFragment(fragmentFees);
                break;
            default:
                break;
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

    // 左侧抽屉按钮点击事件:NavigationItem---改用ExpandableListView
//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        Log.i(TAG, "onNavigationItemSelected MenuItem : " + item.getTitle());
//
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
////        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//
//        if (id == R.id.nav_home) {
//            if (fragmentHome == null) {
//                fragmentHome = new FragmentHome();
//            }
////            toolbar.setTitle("Home");
//            switchFragment(fragmentHome);
//
//        } else if (id == R.id.nav_about) {
//            if (fragmentAbout == null) {
//                fragmentAbout = new FragmentAbout();
//            }
//            switchFragment(fragmentAbout);
//
//        } else if (id == R.id.nav_curriculum) {
//
//        } else if (id == R.id.nav_admission) {
//
//        } else if (id == R.id.nav_graduate) {
//
//        } else if (id == R.id.nav_news) {
//
//        } else if (id == R.id.nav_student) {
//
//        }
////        item.setCheckable(true);
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.main_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

    // 初始化左侧抽屉按钮数据
    // 参考 : https://www.journaldev.com/19375/android-expandablelistview-navigationview
    // https://www.jianshu.com/p/05df9c17a1d8
    private void prepareMenuData() {

        // Menu of Home. No sub menus
        MenuModel menuModel = new MenuModel("home", true, false);
        headerList.add(menuModel);

        List<MenuModel> childModelsList = new ArrayList<>();
        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }

        // Menu of Basci Infomation. 3 sub menus
        menuModel = new MenuModel("Basic Information", true, true);
        headerList.add(menuModel);

        childModelsList = new ArrayList<>();
        childModelsList.add(new MenuModel("Programme Overview", false, false));
        childModelsList.add(new MenuModel("Programme Schedule", false, false));
        childModelsList.add(new MenuModel("Composite Fees", false, false));
        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }

        // Menu of About. 3 sub menus
        menuModel = new MenuModel("About", true, true);
        headerList.add(menuModel);

        childModelsList = new ArrayList<>();
        childModelsList.add(new MenuModel("Faculty", false, false));
        childModelsList.add(new MenuModel("Message fom Programme Director", false, false));
        childModelsList.add(new MenuModel("About HKU", false, false));
        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }

//        // Menu of Admission. 6 sub menus
//        menuModel = new MenuModel("Admission", true, true);
//        headerList.add(menuModel);
//
//        childModelsList = new ArrayList<>();
//        childModelsList.add(new MenuModel("Admission Requirements", false, false));
//        childModelsList.add(new MenuModel("Application Procedures", false, false));
//        childModelsList.add(new MenuModel("Composition Fees", false, false));
//        childModelsList.add(new MenuModel("Words from Students and Graduates", false, false));
//        childModelsList.add(new MenuModel("Information Sessions", false, false));
//        childModelsList.add(new MenuModel("FAQ", false, false));
//        if (menuModel.hasChildren) {
//            childList.put(menuModel, childModelsList);
//        }
//
//        // Menu of Curriculum. 4 sub menus
//        menuModel = new MenuModel("Curriculum", true, true);
//        headerList.add(menuModel);
//
//        childModelsList = new ArrayList<>();
//        childModelsList.add(new MenuModel("Programme Overview", false, false));
//        childModelsList.add(new MenuModel("Courses", false, false));
//        childModelsList.add(new MenuModel("Duration of Study & Class Schedule", false, false));
//        childModelsList.add(new MenuModel("Regulations and Syllabus", false, false));
//        if (menuModel.hasChildren) {
//            childList.put(menuModel, childModelsList);
//        }
//
        // Menu of News & Events. no sub menus
        menuModel = new MenuModel("News & Events", true, true);
        headerList.add(menuModel);

        childModelsList = new ArrayList<>();
        childModelsList.add(new MenuModel("News", false, false));
        childModelsList.add(new MenuModel("Events", false, false));
        if (menuModel.hasChildren) {
            childList.put(menuModel, childModelsList);
        }
//
//        // Menu of Student Resources. 4 sub menus
//        menuModel = new MenuModel("Student Resources", true, true);
//        headerList.add(menuModel);
//
//        childModelsList = new ArrayList<>();
//        childModelsList.add(new MenuModel("Learning Environment", false, false));
//        childModelsList.add(new MenuModel("MSc(CompSc) Intranet", false, false));
//        childModelsList.add(new MenuModel("HKU Portal", false, false));
//        childModelsList.add(new MenuModel("Useful Links", false, false));
//        if (menuModel.hasChildren) {
//            childList.put(menuModel, childModelsList);
//        }
    }


    // 初始化Expandable 组件
    private void populateExpandableList() {

        expandableListAdapter = new ExpandableListAdapter(this, headerList, childList);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View view, int groupPosition, long id) {
                MenuModel headItem = headerList.get(groupPosition);
                Log.i(TAG, "onGroupClick : " + headItem.menuName);

                if (headItem.isGroup) {
                    if (!headItem.hasChildren) {
                        //一级菜单没有子菜单,先默认跳转到home页
                        switch (headItem.menuName) {
                            default:
                                if (fragmentHome == null) {
                                    fragmentHome = new FragmentHome();
                                }
                                switchFragment(fragmentHome);
                                onBackPressed();
                        }
                    }
                }
                return false;
            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long id) {
                if (childList.get(headerList.get(groupPosition)) != null) {
                    MenuModel childItem = childList.get(headerList.get(groupPosition)).get(childPosition);
                    Log.i(TAG, "onChildClick : " + childItem.menuName);

                    switch (childItem.menuName) {
                        case "Programme Overview":
                            if (fragmentOverview == null) {
                                fragmentOverview = new FragmentOverview();
                            }
                            switchFragment(fragmentOverview);
                            break;
                        case "Programme Schedule":
                            if (fragmentSchedule == null) {
                                fragmentSchedule = new FragmentSchedule();
                            }
                            switchFragment(fragmentSchedule);
                            break;
                        case "Composite Fees":
                            if (fragmentFees == null) {
                                fragmentFees = new FragmentFees();
                            }
                            switchFragment(fragmentFees);
                            break;
                        case "Faculty":
                            if (fragmentFaculty == null) {
                                fragmentFaculty = new FragmentFaculty();
                            }
                            switchFragment(fragmentFaculty);
                            break;
                        case "Message fom Programme Director":
                            if (fragmentMessage == null) {
                                fragmentMessage = new FragmentMessage();
                            }
                            switchFragment(fragmentMessage);
                            break;
                        case "About HKU":
                            if (fragmentAbout == null) {
                                fragmentAbout = new FragmentAbout();
                            }
                            switchFragment(fragmentAbout);
                            break;
                        case "News":
                            if (fragmentNews == null) {
                                fragmentNews = new FragmentNews();
                            }
                            switchFragment(fragmentNews);
                            break;
                        case "Events":
                            if (fragmentEvents == null) {
                                fragmentEvents = new FragmentEvents();
                            }
                            switchFragment(fragmentEvents);
                            break;
                        default:
                            if (fragmentHome == null) {
                                fragmentHome = new FragmentHome();
                            }
                            switchFragment(fragmentHome);
                    }

                    onBackPressed();
                } else {
                    Log.e(TAG, "onChildClick childList is null ! Head : " + headerList.get(groupPosition));
                }

                return false;
            }
        });
    }

}