package com.carlos.capstone;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.carlos.capstone.utils.Utilities;
import com.github.mikephil.charting.charts.CombinedChart;

/**
 * Created by Carlos on 22/02/2016.
 */
public class DetailStockActivity extends AppCompatActivity {
    private static final String LOG_TAG=DetailStockActivity.class.getSimpleName();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG,"onCreate DetailStockActivity");
        setContentView(R.layout.activity_stock_detail);

        if(savedInstanceState==null) {
            FragmentStockDetail fragmentStockDetail = new FragmentStockDetail();
            Intent intent = getIntent();
            if (intent != null) {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    fragmentStockDetail.setArguments(bundle);
                }
            }

            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
                Slide slideBottom = new Slide(Gravity.RIGHT);
                slideBottom.setInterpolator(AnimationUtils.loadInterpolator(this,
                        android.R.interpolator.fast_out_slow_in));
                slideBottom.setDuration(300);
                getWindow().setEnterTransition(slideBottom);
                supportPostponeEnterTransition();
            }

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.detail_stock_container, fragmentStockDetail)
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Utilities.analyticsTrackScreen(getApplication(),"DetailStockActivity");

    }
    @Override
    public void onBackPressed() {

        CombinedChart chart= (CombinedChart) findViewById(R.id.chart);
        chart.setVisibility(View.INVISIBLE);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        }
        super.onBackPressed();
    }
}
