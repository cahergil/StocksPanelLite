package com.carlos.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.carlos.capstone.interfaces.Callback;
import com.carlos.capstone.utils.MyApplication;
import com.carlos.capstone.utils.Utilities;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;


/**
 * Created by Carlos on 18/01/2016.
 */
public class MainActivity extends AppCompatActivity implements Callback {

    private static final String LOG_TAG=MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(LOG_TAG,"onCreate MainActivity");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState==null) {

            //Stetho.initializeWithDefaults(this);

            //if bundleWidget ==null not comming from widget, and onCreate means this Activity isn't in background.
            //if bundleWidget !=null it comes from widget,and onCreate means this Activity isn't in background.
            if(Utilities.hasTwoPane(this)) {
                Intent widgetIntent=getIntent();
                Bundle bundleWidget = widgetIntent.getExtras();
                widgetLauncher(bundleWidget);
            }

        }




    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(LOG_TAG,"onNewIntent");
        setIntent(intent);
        //hasTwoPane==false then do nothing
        if(Utilities.hasTwoPane(this)) {
            Intent widgetIntent=getIntent();
            Bundle bundleWidget = widgetIntent.getExtras();
            //if bundleWidget==null then the app doesn't come from Widget and this
            //activity is at top of the stack(because onNewIntent has executed)
            //in this case do nothing, just after this method executes onResume().
            if(bundleWidget!=null) {
                widgetLauncher(bundleWidget);
            }
        }
    }

    private void widgetLauncher(Bundle bundleWidget){
        if(bundleWidget!=null) {
            String securityType = bundleWidget.getString(getString(R.string.lpf_security_type_key));
            if (securityType != null) {
                if (securityType.equals(getString(R.string.equity).toUpperCase())) {

                    FragmentStockDetail fragmentStockDetail=new FragmentStockDetail();
                    fragmentStockDetail.setArguments(bundleWidget);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_activity_container,fragmentStockDetail)
                            .commit();

                } else {

                    FragmentIndexDetail fragmentIndexDetail = new FragmentIndexDetail();

                    fragmentIndexDetail.setArguments(bundleWidget);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.main_activity_container, fragmentIndexDetail)
                            .commit();
                }


            }
        } else {
            String ticker = "TX60.TS";
            Bundle bundle = new Bundle();
            bundle.putString(getString(R.string.ticker_bundle_key), ticker);
            //not neccesary to remove fragment since replace removes the previous fragment
            // http://stackoverflow.com/questions/20682248/difference-between-fragmenttransaction-add-and-fragmenttransaction-replace
            FragmentIndexDetail fragmentIndexDetail = new FragmentIndexDetail();
            fragmentIndexDetail.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_activity_container, fragmentIndexDetail)
                    .commit();
        }

    }
    @Override
    protected void onStart() {
        super.onStart();
        Utilities.analyticsTrackScreen(getApplication(),LOG_TAG);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //  getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(this,SettingsActivity.class));
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }


    /**
     * Callback from Fragment Region, when the user selects an index
     * @param ticker
     */
    @Override
    public void onItemInIndexesSelected(String ticker,String indexName,String region) {
        Tracker tracker=((MyApplication)getApplication()).getTracker();
        //send to GA name of the index clicked
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory(getString(R.string.index_ga_category))
                .setAction(indexName)
                .setLabel(region)
                .build());

        Bundle bundle=new Bundle();
        bundle.putString(getString(R.string.ticker_bundle_key), ticker);
        if(Utilities.hasOnePane(this)) {
            Intent intent = new Intent(this, DetailIndexActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);

        } else {
            //not neccesary to remove fragment since "replace" already removes the previous fragment
           // http://stackoverflow.com/questions/20682248/difference-between-fragmenttransaction-add-and-fragmenttransaction-replace
            FragmentIndexDetail fragmentIndexDetail = new FragmentIndexDetail();
            fragmentIndexDetail.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_activity_container, fragmentIndexDetail)
                    .commit();

        }
    }

    /**
     * Callback from FragmentFavorites
     * @param ticker
     * @param companyName
     * @param securityType
     */
    @Override
    public void onItemSelected(String ticker, String companyName, String securityType) {
        Bundle bundleEquity=new Bundle();
        bundleEquity.putString(getString(R.string.symbol_key),ticker);
        bundleEquity.putString(getString(R.string.company_name_key),companyName);
        Bundle bundleIndexEtf=new Bundle();
        bundleIndexEtf.putString(getString(R.string.ticker_bundle_key),ticker);
        //phone mode
        if(Utilities.hasOnePane(this)){
            //EQUITY
            if(securityType!=null && securityType.equals(getString(R.string.equity).toUpperCase())) {
                Intent intent=new Intent(this,DetailStockActivity.class);
                intent.putExtras(bundleEquity);
                startActivity(intent);
             //INDEX or ETF
            } else {
                Intent intent = new Intent(this, DetailIndexActivity.class);
                intent.putExtras(bundleIndexEtf);
                startActivity(intent);

            }

        //tablet mode
        } else {
            //EQUITY
            if(securityType!=null && securityType.equals(getString(R.string.equity).toUpperCase())) {
                FragmentStockDetail fragmentStockDetail=new FragmentStockDetail();
                fragmentStockDetail.setArguments(bundleEquity);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_activity_container,fragmentStockDetail)
                        .commit();
            //INDEX OR ETF
            } else {
                FragmentIndexDetail fragmentIndexDetail=new FragmentIndexDetail();
                fragmentIndexDetail.setArguments(bundleIndexEtf);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_activity_container,fragmentIndexDetail)
                        .commit();
            }

        }

    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        try {
//            AppBarLayout appBarLayout;
//            appBarLayout = ((AppBarLayout) findViewById(R.id.appbar));
//            CollapsingToolbarLayout collapsingToolbarLayout= (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//
//            if (ev.getAction() == ev.ACTION_UP) {
//                Log.d(LOG_TAG,"Appbar->getBottom:"+appBarLayout.getBottom());
//                Log.d(LOG_TAG,"collapsingToolbarLayout->getBottom:"+collapsingToolbarLayout.getBottom());
//                if(appBarLayout.getBottom()<379) {
//
//                }
//
//            }
//            return super.dispatchTouchEvent(ev);
//        } catch (Exception e) {
//            Log.e(LOG_TAG, "dispatchTouchEvent " + e.toString());
//            return false;
//        }
//    }

    @Override
    protected void onDestroy() {
        Log.d(LOG_TAG,"ActivityMain on Destroy");
        super.onDestroy();
    }

}
