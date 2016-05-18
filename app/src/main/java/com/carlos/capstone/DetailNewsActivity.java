package com.carlos.capstone;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.carlos.capstone.utils.Utilities;

/**
 * Created by Carlos on 31/12/2015.
 */
public class DetailNewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        getWindow().setFeatureInt(Window.FEATURE_PROGRESS,Window.PROGRESS_VISIBILITY_ON);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_detail_activity);

        Intent intent=getIntent();
        if(intent!=null) {
            if(savedInstanceState==null) {
                Bundle bundle = new Bundle();
                bundle.putString("url", intent.getStringExtra("url"));
                bundle.putString("title",intent.getStringExtra("title"));
                FragmentNewsDetail fragmentNewsDetail = new FragmentNewsDetail();
                fragmentNewsDetail.setArguments(bundle);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.container, fragmentNewsDetail);
                transaction.commit();
            }
        }



    }
    @Override
    protected void onStart() {
        super.onStart();
        Utilities.analyticsTrackScreen(getApplication(),"DetailNewsWebViewActivity");

    }
}
