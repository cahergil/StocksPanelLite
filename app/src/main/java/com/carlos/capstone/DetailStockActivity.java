package com.carlos.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.carlos.capstone.utils.Utilities;

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

}
