package com.carlos.capstone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.carlos.capstone.services.RegionChartAndIndexLoaderService;
import com.carlos.capstone.sync.CapstoneSyncAdapter;
import com.carlos.capstone.utils.Utilities;

/**
 * Created by Carlos on 01/04/2016.
 */
public class SplashActivity extends AppCompatActivity {
    private static final String LOG_TAG=SplashActivity.class.getSimpleName();
    private  boolean IsIndexesChartAvailable=false;
    private boolean animationStarted = false;
    private BroadcastReceiver mMessageReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.SplashTheme);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mMessageReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(LOG_TAG,"## onReceivedSplash");
                IsIndexesChartAvailable=true;
                Log.d(LOG_TAG,"IsIndexesChartAvailable"+IsIndexesChartAvailable);
            }
        };

        if (savedInstanceState==null) {
            LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,new IntentFilter(getString(R.string.data_ready)));
            // Stetho.initializeWithDefaults(this);
            //inmediatly after installation, extract from shared preferences
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            if (sharedPreferences.getBoolean(getString(R.string.first_launch), true)) {
                //configure periodic sync and start immediately
                CapstoneSyncAdapter.initializeSyncAdapter(this);


            } else {
                //it is a hot start, the app is not in background, otherwiese only onResume() would be executed
                //CapstoneSyncAdapter.syncImmediately(this);
                Intent intentAmerica = new Intent(this, RegionChartAndIndexLoaderService.class);
                intentAmerica.setAction(RegionChartAndIndexLoaderService.FETCH_AMERICA_DATA);
                startService(intentAmerica);
            }
        }

        new WaitAsyncTask().execute();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Utilities.analyticsTrackScreen(getApplication(),LOG_TAG);

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        if (!hasFocus || animationStarted) {
            return;
        }

        animate();

        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();

    }

    private void animate() {

        ImageView imageView= (ImageView) findViewById(R.id.img);
        ViewGroup container = (ViewGroup) findViewById(R.id.rootSplash);
        ViewCompat.animate(imageView)
              //  .translationY(getResources().getInteger(R.integer.splash_translationY))
                .alpha(1)
                .setStartDelay(200)
                .setDuration(800).setInterpolator(
                new DecelerateInterpolator(1.2f)).start();
        for (int i = 0; i < container.getChildCount(); i++) {
            View v = container.getChildAt(i);
            ViewPropertyAnimatorCompat viewAnimator=null;
            if (v instanceof TextView) {
                viewAnimator = ViewCompat.animate(v)
                     //   .translationY(120)
                        .alpha(1)
                        .setStartDelay(500)
                        .setDuration(800);
                viewAnimator.setInterpolator(new DecelerateInterpolator()).start();
            }

        }


    }
        private  class WaitAsyncTask extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            while (!IsIndexesChartAvailable) {
                Log.d(LOG_TAG,"IsIndexesChartAvailable"+IsIndexesChartAvailable);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(mainIntent);
            finish();
        }
    }

}
