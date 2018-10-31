package com.hku.msc.fragment.News;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hku.msc.MainActivity;
import com.hku.msc.R;
import com.hku.msc.view.LoadingView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FragmentEvents extends Fragment implements LoadingView.LoadingViewListener {
    private static final String TAG = "FragmentEvents";

    private LoadingView mLoadingView;

    private LinearLayout eventsLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_events, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.fragment_toolbar);
        toolbar.setTitle("Events");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.icon_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "toolbar onclick");
                ((MainActivity) getActivity()).goBackView("home");
            }
        });

        //显示loading页面
        mLoadingView = (LoadingView) view.findViewById(R.id.loading);
        mLoadingView.setListener(this);
        mLoadingView.showLoading();

        eventsLayout = view.findViewById(R.id.linearLayout);

        initNewThread();

        return view;
    }

    private void initNewThread() {
        new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        Document doc = Jsoup.connect("https://www.msc-cs.hku.hk/NewsnEvents").get();
                        Elements elements = doc.select("div.Events");

                        LayoutInflater layoutInflater = getLayoutInflater();

                        for (Element element : elements) {
                            String img = element.select("div.h-card").attr("data-src");
                            String time = element.select("div.content").select("div.ch-info-out").text();
                            String location = element.select("div.content").select("div.ch-info").text();
                            String title = element.select("div.content").select("h5.title").text();
                            String des = element.select("div.content").select("p.description").text();

                            Bitmap bitmap = getHttpBitmap("https://www.msc-cs.hku.hk" + img);

                            View cardview = layoutInflater.inflate(R.layout.cardview_events, null);

                            ImageView imageView = cardview.findViewById(R.id.image);
                            TextView timeView = cardview.findViewById(R.id.time);
                            TextView locationView = cardview.findViewById(R.id.loaction);
                            TextView titleView = cardview.findViewById(R.id.title);
                            TextView desView = cardview.findViewById(R.id.des);

                            setBitmap(imageView, bitmap);
                            setText(timeView, time);
                            setText(locationView, location);
                            setText(titleView, title);
                            setText(desView, des);

                            addCardView(cardview, eventsLayout);
                        }

                        showContentView();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d(TAG, "onFailure: ");
                    mLoadingView.showFailed();
                }

            }
        }.start();
    }

    private void setBitmap(final ImageView imageView, final Bitmap bitmap) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bitmap);
            }
        });
    }

    private void setText(final TextView text, final String value) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                text.setText(value);
            }
        });
    }

    private void addCardView(final View cardView, final LinearLayout layout) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (cardView.getParent() != null) {
                    Log.i(TAG, "cardview getParent is not null, removeView ");
                    ((ViewGroup) cardView.getParent()).removeView(cardView);
                }
                layout.addView(cardView);
//                Log.i(TAG, "addCardView ");
            }
        });
    }

    private void showContentView() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLoadingView.showContentView();
            }
        });
    }

    private static Bitmap getHttpBitmap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setConnectTimeout(0);
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    @Override
    public void onFailedClickListener() {
        initNewThread();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden == false) {
            Log.i(TAG, "重新加载Events");
            mLoadingView.showLoading();
            eventsLayout.removeAllViews();
            initNewThread();
        }
    }
}
