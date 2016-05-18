package com.carlos.capstone;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AnimationUtils;

import com.carlos.capstone.utils.Utilities;
import com.github.mikephil.charting.charts.CombinedChart;

/**
 * Created by Carlos on 09/02/2016.
 */
public class DetailIndexActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index_detail);
        String ticker=null;
        boolean fromWidget=false;



        if(savedInstanceState==null) {//si no pongo esto se bloquea la pagina summary al rotar
            Intent intent = getIntent();
            if (intent != null) {
                Bundle bundle = intent.getExtras();
                if (bundle != null) {
                    ticker = bundle.getString(getString(R.string.ticker_bundle_key));
                    fromWidget=bundle.getBoolean(getString(R.string.stock_from_widget_key),false);

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
            FragmentIndexDetail fragmentIndexDetail = new FragmentIndexDetail();
            Bundle bundle = new Bundle();
            bundle.putString(getString(R.string.ticker_bundle_key), ticker);
            bundle.putBoolean(getString(R.string.stock_from_widget_key),fromWidget);
            fragmentIndexDetail.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragmentIndexDetail)
                    .commit();

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Utilities.analyticsTrackScreen(getApplication(),"DetailIndexActivity");

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
