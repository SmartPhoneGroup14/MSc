package com.hku.msc.fragment.BasicInfo;

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

public class FragmentSchedule extends Fragment {
    private static final String TAG = "Basic Info > Schedule";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.fragment_toolbar);
        toolbar.setTitle("Programme Schedule");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//      自定义返回图标
        toolbar.setNavigationIcon(R.drawable.icon_arrow);
//        setHasOptionsMenu(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG,"toolbar onclick");
                ((MainActivity) getActivity()).goBackView("home");
            }
        });

        return view;
    }

}
