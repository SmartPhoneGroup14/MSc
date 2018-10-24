package com.hku.msc.fragment.About;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.hku.msc.MainActivity;
import com.hku.msc.R;

public class FragmentMessage extends Fragment {
    private static final String TAG = "FragmentAbout > Message";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_message, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.fragment_message_toolbar);
//      自定义返回图标
//        toolbar.setNavigationIcon( R.attr.navigationIcon);
        toolbar.setTitle("Home > About > Message from Programme Director");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.i(TAG, "onOptionsItemSelected " + item.getItemId() + item.getTitle());
//        FragmentTransaction fragmentTransaction = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.hide()
        ((MainActivity) getActivity()).goBackView("home");

//        return true;
        return super.onOptionsItemSelected(item);
    }

}
