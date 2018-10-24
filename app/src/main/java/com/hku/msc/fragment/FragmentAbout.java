package com.hku.msc.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.hku.msc.MainActivity;
import com.hku.msc.R;

public class FragmentAbout extends Fragment implements Button.OnClickListener {

    private static final String TAG = "FragmentAbout";

    private Button btn1;
    private Button btn2;
    private Button btn3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, TAG + " onCreateView");
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        btn1 = (Button) view.findViewById(R.id.fragment_about_btn1);
        btn2 = (Button) view.findViewById(R.id.fragment_about_btn2);
        btn3 = (Button) view.findViewById(R.id.fragment_about_btn3);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);


        Toolbar toolbar = (Toolbar) view.findViewById(R.id.about_toolbar);

//        toolbar.setNavigationIcon( R.attr.navigationIcon);

        toolbar.setTitle("fragment about");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);


        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setHasOptionsMenu(true);


        return view;
    }


//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        Log.i(TAG, "onCreateOptionsMenu ");
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.main, menu);
//    }

    @Override
    public void onClick(View view) {
        Button btn = (Button) view;
        String text = btn.getText().toString();
        int id = btn.getId();
        Log.i(TAG, "onClick id : " + id + " , text : " + text);

        if (id == R.id.fragment_about_btn1) {
            Log.i(TAG, "fragment_about_btn1");

        } else if (id == R.id.fragment_about_btn2) {
            Log.i(TAG, "fragment_about_btn2");

        } else if (id == R.id.fragment_about_btn3) {
            Log.i(TAG, "fragment_about_btn3");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, "onOptionsItemSelected " + item.getItemId() + item.getTitle());
//        FragmentTransaction fragmentTransaction = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.hide()

        ((MainActivity) getActivity()).goBackView("about");

        return true;
//        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        Log.i(TAG, TAG + " onHiddenChanged hidden : " + hidden);
    }

}