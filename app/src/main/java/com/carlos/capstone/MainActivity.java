package com.carlos.capstone;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.carlos.capstone.interfaces.Callback;
import com.carlos.capstone.utils.MyApplication;
import com.carlos.capstone.utils.Utilities;
import com.facebook.stetho.Stetho;
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
        Intent intent=getIntent();
        if (intent != null) {
            if(intent.getBooleanExtra("from_widget_key",false)) {
                String symbol=intent.getStringExtra(getString(R.string.symbol_key));
                String company=intent.getStringExtra(getString(R.string.company_name_key));
                String type=intent.getStringExtra("security_type_key");
                this.onItemSelected(symbol,company,type);

            }
        }





        if (savedInstanceState==null) {

            Stetho.initializeWithDefaults(this);
            //inmediatly after installation, extract from shared preferences

//            SharedPreferences sp=PreferenceManager.getDefaultSharedPreferences(this);
//            SharedPreferences.Editor editor=sp.edit();
//            if(findViewById(R.id.main_activity_container)!=null) {
//
//                editor.putBoolean(getString(R.string.two_pane_key),true);
//
//            } else {
//
//                editor.putBoolean(getString(R.string.two_pane_key),false);
//            }
//            editor.apply();


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

        if(Utilities.hasOnePane(this)) {
            Bundle bundle;
            Intent intent = new Intent(this, DetailIndexActivity.class);
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
                bundle= ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                bundle.putString(getString(R.string.ticker_bundle_key), ticker);
                intent.putExtras(bundle);
                startActivity(intent,bundle);
            } else {
                bundle=new Bundle();
                bundle.putString(getString(R.string.ticker_bundle_key), ticker);
                intent.putExtras(bundle);
                startActivity(intent);
            }


        } else {
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

        //phone mode
        if(Utilities.hasOnePane(this)){
            //EQUITY
            if(securityType!=null && securityType.equals(getString(R.string.equity).toUpperCase())) {
                Intent intent=new Intent(this,DetailStockActivity.class);
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {

                    bundleEquity=ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                    bundleEquity.putString(getString(R.string.symbol_key),ticker);
                    bundleEquity.putString(getString(R.string.company_name_key),companyName);
                    intent.putExtras(bundleEquity);
                    startActivity(intent,bundleEquity);

                } else {

                    intent.putExtras(bundleEquity);
                    startActivity(intent);
                }




            //INDEX or ETF
            } else {
                Bundle bundleIndexEtf=new Bundle();
                Intent intent = new Intent(this, DetailIndexActivity.class);
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
                    bundleIndexEtf= ActivityOptions.makeSceneTransitionAnimation(this).toBundle();
                    bundleIndexEtf.putString(getString(R.string.ticker_bundle_key),ticker);
                    intent.putExtras(bundleIndexEtf);
                    startActivity(intent,bundleIndexEtf);

                } else {
                    bundleIndexEtf.putString(getString(R.string.ticker_bundle_key),ticker);
                    intent.putExtras(bundleIndexEtf);
                    startActivity(intent);
                }
            }
            Toast.makeText(this, "click card", Toast.LENGTH_SHORT).show();
        //tablet device
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
                Bundle bundleIndexEtfTab=new Bundle();
                bundleIndexEtfTab.putString(getString(R.string.ticker_bundle_key),ticker);
                FragmentIndexDetail fragmentIndexDetail=new FragmentIndexDetail();
                fragmentIndexDetail.setArguments(bundleIndexEtfTab);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.main_activity_container,fragmentIndexDetail)
                        .commit();
            }

        }

    }



    @Override
    protected void onDestroy() {
        Log.d(LOG_TAG,"ActivityMain on Destroy");
        super.onDestroy();
    }

}
